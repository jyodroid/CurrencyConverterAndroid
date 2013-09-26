package com.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class AddCurrencyActivity extends Activity {

	private DataSource ds;
	private Currency currencyToAdd;
	private TextView result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_currency);
        ds = new DataSource(this);
        ds.open();
        currencyToAdd = new Currency();
        result = (TextView) findViewById(R.id.txt_result);
	}
	
	public void searchName(View view){
		//populate currencytoadd
		String[] currencyResult = 
				result.getText().toString().split(" ");
		
		currencyToAdd.setCurrencyName(currencyResult[0]);
		currencyToAdd.setCurrencyCountry(currencyResult[2]);
		//Traer el valor actual de la moneda seleccionada y guardarlo en dollar value.
		//Ver ejemplo de Angee http://www.firstamong.com/building-android-currency-converter/
	}
	
	public void addCurrency(View view){
    	ds.addCurrency(currencyToAdd);
    	ds.close();
    	Intent backIntent = new Intent(this, MainActivity.class);
    	startActivity(backIntent);
    	finish();
	}
}
