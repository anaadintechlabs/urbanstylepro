package com.anaadihsoft.common.DTO;

import com.anaadihsoft.common.master.ProductVariant;

public class ProductVariantMini {

	private long productVariantId;
	
	private String uniqueprodvarId;
	
	private long categoryId;
	
	private String sku;
	 
	 private String variantName;
	 	 
	 private String mainImageUrl;
	 
	 private double displayPrice;
		
		private double manufacturerSuggesstedPrice;
	 
	 private boolean salesEnabled;
	 
	 private double salesPrice;
	 
	 public ProductVariantMini()
	 {
		 
	 }
	 public ProductVariantMini(ProductVariant pv)
	 {
		 this.categoryId=pv.getCategoryId();
		 this.displayPrice=pv.getDisplayPrice();
		 this.mainImageUrl=pv.getMainImageUrl();
		 this.manufacturerSuggesstedPrice=pv.getManufacturerSuggesstedPrice();
		 this.productVariantId=pv.getProductVariantId();
		 this.salesEnabled=pv.isSalesEnabled();
		 this.salesPrice=pv.getSalesPrice();
		 this.sku=pv.getSku();
		 this.uniqueprodvarId=pv.getUniqueprodvarId();
		 this.variantName=pv.getVariantName();
	 }
	 
}
