package com.currencyconverter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;

public class SplashActivity extends Activity {

	//Duraci�n del splash de 5 segundos
	private long SPLASH_DELAY = 1000;
	
	//Manejador de base de datos
	private DataSource ds;
	private Currency currency;
	private Double dollarValue = 1.0;
	private WebService ws;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        
        //Inicializo el manejador
        ds = new DataSource(this);
        currency = new Currency();
        ds.open();
        Currency dollarCurrency = new Currency();
        //Moneda de referencia
        
        dollarCurrency.setCurrencyName("USD");
        dollarCurrency.setCurrencyCountry("United_Estates_of_America");
        dollarCurrency.setBaseValue(1);
        dollarCurrency.setFavorite(1);
        dollarCurrency.setDollarValue(1.0);
        ds.addCurrency(dollarCurrency);
        //Creo la tarea de pasar a la actividad principal 
        TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				HashMap<String, String> namesAndCountries = new HashMap<String, String>();
				
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
				
		        //Ingreso a la base de datos los currency de la colección
		        Iterator<Entry<String, String>> it = namesAndCountries.entrySet().iterator();
		        while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					currency.setCurrencyCountry(entry.getValue().toString());
					currency.setCurrencyName(entry.getKey().toString());
					
					//Se trae del webservice el valor
					ws = new WebService();
					
					//Conexion con WS
					Thread networkThread = new Thread() {
						@Override
						public void run() {
							try {
								dollarValue = ws.getDollarValue(currency.getCurrencyName());
							}
							catch (Exception e) {
								
								//Para efectos de depuración de código
								Log.d("SplashActivity", "ERROR DE CONEXION "+" "+e.getMessage() + " " + e.getLocalizedMessage());
								e.printStackTrace();
							}
						}
					};
					networkThread.run();
					currency.setDollarValue(dollarValue);
					ds.addCurrency(currency);
				}
		        
		        ds.close();
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				finish();//El usuario no pueda regresar al splash
			}	
		};
		
        //Programo la tarea para que se ejecute en 5 segundos
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);
    }
}
