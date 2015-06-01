package com.example.pruebagcm.modelos;

import java.io.Serializable;

public class Datos implements Serializable{
	// Atributos de clase
	private int id;
	//private String nombre;
	//private String apellido;
	private String titulo;
	private String mensaje;
	private String ruta_imagen;
		
	/**
	 * Constructor por defecto.
	 */
	public Datos(){	
	}
		
	/**
	 * Constructor que se le pasa 4 parametros.
	 * @param p_nombre
	 * @param p_apellido
	 * @param p_edad
	 * @param p_ruta_imagen
	 */
	public Datos(String p_titulo, String p_mensaje, String p_ruta_imagen){
		setTitulo(p_titulo);
		setMensaje(p_mensaje);
		setRutaImagen(p_ruta_imagen);
	}
		
	/**
	 * Constructor que se le pasa 5 parametros.
	 * @param p_id
	 * @param p_nombre
	 * @param p_apellido
	 * @param p_edad
	 * @param p_ruta_imagen
	 */
	public Datos(int p_id, String p_titulo, String p_mensaje, String p_ruta_imagen){
		setId(p_id);
		setTitulo(p_titulo);
		setMensaje(p_mensaje);
		setRutaImagen(p_ruta_imagen);
	}
		
	/*
	 * Getters y Setters
	 */
	/**
	 * 
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}
		
	/**
	 * 
	 * @param nombre
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMensaje() {
		return this.mensaje;
	}
		
	/**
	 * 
	 * @param apellido
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
		
	
	/**
	 * 
	 * @return
	 */
	public int getId(){
		return this.id;
	}
		
	/**
	 * 
	 * @param id
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRutaImagen(){
		return this.ruta_imagen;
	}
		
	/**
	 * 
	 * @param String
	 */
	public void setRutaImagen(String ruta_imagen){
		// Si ruta_imagen es null entonces se pone un valor predeterminado.
		if(ruta_imagen == null){
			//this.ruta_imagen = "No tiene imagen.";
		}else{
			this.ruta_imagen = ruta_imagen;
		}	
	}
} // Fin de la clase Datos.