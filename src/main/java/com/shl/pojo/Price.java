package com.shl.pojo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Price implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int partNumber;
	double marketPrice;
	double salePrice;
	double offerPrice;
	String eyeBrow;
	
	
	public int getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}
	public double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getOfferPrice() {
		return offerPrice;
	}
	public void setOfferPrice(double offerPrice) {
		this.offerPrice = offerPrice;
	}
	public String getEyeBrow() {
		return eyeBrow;
	}
	public void setEyeBrow(String eyeBrow) {
		this.eyeBrow = eyeBrow;
	}
	
}
