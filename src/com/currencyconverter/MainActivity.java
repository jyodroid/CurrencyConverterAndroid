package com.currencyconverter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> 79f43014fecffda9e2e0af81707e7419047372b6
import android.app.ListActivity;
import android.content.Intent;

public class MainActivity extends ListActivity {

	private DataSource ds;
	private List<Currency> currencies;
	private ArrayAdapter<String> currencyPrefered;
	private TextView primaryCurrency;
	private EditText primaryCurrencyValue;
<<<<<<< HEAD
	private Double primaryDollarValue;
=======
>>>>>>> 79f43014fecffda9e2e0af81707e7419047372b6
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        primaryCurrency = (TextView) findViewById(R.id.txt_primary);
        primaryCurrencyValue = (EditText) findViewById(R.id.et_primary);
<<<<<<< HEAD
        primaryDollarValue = 1.0;
        
        ds = new DataSource(this);
        ds.open();
=======
        
        ds = new DataSource(this);
        ds.open();
        
>>>>>>> 79f43014fecffda9e2e0af81707e7419047372b6
        currencies = ds.getPreferedCurrency();
        ds.close();
        List<String> currenciesString = new ArrayList<String>();
        for (Currency currency : currencies) {
<<<<<<< HEAD
        	Double primaryValue = Double.parseDouble(primaryCurrencyValue.getText().toString());
        	Converter converter = new Converter();
        	Double converted = 1.0;
			try {
				converted = converter.convert(primaryValue, 
						primaryDollarValue, currency.getDollarValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
			if (currency.getCurrencyName().equals("USD")){
				continue;
			}
			
			String currencyString = 
					currency.getCurrencyName()+
					" from: "+currency.getCurrencyCountry()+
					" Convertion: "+converted;//Convertion to especifically currency 
=======
			String currencyString = 
					currency.getCurrencyName()+
					"from: "+currency.getCurrencyCountry()+
					" Convertion: "+currency.getDollarValue();
>>>>>>> 79f43014fecffda9e2e0af81707e7419047372b6
			currenciesString.add(currencyString);
		}
        
        currencyPrefered = 
        		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currenciesString); 
        
        setListAdapter(currencyPrefered);
    }
    
    public void changePrimaryCurrency(String selection){
<<<<<<< HEAD
    	
    	Converter converter = new Converter();
    	currencyPrefered.remove(selection);
    	String[] mySelection = selection.split(" ");
    	String newBase = mySelection[0]+" "+mySelection[1]+" "+mySelection[2]+" Amount";    	
    	Double converted = 1.0;
    	try {
			converted = converter.convert(1.0, primaryDollarValue, Double.valueOf(mySelection[4]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	String[] preferedStrings = primaryCurrency.getText().toString().split(" ");
    	String newPrefered = preferedStrings[0]+" "+preferedStrings[1]+" "+
    			preferedStrings[2]+" "+preferedStrings[3]+" Convertion";
    	
    	currencyPrefered.add(newPrefered+" "+converted.toString());
    	primaryDollarValue = Double.valueOf(mySelection[4]);
    	primaryCurrency.setText(newBase);
    }
    
    public void calculate(View view){
    	ds = new DataSource(this);
        ds.open();
        
        currencies = ds.getPreferedCurrency();
        ds.close();
        List<String> currenciesString = new ArrayList<String>();
        for (Currency currency : currencies) {
        	Double primaryValue = Double.parseDouble(primaryCurrencyValue.getText().toString());
        	Converter converter = new Converter();
        	Double converted = 1.0;
			try {
				converted = converter.convert(primaryValue, 
						primaryDollarValue, currency.getDollarValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	currency.getDollarValue(); 
			String currencyString = 
					currency.getCurrencyName()+
					" from: "+currency.getCurrencyCountry()+
					" Convertion: "+converted;//Convertion to especifically currency 
			currenciesString.add(currencyString);
		}
        
        currencyPrefered = 
        		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currenciesString); 
        
        setListAdapter(currencyPrefered);
=======
    	currencyPrefered.add(primaryCurrency.getText().toString()
    			+" "+primaryCurrencyValue.getText().toString());
    	currencyPrefered.remove(selection);
    	primaryCurrency.setText(selection);
>>>>>>> 79f43014fecffda9e2e0af81707e7419047372b6
    }
    
    public void addCurrency(View view){
    	Intent addIntent = new Intent(this, AddCurrencyActivity.class);
    	startActivity(addIntent);
    }
    
    @Override protected void onListItemClick(ListView listView, 
            View view, int position, long id) {
    		super.onListItemClick(listView, view, position, id);
    		Object o = getListAdapter().getItem(position);
<<<<<<< HEAD
=======
    		Toast.makeText(this, "Selección: " + Integer.toString(position)
    				+  " - " + o.toString(),Toast.LENGTH_LONG).show();
>>>>>>> 79f43014fecffda9e2e0af81707e7419047372b6
    		changePrimaryCurrency(o.toString());
    }
}
