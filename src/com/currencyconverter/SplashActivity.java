package com.currencyconverter;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;

public class SplashActivity extends Activity {

	//Duración del splash de 5 segundos
	private long SPLASH_DELAY = 5000;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        
        //Creo la tarea de pasar a la actividad principal 
        TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
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
