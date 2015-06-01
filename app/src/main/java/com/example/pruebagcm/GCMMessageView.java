package com.example.pruebagcm;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class GCMMessageView extends Activity {
    String message;
    TextView txtmsg;
 //String prueba="mensaje de prueba";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messageview);
 
      Bundle extras=getIntent().getExtras();
      
        message =extras.getString("aviso"); 
        
        // Retrive the data from GCMIntentService.java
        //Intent i = getIntent();
 
        //message = i.getStringExtra("AppMovilTesco");
 
        // Locate the TextView
        txtmsg = (TextView) findViewById(R.id.messageView);
 
        // Set the data into TextView
        
        //txtmsg.setText(prueba);
        txtmsg.setText(message);
    }
}
