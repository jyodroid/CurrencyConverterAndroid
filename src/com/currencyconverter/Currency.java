package com.currencyconverter;

/*
 * Java Bean to carry currency data
 */
public class Currency {

	private String currencyName;
	private String currencyCountry;
	private Double dollarValue;
	private int favorite;
	private int baseValue;
	
	//by default favorite and baseValue are false = 0
	public Currency(){
		setFavorite(0);
		setBaseValue(0);
	}
	
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
	public int isFavorite() {
		return favorite;
	}
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}
	public int isBaseValue() {
		return baseValue;
	}
	public void setBaseValue(int baseValue) {
		this.baseValue = baseValue;
	}
}
