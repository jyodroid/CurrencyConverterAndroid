package com.currencyconverter;

import java.util.ArrayList;
import java.util.List;

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
	private List<Currency> currencies;
	private ArrayAdapter<String> currencyPrefered;
	private TextView primaryCurrency;
	private EditText primaryCurrencyValue;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        primaryCurrency = (TextView) findViewById(R.id.txt_primary);
        primaryCurrencyValue = (EditText) findViewById(R.id.et_primary);
        
        ds = new DataSource(this);
        ds.open();
        
        currencies = ds.getPreferedCurrency();
        ds.close();
        List<String> currenciesString = new ArrayList<String>();
        for (Currency currency : currencies) {
			String currencyString = 
					currency.getCurrencyName()+
					"from: "+currency.getCurrencyCountry()+
					" Convertion: "+currency.getDollarValue();
			currenciesString.add(currencyString);
		}
        
        currencyPrefered = 
        		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currenciesString); 
        
        setListAdapter(currencyPrefered);
    }
    
    public void changePrimaryCurrency(String selection){
    	currencyPrefered.add(primaryCurrency.getText().toString()
    			+" "+primaryCurrencyValue.getText().toString());
    	currencyPrefered.remove(selection);
    	primaryCurrency.setText(selection);
    }
    
    public void addCurrency(View view){
    	Intent addIntent = new Intent(this, AddCurrencyActivity.class);
    	startActivity(addIntent);
    }
    
    @Override protected void onListItemClick(ListView listView, 
            View view, int position, long id) {
    		super.onListItemClick(listView, view, position, id);
    		Object o = getListAdapter().getItem(position);
    		Toast.makeText(this, "Selección: " + Integer.toString(position)
    				+  " - " + o.toString(),Toast.LENGTH_LONG).show();
    		changePrimaryCurrency(o.toString());
    }
}
