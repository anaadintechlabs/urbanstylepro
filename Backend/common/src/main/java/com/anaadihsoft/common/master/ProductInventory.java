package com.anaadihsoft.common.master;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductInventory {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;

		@ManyToOne 
		@JoinColumn(name = "Variant")
		private ProductVariant productVariant;



		@ManyToOne
		@JoinColumn(name = "warehouse_id")
		private WarehouseInfo warehouse;
		
		@ManyToOne
		@JoinColumn(name = "vendor_id")
		private User user;		



		private long qty;

		private double stockCost;

		private double avgSalesPrice;

		private double standardSalesPrice;

		private long reservedQty;

		private long holdingBalance;

		private double reminderPoint;

		private Date createdDate;


		private Date modifiedDate;

		private String createdBy;
		private String modifiedBy;
		
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public ProductVariant getProductVariant() {
			return productVariant;
		}
		public void setProductVariant(ProductVariant productVariant) {
			this.productVariant = productVariant;
		}
		public WarehouseInfo getWarehouse() {
			return warehouse;
		}
		public void setWarehouse(WarehouseInfo warehouse) {
			this.warehouse = warehouse;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public long getQty() {
			return qty;
		}
		public void setQty(long qty) {
			this.qty = qty;
		}
		public double getStockCost() {
			return stockCost;
		}
		public void setStockCost(double stockCost) {
			this.stockCost = stockCost;
		}
		public double getAvgSalesPrice() {
			return avgSalesPrice;
		}
		public void setAvgSalesPrice(double avgSalesPrice) {
			this.avgSalesPrice = avgSalesPrice;
		}
		public double getStandardSalesPrice() {
			return standardSalesPrice;
		}
		public void setStandardSalesPrice(double standardSalesPrice) {
			this.standardSalesPrice = standardSalesPrice;
		}
		public long getReservedQty() {
			return reservedQty;
		}
		public void setReservedQty(long reservedQty) {
			this.reservedQty = reservedQty;
		}
		public long getHoldingBalance() {
			return holdingBalance;
		}
		public void setHoldingBalance(long holdingBalance) {
			this.holdingBalance = holdingBalance;
		}
		public double getReminderPoint() {
			return reminderPoint;
		}
		public void setReminderPoint(double reminderPoint) {
			this.reminderPoint = reminderPoint;
		}
		
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public Date getModifiedDate() {
			return modifiedDate;
		}
		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}
		

}
