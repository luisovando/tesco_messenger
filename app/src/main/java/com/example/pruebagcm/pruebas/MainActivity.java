package com.example.pruebagcm.pruebas;


import com.example.pruebagcm.R;
import com.example.pruebagcm.SendNotification;
import com.example.pruebagcm.R.id;
import com.example.pruebagcm.R.layout;
import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity{
	private static final String SENDER_ID ="1068838226217";
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.activity_main);
	
	
	GCMRegistrar.checkDevice(this);
	GCMRegistrar.checkManifest(this);
	 //final String regId = GCMRegistrar.getRegistrationId(this);
	 
	
	
	 
	 // Register Device Button
    Button regbtn = (Button) findViewById(R.id.registro);

    regbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	final String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
        	if (regId.equals("")) {
            		//Lanzamos el registro
	GCMRegistrar.register(MainActivity.this, SENDER_ID);
	Toast toast1 = Toast.makeText(getApplicationContext(),"Registering device", Toast.LENGTH_SHORT);
                         toast1.show();
            
	} else {
	//Log.v("javahispano", "Ya esta registrado");
		intentos();
	Toast toast1 = Toast.makeText(getApplicationContext(),"ya esta registrado1", Toast.LENGTH_SHORT);
    toast1.show();
   
	}
           
        }
    });
    Button unregbtn = (Button) findViewById(R.id.desregistro);

    unregbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	final String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
            if (!regId.equals("")) {
	//Lanzamos el registro
	GCMRegistrar.unregister(MainActivity.this);
	Toast toast1 = Toast.makeText(getApplicationContext(),"eliminando registro", Toast.LENGTH_SHORT);
                         toast1.show();
            
	} else {
	Log.v("javahispano", "Ya esta registrado");
	Toast toast1 = Toast.makeText(getApplicationContext(),"ya no esta registrado", Toast.LENGTH_SHORT);
    toast1.show();
	}
           
        }
    });
}
	
	public void intentos(){
		
		 Intent intent = new Intent(this, SendNotification.class);
		   
		    startActivity(intent);
	}


}
