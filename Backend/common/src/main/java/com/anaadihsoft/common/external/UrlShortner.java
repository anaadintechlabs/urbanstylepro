package com.anaadihsoft.common.external;



public class UrlShortner {
	
	private String variantCode;
	private long userid;
	private String sku;
	
	public UrlShortner(String variantCode,long userid, String sku) {
		this.variantCode = variantCode;
		this.userid = userid;
		this.sku = sku;
	}
	
	public String generateLink() {
		
		String userId = String.valueOf(userid);
		
		String generateURL = "";
		if(sku.length()>3){
			generateURL+=sku.substring(0, 3);
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