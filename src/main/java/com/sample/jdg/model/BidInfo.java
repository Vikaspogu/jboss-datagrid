package com.sample.jdg.model;

public class BidInfo {

	private String bidderName;
	private Double bidderAmount;

	public BidInfo() {
		super();
	}

	public BidInfo(String bidderName, Double bidderAmount) {
		super();
		this.bidderName = bidderName;
		this.bidderAmount = bidderAmount;
	}

	public String getBidderName() {
		return bidderName;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public Double getBidderAmount() {
		return bidderAmount;
	}

	public void setBidderAmount(Double bidderAmount) {
		this.bidderAmount = bidderAmount;
	}

}
