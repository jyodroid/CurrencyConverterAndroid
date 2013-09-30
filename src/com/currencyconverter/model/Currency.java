package com.currencyconverter.model;

/*
 * Java Bean to carry currency data
 */
public class Currency {

	private String currencyName;
	private String currencyCountry;
	private Double dollarValue;
	
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
	public Double getDollarValue() {
		return dollarValue;
	}
	public void setDollarValue(Double dollarValue) {
		this.dollarValue = dollarValue;
	}
}
