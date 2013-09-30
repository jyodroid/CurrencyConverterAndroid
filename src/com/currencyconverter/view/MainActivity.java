package com.currencyconverter.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.currencyconverter.R;
import com.currencyconverter.database.DataSource;
import com.currencyconverter.model.Converter;
import com.currencyconverter.model.Currency;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;
import android.content.Intent;

public class MainActivity extends ListActivity {

	private DataSource ds;
	private ArrayAdapter<String> currencyPrefered;
	private TextView primaryCurrency;
	private EditText primaryCurrencyQuantity;
	private Double quantity;
	private Double primaryCurrencyDollarValue ;
	private String primaryCurrencyName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        primaryCurrency = (TextView) findViewById(R.id.txt_primary);
        primaryCurrencyQuantity = (EditText) findViewById(R.id.et_primary);
        quantity = Double.parseDouble(primaryCurrencyQuantity.getText().toString());
        
        //Inicializo la moneda de referencia: el dolar
        primaryCurrencyDollarValue = 1.0;
        primaryCurrencyName = "USD";
        
        //Inicializo el manejador de base de datos
        ds = new DataSource(this);
        
        //Inicializo la lista de favoritos
        refreshFavoriteList();
    }
    
    /*
     * @name: changePrimaryCurrency
     * @type: void
     * @param: String selection
     * @Description: Cambia la moneda de referencia para hacer los computos 
     */
    
    public void changePrimaryCurrency(String selection){
    	
    	//Se retira de la lista de monedas general
    	currencyPrefered.remove(selection);
    	
    	//Se extrae el nombre y el pais de la nueva moneda de referencia
    	String[] mySelection = selection.split(" ");
    	String name = mySelection[0];
    	String country = mySelection[2];
    	
    	//Actualizo las variables globales
    	primaryCurrencyName = name;
    	
    	//Se trae el valor en dolares de la nueva moneda de referencia
    	ds.open();
    	
    	primaryCurrencyDollarValue = ds.getDollarValue(name);
    	ds.close();
    	
    	//Se resetea el valor de quantity
    	quantity = 1.0;
    	primaryCurrencyQuantity.setText("1");
    	
    	//se transforma la lista en base a la nueva moneda de referencia
    	refreshFavoriteList();
    	
        //Pongo el nombre de la nueva moneda de referencia
    	primaryCurrency.setText(name+" from "+country);
    }
    
    /*
     * @name: calculate
     * @type: void
     * @param: view:View
     * @Description: hace el calculo de cambio con una cantidad
     * 				especificada por el usuario
     */
    
    public void calculate(View view){
    	
    	//actualizo el valor de quantity
    	quantity = Double.parseDouble(
    			primaryCurrencyQuantity.getText().toString());
    	
    	//actualizo la lista de favoritos con las nuevas cantidades
    	refreshFavoriteList();
    }
    
    //Cambia a la activity que me permite adicionar monedas favoritas
    public void addCurrency(View view){
    	Intent addIntent = new Intent(this, AddCurrencyActivity.class);
    	startActivity(addIntent);
    	finish();
    }
    
    //Para informar como se cierra el pad numérico
	public void informNumericPad(View view){
    	Toast.makeText(this, "To exit numeric pad please click back button", 
    			Toast.LENGTH_LONG).show();
    }
    
    //Para que "escuche" cuando el usuario quiere cambiar de moneda de referencia
    @Override protected void onListItemClick(ListView listView, 
            View view, int position, long id) {
    		super.onListItemClick(listView, view, position, id);
    		Object o = getListAdapter().getItem(position);
    		changePrimaryCurrency(o.toString());
    }
    
    /*
     * @name: refreshFavoriteList
     * @type: void
     * @param: no
     * @Description: Actualiza la lista de monedas favoritas y sus valores
     */
    private void refreshFavoriteList(){
    	
    	DecimalFormat df = new DecimalFormat("#,###,###,###.#####");
    	List<Currency> currencies;
    	
    	ds.open();
    	
        //Extraigo la lista de la base de datos y cierro la conexión
        currencies = ds.getPreferedCurrency();
        ds.close();
         
        List<String> currenciesString = new ArrayList<String>();
        
        for (Currency currency : currencies) {
         	
        	//No hago nada si es la moneda de referencia
 			if (currency.getCurrencyName().equals(primaryCurrencyName)){
 				continue;
 			}
         	
         	Converter converter = new Converter();
                 	
         	Double converted = 0.0;
 			
	         try {
	        	ds.open();
	        	currency.setDollarValue(ds.getDollarValue
	        			(currency.getCurrencyName()));
	        	ds.close();
	 			converted = converter.convert(quantity, 
	 					primaryCurrencyDollarValue, currency.getDollarValue());
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		}
	         	
 			String currencyString = 
 					currency.getCurrencyName()+
 					" from: "+currency.getCurrencyCountry()+
 					" Convertion: "+ df.format(converted);//Convertion to especifically currency 
 			
 			currenciesString.add(currencyString);
 		}
         
         currencyPrefered = 
         		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currenciesString); 
         setListAdapter(currencyPrefered);
    }
    
	@Override
	public void onBackPressed() {
		finish();
	}
}