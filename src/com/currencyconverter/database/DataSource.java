package com.currencyconverter.database;

import java.util.ArrayList;
import java.util.List;

import com.currencyconverter.model.Currency;

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
	
	public void addCurrency(Currency currency) throws SQLException{
		
		//Probamos que el currency ya no esté insertado en la base de datos
		Cursor cursor = database.
				  rawQuery("select * from currency where name = ?"
		, new String[] { currency.getCurrencyName()}); 
		
		//Se organizan los valores
		ContentValues values = new ContentValues();
		values.put("name", currency.getCurrencyName());
		values.put("country", currency.getCurrencyCountry());
		values.put("dollarvalue", currency.getDollarValue());
		
		if (cursor.getCount()==0){
		 
			// si no existe la moneda, se inserta en la base de datos
			database.insert("currency", null, values);
		}else{
			//Si ya existe, se actualiza
			
			database.update("currency", values, "name=?",
					new String[] { currency.getCurrencyName()});
		}
	}
	
    /*
     * name: getPreferedCurrency()
     * type: List<Currency>
     * @param: no
     * Description: Trae la lista de monedas preferidas
     */
	
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
	
    /*
     * name: clean()
     * type: void
     * @param: no
     * Description: Limpia la base de datos (por ahora solo utilizada para probar la aplicación)
     */
	
	public void clean(){
		database.delete("currency", null, null);
	}
	
    /*
     * name: getDollarValue
     * type: Double
     * @param: String currecyName
     * Description: Trae el valor en dolares de determinada moneda
     */
	
	public Double getDollarValue(String currencyName){
		
		Cursor cursor = database.
				  rawQuery("select dollarvalue from currency where name = ?"
		, new String[] { currencyName }); 		
		cursor.moveToFirst();
		
		return cursor.getDouble(0);
	}
}
