package com.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class AddCurrencyActivity extends Activity {

	private SQLiteDatabase database;
	private DataSource ds;
	private Currency currencyToAdd;
	private TextView result;
	private EditText name;
	private EditText country;
	private Button add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_currency);
        ds = new DataSource(this);
        ds.open();
        currencyToAdd = new Currency();
        result = (TextView) findViewById(R.id.txt_result);
        name = (EditText) findViewById(R.id.et_name);
        country = (EditText) findViewById(R.id.et_country);
        add = (Button) findViewById(R.id.btn_add);
        add.setEnabled(false);
	}
	
	public void search(View view){
		//populate currencytoadd
		if (name.getText().length()!=0){
			String sql = "select * from currency where name = "+name.getText();
			//database.execSQL(sql);
			
			ds.close();
			add.setEnabled(true);
		}else if (country.getText().length()!=0){
			String sql = "select * from currency where name = "+country.getText();
			//database.execSQL(sql);
			add.setEnabled(true);
		}else{
			Toast.makeText(this, "Ingrese el nombre o el pais ",Toast.LENGTH_LONG).show();
		}
		
		String[] currencyResult = 
				result.getText().toString().split(" ");
	}
	
	public void addCurrency(View view){
    	ds.addCurrency(currencyToAdd);
    	ds.close();
    	Intent backIntent = new Intent(this, MainActivity.class);
    	startActivity(backIntent);
    	finish();
	}
}
