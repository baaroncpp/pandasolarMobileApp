package com.panda.solar.Model.entities;

public class StockProduct {
	LeaseOffer leaseOffer;
	Long stockValue;

	public StockProduct(){super();}

	public LeaseOffer getLeaseOffer() {
		return leaseOffer;
	}

	public Long getStockValue() {
		return stockValue;
	}

	public void setLeaseOffer(LeaseOffer leaseOffer) {
		this.leaseOffer = leaseOffer;
	}

	public void setStockValue(Long stockValue) {
		this.stockValue = stockValue;
	}
}
