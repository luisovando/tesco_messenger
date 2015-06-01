package com.example.pruebagcm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


 public class RegistrarGCMBD extends Activity{

	 private static final String SENDER_ID ="1068838226217";
	 EditText user;
	 EditText pass;
	 Button validar;
	 String datoPost;
	 String datoP;
	 String regID_temporal;
	// final String regId="";
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.login);
      
	 GCMRegistrar.checkDevice(this);
	 GCMRegistrar.checkManifest(this);
		
	 user = (EditText) findViewById(R.id.txtUsuario);
	 pass = (EditText) findViewById(R.id.txtPass);
	 validar = (Button) findViewById(R.id.btnValidar);

	 validar.setOnClickListener(new OnClickListener() {
	 @Override
			public void onClick(View v) {

				final String 		 regId = GCMRegistrar
						.getRegistrationId(RegistrarGCMBD.this);
				//comprobar si ya existe algun registro
				
				if (regId.equals("")) {
					// Lanzamos el registro
					GCMRegistrar.register(RegistrarGCMBD.this, SENDER_ID);
					//lanzamos mensaje notificacion Toast
					Toast toast1 = Toast.makeText(getApplicationContext(),
							"Registrando Dispositivo", Toast.LENGTH_SHORT);
					toast1.show();
					regID_temporal=GCMRegistrar
							.getRegistrationId(RegistrarGCMBD.this);
					// Instanciamos y ejecutamos el proceso Asnyctask
					new LlamadaServidor().execute();

				} else {
					Log.v("javahispano", "Ya esta registrado");
					//lanzamos mensaje notificacion Toast
					
					regID_temporal=GCMRegistrar
							.getRegistrationId(RegistrarGCMBD.this);
					
					new LlamadaServidor().execute();
					
				}

			}
		});
	}// fin onCreate
	 
	 
	 
	 
	 
	 
	 
	 
	 class LlamadaServidor extends AsyncTask<Void, Void, Boolean>{

		 RegistrarGCMBD r= new RegistrarGCMBD();
		 
		 String regID=GCMRegistrar.getRegistrationId(RegistrarGCMBD.this);
		 
		@Override
		protected Boolean doInBackground(Void... arg0) {
			ArrayList parametros = new ArrayList();
			 parametros.add("Usuario");
			 parametros.add(user.getText().toString());
			 parametros.add("Contrasenia");
			 parametros.add(pass.getText().toString());
			 parametros.add("registroGCM");
			 parametros.add(regID_temporal);
			 // Llamada a Servidor Web PHP
			 try {
			    Post post = new Post();
			    JSONArray datos = post.getServerData(parametros,"http://www.tecnologicodecoacalco.edu.mx/AppMovil/login.php");
			    //JSONArray datos = post.getServerData(parametros,"http://noticiasmovil.com/AppMovil/login.php");
			 // No se puede poner localhost, carga la consola de Windows
			 // y escribe ipconfig/all para ver tu IP
			 if (datos != null && datos.length() > 0) {
			                         JSONObject json_data = datos.getJSONObject(0);
			                        // int numRegistrados = json_data.getInt("id_usuario");
			                        
			                         // JSONObject jsonObject = new JSONObject(post.respuesta);
			                         //String nombre = jsonObject.getString("Usuario");
			 //if (numRegistrados >= 0) {
				// if (nombre.equals("")) {	 
			                         datoP = "entro";
			                    
			                      
			   //                     }
			                      
			                     
			 //} else {
			   //                      Toast.makeText(getBaseContext(),
			     //                    "Usuario incorrecto1. " , Toast.LENGTH_SHORT)
		           //                  .show();
			         //                return false;
			                         }else{
			                        	 datoP= "no entro";
			                         }
			 } catch (Exception e) {
				 datoP = "error servidor";
			                         Toast.makeText(getBaseContext(),
			                         "Error al conectar con el servidor. ",
			                         Toast.LENGTH_LONG).show();
			 }
			 // FIN Llamada a Servidor Web PHP
			return true;
		}//fin doInBackground
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		
			if(user.getText().length()>0 && pass.getText().length()>0){
				if(datoP.equals("entro")){
					intentos(user.getText().toString());
					Toast toast1 = Toast.makeText(getApplicationContext(),
						"Bienvenido "+user.getText().toString() , Toast.LENGTH_SHORT);
					toast1.show();
				}else{
					Toast toast2 = Toast.makeText(getApplicationContext(),
							"Datos Incorrectos1 " , Toast.LENGTH_SHORT);
					toast2.show();
				}
				
				}else{
					Toast toast2 = Toast.makeText(getApplicationContext(),
							"Datos Incorrectos " , Toast.LENGTH_SHORT);
					toast2.show();
				}
		
			// Toast.makeText(getBaseContext(),
		      //         "dato POST: "+datoPost+"  /n  datpP: "+datoP, Toast.LENGTH_LONG).show();
			
		}
		 
	 }//fin class llamada servidor
	 
	 
	 
	 

	class Post {
		private InputStream is = null;
		private String respuesta = "";

		private void conectaPost(ArrayList parametros, String URL) {
			ArrayList nameValuePairs;
			try {

				HttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new HttpPost(URL);
				
				nameValuePairs = new ArrayList();

				if (parametros != null) {
					for (int i = 0; i < parametros.size() - 1; i += 2) {
						nameValuePairs.add(new BasicNameValuePair(
								(String) parametros.get(i), (String) parametros
										.get(i + 1)));
					}
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				}
				
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			} catch (Exception e) {

				Log.e("log_tag", "Error in http connection " + e.toString());
				datoPost = "error in http";
			} finally {
				

			}
		}
		private void getRespuestaPost() {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				respuesta = sb.toString();
				Log.e("log_tag", "Cadena JSon " + respuesta);
				datoPost="cadena jSon: "+respuesta;
				
			} catch (Exception e) {

				Log.e("log_tag", "Error converting result " + e.toString());
                 datoPost="Error converting resilt";
			}
		}

		@SuppressWarnings("finally")
		private JSONArray getJsonArray() {
			JSONArray jArray = null;
			try {

				jArray = new JSONArray(respuesta);

			} catch (Exception e) {

			} finally {
				return jArray;
			}
		}

		public JSONArray getServerData(ArrayList parametros, String URL) {
			conectaPost(parametros, URL);
			if (is != null) {
				getRespuestaPost();
			}
			if (respuesta != null && respuesta.trim() != "") {
				return getJsonArray();
			} else {
				return null;
			}
		}
	}
	public void intentos(String emisor){
		
		 Intent intent = new Intent(this, SendNotification.class);
		  // GestionarAcciones.class);
		 intent.putExtra("emisor", emisor);
		    startActivity(intent);
	}
}