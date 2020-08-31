package com.panda.solar.Model.entities;

import java.util.Date;

public class TotalLeasePayments implements java.io.Serializable {

	private String id;
	private String leaseid;
	private float lastpaidamount;
	private float totalamountpaid;
	private float totalamountowed;
	private float residueamount;
	private int times;
	private Date createdon;
	private Date nextpaymentdate;
	private Date updatedon;

	public TotalLeasePayments() {
	}

	public String getId() {
		return id;
	}

	public String getLeaseid() {
		return leaseid;
	}

	public float getLastpaidamount() {
		return lastpaidamount;
	}

	public float getTotalamountpaid() {
		return totalamountpaid;
	}

	public float getTotalamountowed() {
		return totalamountowed;
	}

	public float getResidueamount() {
		return residueamount;
	}

	public int getTimes() {
		return times;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public Date getNextpaymentdate() {
		return nextpaymentdate;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLeaseid(String leaseid) {
		this.leaseid = leaseid;
	}

	public void setLastpaidamount(float lastpaidamount) {
		this.lastpaidamount = lastpaidamount;
	}

	public void setTotalamountpaid(float totalamountpaid) {
		this.totalamountpaid = totalamountpaid;
	}

	public void setTotalamountowed(float totalamountowed) {
		this.totalamountowed = totalamountowed;
	}

	public void setResidueamount(float residueamount) {
		this.residueamount = residueamount;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public void setNextpaymentdate(Date nextpaymentdate) {
		this.nextpaymentdate = nextpaymentdate;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}
}
