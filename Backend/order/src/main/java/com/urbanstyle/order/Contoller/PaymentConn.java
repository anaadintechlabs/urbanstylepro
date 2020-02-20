package com.urbanstyle.order.Contoller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

@Component
public class PaymentConn {
	
	private String Secreat_API_Key = "sk_test_iAMtyoKyEuPJeePz8KhVcTO100AvnSjX72";
	
	public void Authentication() {
		Stripe.apiKey = Secreat_API_Key;
		
		RequestOptions requestOptions = RequestOptions.builder()
				  .setApiKey(Secreat_API_Key)
				  .build();

				Charge charge = null;
				try {
						charge = Charge.retrieve(
						  "ch_1GB8wOGikuwr0rEH00EHnsq5",
						  requestOptions
						);
				} catch (StripeException e1) {
					e1.printStackTrace();
				}

	}
	
	public void createCustomer() {
		Stripe.apiKey = Secreat_API_Key;
		CustomerCreateParams params =
				  CustomerCreateParams.builder()
				    .setEmail("SanchitKhurana@gmail.com")
				    .setName("Sanchit")
				    .setPhone("8950900035")
				    .build();

				try {
					System.out.println("customer succed");
					Customer customer = Customer.create(params);
				} catch (StripeException e) {
					e.printStackTrace();
				}


	}
	
	public void getCustomer(String CustomerName) {
		Stripe.apiKey = Secreat_API_Key;
		CustomerName = "cus_GibhGlFT9kXSxE";
		try {
			Customer oldCustomer = Customer.retrieve(CustomerName);
			System.out.println(""+oldCustomer);
		} catch (StripeException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCardToCustomer(String CustomerName) {
		Stripe.apiKey = Secreat_API_Key;
		try {
			Customer oldCustomer = Customer.retrieve(CustomerName);
			// create card parameters
			
			Map<String, Object> cardParams = new HashMap<>();
			cardParams.put("number", "4000002500003155");   //4000002500003155
			cardParams.put("exp_month", "02");
			cardParams.put("exp_year", "2022");
			cardParams.put("cvc", "999");
			
			// add token
			Map<String, Object> tokenMap = new HashMap<>();
			tokenMap.put("card", cardParams);
			
			Token token = Token.create(tokenMap);
			
			// generate source
			Map<String, Object> source = new HashMap<>();			
			source.put("source", token.getId());
			
			oldCustomer.getSources().create(source);
			
			System.out.println(""+oldCustomer);
		} catch (StripeException e) {
			e.printStackTrace();
		}
	}
	
	public void chargePayment(String CustomerName) {
		Stripe.apiKey = Secreat_API_Key;
		try {
			Customer oldCustomer = Customer.retrieve(CustomerName);
			
			Map<String, Object> chargeParam = new HashMap<>();
			chargeParam.put("amount", "60000");
			chargeParam.put("currency", "inr");
			chargeParam.put("customer", oldCustomer.getId());
			chargeParam.put("description", "Example charge");
			//chargeParam.put("return_url", "https://example.com/return_url");
			
		
			//PaymentIntent paymentIntent = PaymentIntent.create(chargeParam);
			// if use another card another then default
			
			/* chargeParam.put("source", "Card Id"); */
			
			// if use another card another then default
			
			Charge charge = Charge.create(chargeParam);
			String paymentIntent  =	charge.getPaymentIntent();
					//paymentIntent.confirm(chargeParam);
	  } catch (StripeException e) {
			e.printStackTrace();
		}
		
	}
	
}
