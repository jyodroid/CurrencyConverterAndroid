package com.currencyconverter.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.currencyconverter.R;
import com.currencyconverter.database.DataSource;
import com.currencyconverter.model.Currency;
import com.currencyconverter.model.WebService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class AddCurrencyActivity extends Activity {

	private DataSource ds;
	private Currency currencyToAdd;
	private TextView result;
	private EditText name;
	private EditText country;
	private Button add;
	private Button search;
	private WebService ws;
	private HashMap<String, String> namesAndCountries;
    private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_currency);
        result = (TextView) findViewById(R.id.txt_result);
        name = (EditText) findViewById(R.id.et_name);
        country = (EditText) findViewById(R.id.et_country);
        add = (Button) findViewById(R.id.btn_add);
        search = (Button) findViewById(R.id.btn_search);
        add.setEnabled(false);
        ds = new DataSource(this);
        currencyToAdd = new Currency();
        ws = new WebService(); 
        
        //Dialogo de proceso
		pDialog = new ProgressDialog(AddCurrencyActivity.this);
	    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    pDialog.setMessage("Searching dollar value for currency...");
	    pDialog.setCancelable(true);
	    pDialog.setMax(100);
	}
	
    /*
     * @name: search
     * @type: void
     * @param: view:View
     * @Description: busca una moneda especificada por el usuario
     * 				si la encuentra activa el botón "+"
     */
	
	public void search(View view){
		
		//populate currencytoadd
		if (name.getText().length()!=0 && country.getText().length()!=0){
			
			search.setEnabled(false);
			currencyToAdd.setCurrencyName(name.getText().toString());
			currencyToAdd.setCurrencyCountry(country.getText().toString());
			
			//Inicializo el hilo de conexión
			Thread networkThread = new Thread() {
	    		@Override
	    		public void run() {
	    			try {
	    				currencyToAdd.setDollarValue(
	    						ws.getDollarValue(currencyToAdd.getCurrencyName()));
	    				
	    				result.post(new Runnable() {
							
							@Override
							public void run() {
				   				result.setText(currencyToAdd.getCurrencyName()+
			    						" from "+currencyToAdd.getCurrencyCountry());	
							}
						});
	    				
	    				add.post(new Runnable() {
							
							@Override
							public void run() {
								add.setEnabled(true);
							}
						});
	    				
	    			}catch (Exception e) {				
	    				//Para efectos de depuración de código
	    				Log.d("AddCurrencyActivity", "ERROR DE CONEXION "+
	    				" "+e.getMessage() + " " + e.getLocalizedMessage());
	    				e.printStackTrace();
	    				
	    				result.post(new Runnable() {
							
							@Override
							public void run() {
				   				result.setText(currencyToAdd.getCurrencyName()+
			    						" from "+currencyToAdd.getCurrencyCountry()+" not found!");	
							}
						});
	    			}finally{
	    				search.post(new Runnable() {
							
							@Override
							public void run() {
								search.setEnabled(true);
								
							}
						});
	    			}
	    		}
	        };
	        
			networkThread.start();
			
		}else{
			Toast.makeText(this, 
					"Insert name and currency country according ISO-4217",
					Toast.LENGTH_LONG).show();
		}
	}
	
    /*
     * @name: addCurrency
     * @type: void
     * @param: view:View
     * @Description: Agrega a la base de datos una moneda ya encontrada.
     */
	
	public void addCurrency(View view){
    	ds.open();
		ds.addCurrency(currencyToAdd);
    	ds.close();
    	Toast.makeText(this, 
				"Currency Added",
				Toast.LENGTH_LONG).show();
    	Intent backIntent = new Intent(this, MainActivity.class);
    	startActivity(backIntent);
    	finish();
	}
	
    /*
     * @name: addDefaultCurrencies
     * @type: void
     * @param: view:View
     * @Description: Agrega una colección de currencies que trae el sistema
     */
	
	public void addDefaultCurrencies(View view){
		AdderDefaulCurrencies adder = new AdderDefaulCurrencies();
		adder.execute();
	}
	
    /*
     * @name: clean
     * @type: void
     * @param: view:View
     * @Description: limpia la base de datos y deja la lista en blanco
     */

	public void clean(View view){
		ds.open();
		ds.clean();
		
        //Agrego la Moneda de referencia
        Currency dollarCurrency = new Currency();
        dollarCurrency.setCurrencyName("USD");
        dollarCurrency.setCurrencyCountry("United_Estates_of_America");
        dollarCurrency.setDollarValue(1.0);
        ds.addCurrency(dollarCurrency);
		
        //Cierro la conexión
        ds.close();
		Toast.makeText(this, 
				"Done, no currencies in list",
				Toast.LENGTH_LONG).show();
	}
	
	private class AdderDefaulCurrencies extends AsyncTask<Void, Integer, Boolean>{
		 @Override
		    protected Boolean doInBackground(Void... params) {
		 
			// Colección de denominaciones
				namesAndCountries = new HashMap<String, String>();
				
				//LLeno la colección con los nombres de paises 
		        namesAndCountries.put("COP","Colombia");
		        namesAndCountries.put("CAD","Canada");
		        namesAndCountries.put("SGD","Singapore");
		        namesAndCountries.put("VND","Vietnam");
		        namesAndCountries.put("DZD","Algeria");
		        namesAndCountries.put("EUR","Andorra");
		        namesAndCountries.put("RON","Romania");
		        namesAndCountries.put("XCD","Anguilla");
		        namesAndCountries.put("SVC","Salvador");
		        namesAndCountries.put("SEK","Sweden");
		        namesAndCountries.put("DOP","Dominican_Republic");
		        namesAndCountries.put("AUD","Australia");
		        namesAndCountries.put("PEN","Peru");
		        namesAndCountries.put("VEF","Venezuela");
		        namesAndCountries.put("BHD","Bahrain");
		        namesAndCountries.put("IQD","Iraq");
		        namesAndCountries.put("RUB","Russia");
		        namesAndCountries.put("EUR","European_Union");
		        namesAndCountries.put("UAH","Ukraine");
		        namesAndCountries.put("PYG","Paraguay");
		        namesAndCountries.put("BMD","Bermuda");
		        namesAndCountries.put("KPW","North_Korea");
		        namesAndCountries.put("BOB","Bolivia");
		        namesAndCountries.put("BWP","Botswana");
		        namesAndCountries.put("NOK","Bouvet_Island");
		        namesAndCountries.put("BRL","Brazil");
		        namesAndCountries.put("GBP","British_Indian_O._Terr.");
		        namesAndCountries.put("BND","Brunei_Darussalam");
		        namesAndCountries.put("JPY","Japan");
		        namesAndCountries.put("CNY","China");
		        
		        ds.open();
		        
		        //Ingreso a la base de datos los currency de la colección
		        Iterator<Entry<String, String>> it = 
		        		namesAndCountries.entrySet().iterator();
		        
		        while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					currencyToAdd.setCurrencyCountry(entry.getValue().toString());
					currencyToAdd.setCurrencyName(entry.getKey().toString());
					
					//Traigo los valores del ws
					try {
						currencyToAdd.setDollarValue(
								ws.getDollarValue(currencyToAdd.getCurrencyName()));
						ds.addCurrency(currencyToAdd);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
		        }
		        ds.close();
		        return true;
		    }
		 
		    @Override
		    protected void onProgressUpdate(Integer... values) {
		        int progress= values[0].intValue();
		 
		        pDialog.setProgress(progress);
		    }
		 
		    @Override
		    protected void onPreExecute() {
		    	pDialog.setProgress(0);
		    	pDialog.setMax(100);
		    	pDialog.show();
		    }
		 
		    @Override
		    protected void onPostExecute(Boolean result) {
		        if(result)
			    	Toast.makeText(AddCurrencyActivity.this, 
							"Currencies Added",
							Toast.LENGTH_LONG).show();
		        pDialog.dismiss();
		    }
		 
		    @Override
		    protected void onCancelled() {
		        Toast.makeText(AddCurrencyActivity.this, "Default durrencies cancelled",
		                Toast.LENGTH_SHORT).show();
		    }
	}
	
	
	@Override
	public void onBackPressed() {
	   Intent mainIntent = new Intent(this, MainActivity.class);
	   startActivity(mainIntent);
	   finish();
	}
}