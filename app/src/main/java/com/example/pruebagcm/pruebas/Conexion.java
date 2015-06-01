package com.example.pruebagcm.pruebas;

import java.util.ArrayList;

import java.util.List;

 

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;

import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicNameValuePair;

 

import org.apache.http.util.EntityUtils;

public class Conexion {

	public String post(String posturl,String contacto,String mensaje){
			//,String contacto,String mensaje) {

		try {

			HttpClient httpclient = new DefaultHttpClient();

			/*
			 * Creamos el objeto de HttpClient que nos permitira conectarnos
			 * mediante peticiones http
			 */

			HttpPost httppost = new HttpPost(posturl);

			/*
			 * El objeto HttpPost permite que enviemos una peticion de tipo POST
			 * a una URL especificada
			 */

			// A�ADIR PARAMETROS

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("Contacto",contacto));

			params.add(new BasicNameValuePair("Mensaje", mensaje));

			/*
			 * Una vez a�adidos los parametros actualizamos la entidad de
			 * httppost, esto quiere decir en pocas palabras anexamos los
			 * parametros al objeto para que al enviarse al servidor envien los
			 * datos que hemos a�adido
			 */

			httppost.setEntity(new UrlEncodedFormEntity(params));

			/* Finalmente ejecutamos enviando la info al server */

			HttpResponse resp = httpclient.execute(httppost);

			HttpEntity ent = resp.getEntity();/* y obtenemos una respuesta */

			String text = EntityUtils.toString(ent);

			return text;

		}

		catch (Exception e) {
			return "error";
		}

	}

}
//prueba 1