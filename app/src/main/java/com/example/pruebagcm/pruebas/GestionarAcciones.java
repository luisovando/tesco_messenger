package com.example.pruebagcm.pruebas;

import com.example.pruebagcm.R;
import com.example.pruebagcm.R.id;
import com.example.pruebagcm.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GestionarAcciones extends Activity {

	 EditText cont;
	 EditText msj;
	 Button enviar;
	 
	 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enviar_notificaciones);

		 cont  = (EditText) findViewById(R.id.txtUsuario);
	   	 msj = (EditText) findViewById(R.id.txtPass);
	   	 enviar = (Button) findViewById(R.id.btnValidar); 
	   	
	   	 
	   	enviar.setOnClickListener(new OnClickListener() {
	   		public void onClick(View v) {
	   			Conexion handler = new Conexion();
	   			String txt = handler.post(
	   					"http://noticiasmovil.com/AppMovil/EnviarNotificaciones.php",
	   					cont.getText().toString(),
	   					msj.getText().toString());
	   			
	   			Toast toast1 = Toast.makeText(getApplicationContext(),
						txt, Toast.LENGTH_SHORT);
				toast1.show();
	   		}
	   	});

	}//fin onCreate

	
}
