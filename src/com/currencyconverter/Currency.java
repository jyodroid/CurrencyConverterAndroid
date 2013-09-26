package com.currencyconverter;

/*
 * Java Bean to carry currency data
 */
public class Currency {

	private String currencyName;
	private String currencyCountry;
	private float dollarValue;
	
	
	//Getters and setters
	//Unique name like USD
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	//Currency country
	public String getCurrencyCountry() {
		return currencyCountry;
	}
	public void setCurrencyCountry(String currencyCountry) {
		this.currencyCountry = currencyCountry;
	}
	
	//Dollar as referenced currency
	public float getDollarValue() {
		return dollarValue;
	}
	public void setDollarValue(float dollarValue) {
		this.dollarValue = dollarValue;
	}
}
