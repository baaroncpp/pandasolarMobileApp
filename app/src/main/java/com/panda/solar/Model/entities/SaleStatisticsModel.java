package com.panda.solar.Model.entities;

import java.util.List;

public class SaleStatisticsModel {	
	private Sale sale;
	private CustomerMeta customer;
	private List<LeasePayment> payments;
	private TotalLeasePayments totalLeasePayments;
	private Lease lease;

	public SaleStatisticsModel() {}

	public Sale getSale() {
		return sale;
	}

	public Lease getLease(){return lease;}

	public CustomerMeta getCustomer() {
		return customer;
	}

	public List<LeasePayment> getPayments() {
		return payments;
	}

	public TotalLeasePayments getTotalLeasePayments() {
		return totalLeasePayments;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public void setCustomer(CustomerMeta customer) {
		this.customer = customer;
	}

	public void setPayments(List<LeasePayment> payments) {
		this.payments = payments;
	}

	public void setTotalLeasePayments(TotalLeasePayments totalLeasePayments) {
		this.totalLeasePayments = totalLeasePayments;
	}

	public void setLease(Lease lease){
		this.lease = lease;
	}
}
