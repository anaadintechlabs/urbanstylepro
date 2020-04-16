package com.anaadihsoft.common.external;

import org.springframework.stereotype.Component;

public class UrlShortner {
	
	private String variantCode;
	private long userid;
	private String sku;
	
	public UrlShortner() {
		
	}
	
	public UrlShortner(String variantCode,long userid, String sku) {
		this.variantCode = variantCode;
		this.userid = userid;
		this.sku = sku;
	}
	
	public  String generateUid(String prefix,int digits){

		boolean prefixneeded = false;

		if(digits <= 0){
			digits = 13;
		}

		if(prefix!=null && prefix.length()!=0){

		prefixneeded =

		true;

		}

		//randomly generate a number for the numberic part

		//and convert that to a long from double eliminating decimals

		double numericpart = Math.random() * new Double(Math.pow(10, digits)).longValue();

		long numericpartinlong = (long)numericpart;

		//randomly generate a 6 digit number for the alphabetical part

		//and convert that to a long from double eliminating decimals

		double alphapart = Math.random() * 1000000;

		long alphapartinlong = (long)alphapart;

		

		//get a guranteed alphabetical of 3 chars

		String alphapartinstring = Long.toString(alphapartinlong);

		while(alphapartinstring.length()!=6){

		alphapart = Math.random() * 1000000;

		alphapartinlong = (

		long)alphapart;

		alphapartinstring = Long.toString(alphapartinlong);

		}

		//get a guranteed numeric part of 12 digits

		String numericpartinstring = Long.toString(numericpartinlong);

		while (numericpartinstring.length()!=digits) {

		numericpart = Math.random() *

		new Double(Math.pow(10, digits)).longValue();

		numericpartinlong = (

		long)numericpart;

		numericpartinstring = Long.toString(numericpartinlong);

		}

		//divide the alpha string of 6 digits into 3 sets of 2 digits

		//find the modulus of each part of 2 digits with 25 and add it to 65

		//to form a guranteed uppercase character within A to Z randomly

		int alphapartfirstchar = Integer.parseInt(alphapartinstring.substring(0,2))%25;

		int alphapartsecondchar = Integer.parseInt(alphapartinstring.substring(2,4))%25;

		int alphapartthirdchar = Integer.parseInt(alphapartinstring.substring(4))%25;

		//append the 3 chars to form a string - the alphabetical part

		String alphapartstring =

		new String(new char[]{(char)(alphapartfirstchar+65),(char)(alphapartsecondchar+65),(char)(alphapartthirdchar+65)});

		//return the alphanumeric random string as GUID

		alphapartstring = prefixneeded?prefix:alphapartstring;

		return numericpartinstring;

		}

	
	public String generateLink() {
		
		String userId = String.valueOf(userid);
		
		String generateURL = "";
			generateURL+=sku.substring(0, 3);
			if(sku.length()>3){
		}else {
			generateURL+=sku;
		}
		if(variantCode.length()>3) {
			generateURL+=variantCode.substring(0, 2)+variantCode.substring(variantCode.length()-2, variantCode.length());
		}else {
			generateURL+=variantCode;
		}
		if(userId.length()>3) {
			generateURL += userId.substring(0, 2);
		}else {
			generateURL += userId;
		}
		
		return generateURL;
		
	}
	
}