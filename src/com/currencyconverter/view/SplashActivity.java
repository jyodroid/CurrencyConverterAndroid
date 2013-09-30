package com.currencyconverter.view;

import java.util.ArrayList;

import com.currencyconverter.R;
import com.currencyconverter.database.DataSource;
import com.currencyconverter.model.Currency;
import com.currencyconverter.model.WebService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;

/*
 * @author: john
 * Activity para actualizar la base de datos si ya existe o crearla en blanco
 * en caso de no existir
 */

public class SplashActivity extends Activity {
	
	//Manejador de base de datos
	private DataSource ds;
	private Currency currency;
	private WebService ws;
	private ProgressBar progressBar;
	private ArrayList<Currency> prefered;
	private Intent mainIntent;
	private int progress = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.pb_splash);
        progressBar.setProgress(progress);
        //Inicializo el manejador
        ds = new DataSource(this);
        ds.open();
        
        //Inicializo la lista de favoritos y el contenedor
        prefered = (ArrayList<Currency>) ds.getPreferedCurrency();
        currency = new Currency();
        
        //Agrego si no existe la Moneda de referencia
        Currency dollarCurrency = new Currency();
        dollarCurrency.setCurrencyName("USD");
        dollarCurrency.setCurrencyCountry("United_Estates_of_America");
        dollarCurrency.setDollarValue(1.0);
        ds.addCurrency(dollarCurrency);
        
		//Se inicializa el webservice
		ws = new WebService();
		
		//Se inicializa el intent para pasar a la activity principal
    	mainIntent = 
  		  		new Intent(SplashActivity.this, MainActivity.class);
    	
    	//Actualizamos los valores
    	UpdaterCurrencies updater = new UpdaterCurrencies();
    	updater.execute();
		
    }
    
	private class UpdaterCurrencies extends AsyncTask<Void, Integer, Boolean>{
		 
		@Override    
		protected Boolean doInBackground(Void... params) {
		        
			ds.open();
		    //Actaulizo los valores
            for (Currency itCurrency : prefered) {
				currency.setCurrencyName(itCurrency.getCurrencyName());
				currency.setCurrencyCountry(itCurrency.getCurrencyCountry());
            	// No actualizo si es el dolar
            	if (!currency.getCurrencyName().equals("USD")){
            		try {
        				currency.setDollarValue(
        						ws.getDollarValue(currency.getCurrencyName()));
        				ds.addCurrency(currency);
        			}
        			catch (Exception e) {				
        				//Para efectos de depuración de código
        				Log.d("SplashActivity", "ERROR DE CONEXION "+
        				" "+e.getMessage() + " " + e.getLocalizedMessage());
        				e.printStackTrace();
        			}finally{
        				progress++;
        			}
            	}
            }
		        
		        ds.close();
		        return true;
		    }
		 
		    @Override
		    protected void onProgressUpdate(Integer... values) {
		        progressBar.setProgress(progress);
		    }
		 
		    @Override
		    protected void onPreExecute() {
		    	progressBar.setProgress(0);
		    	progressBar.setMax(100);
		    }
		 
		    @Override
		    protected void onPostExecute(Boolean result) {
		        if(result)
		        	startActivity(mainIntent);
		        	finish();//Destruimos esta activity para prevenir que el usuario retorne aqui presionando el boton Atras.
		    }
		 
		    @Override
		    protected void onCancelled() {
		    	finish();
		    }
	}
}