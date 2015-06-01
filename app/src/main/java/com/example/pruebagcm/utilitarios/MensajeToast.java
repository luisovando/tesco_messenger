package com.example.pruebagcm.utilitarios;

import android.content.Context;
import android.widget.Toast;

public class MensajeToast {
	// Atributos de clase.
	private Context contexto;
	
	/**
	 * Constructor con un parametro que es el contexto.
	 * @param contexto
	 */
	public MensajeToast(Context contexto){
		this.contexto = contexto;
	}
	
	/**
	 * Metodo publico que devuelve el contexto.
	 */
	public Context getContexto(){
		return this.contexto;
	}
	
	/**
	 * Metodo publico que muestra un mensaje corto.
	 */
	public void mostrarMensajeCorto(String mensaje){
		Toast.makeText(getContexto(), mensaje, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Metodo publico que muestra un mensaje largo.
	 */
	public void mostrarMensajeLargo(String mensaje){
		Toast.makeText(getContexto(), mensaje, Toast.LENGTH_LONG).show();
	}
}
