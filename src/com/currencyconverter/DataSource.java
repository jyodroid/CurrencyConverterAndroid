package com.currencyconverter;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSource {

	private SQLiteDatabase database;
	private SQLiteOpenHelper dbHelper;
	
	public DataSource(Context context){
		dbHelper = new MySqlLiteHelper(context, "currency", null, 1);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public void addCurrency(Currency currency){
		ContentValues values = new ContentValues();
		values.put("name", currency.getCurrencyName());
		values.put("country", currency.getCurrencyCountry());
		values.put("dollarvalue", currency.getDollarValue());
		database.insert("currency", null, values);
	}
	
	public List <Currency> getPreferedCurrency(){
		
		List<Currency> currencies = new ArrayList<Currency>();
		String[] columns = {"name", "country", "dollarvalue"};
		
		Cursor cursor = database.query("currency", columns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Currency currency = new Currency();
			currency.setCurrencyName(cursor.getString(0));
			currency.setCurrencyCountry(cursor.getString(1));
			currency.setDollarValue(cursor.getDouble(2));
			currencies.add(currency);
			cursor.moveToNext();
			
		}
		
		return currencies;
	}
	
	public void clean(){
		database.delete("currency", null, null);
	}
}
