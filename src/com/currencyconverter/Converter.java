package com.currencyconverter;

public class Converter {

	protected Double convert(Double quantity, Double valueInDollars, Double exchangeRate) throws Exception {
		Double quantityInDollars = 0.0, newValue;
		if (valueInDollars!=0.0){
			quantityInDollars = quantity / valueInDollars;
		}else{
			throw new Exception("No reference Value");
		}
		newValue = quantityInDollars * exchangeRate;
		return newValue;
	}
}