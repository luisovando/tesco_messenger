package com.example.pruebagcm.database;


import com.example.pruebagcm.modelos.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	// Ruta por defecto de las bases de datos en el sistema Android.
	private static String RUTA_BASE_DATOS = "/data/data/com.example.pruebagcm/databases/";
		 
	// Nombre de la Base de Datos.
	private static String NOMBRE_BASE_DATOS = "appMovilTesco";
		
	// Version de la Base de Datos.
	private static final int VERSION_BASE_DATOS = 1;
		 
	// Objeto Base de Datos.
	private SQLiteDatabase base_datos;
		 
	// Objeto Contexto.
	private Context contexto;
		
	// Constante privada
	private String SENTENCIA_SQL_CREAR_BASE_DATOS = "CREATE TABLE if not exists Historial (_id INTEGER PRIMARY KEY autoincrement, " +
		"titulo TEXT, mensaje TEXT, ruta_imagen TEXT)";
		
	/**
	 * Constructor
	 * Toma referencia hacia el contexto de la aplicación que lo invoca para poder acceder a los 'assets' y 
	 * 'resources' de la aplicación.
	 * Crea un objeto DBOpenHelper que nos permitirá controlar la apertura de la base de datos.
	 * @param context
	*/
	public DatabaseHandler(Context context) {
		super(context, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
		this.contexto = context;
	}
			 
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Se ejecuta la sentencia SQL de creación de la tabla personas.
	    db.execSQL(SENTENCIA_SQL_CREAR_BASE_DATOS);
	}
			 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    // Se elimina la versión anterior de la tabla historial.
	    db.execSQL("DROP TABLE IF EXISTS Historial");
	 
	    // Se crea la nueva versión de la tabla historial.
	    db.execSQL(SENTENCIA_SQL_CREAR_BASE_DATOS);
	}
	
	/**
	 * Metodo publico para insertar una nueva notificacion.
	 */
	public void insertarPersona(Datos dato){
		ContentValues valores = new ContentValues();
		valores.put("titulo", dato.getTitulo());
		valores.put("mensaje", dato.getMensaje());
		
		valores.put("ruta_imagen", dato.getRutaImagen());
		this.getWritableDatabase().insert("Historial", null, valores);
	}
	
	/**
	 * Metodo publico para actualizar una persona.
	 */
	public void actualizarRegistros(int id, String titulo, String mensaje, String ruta_imagen){
		ContentValues actualizarDatos = new ContentValues();  
		actualizarDatos.put("titulo", titulo);
		actualizarDatos.put("mensaje", mensaje);
		actualizarDatos.put("ruta_imagen", ruta_imagen);
		String where = "_id=?";
		String[] whereArgs = new String[] {String.valueOf(id)};
		
		try{    
		    this.getReadableDatabase().update("Historial", actualizarDatos, where, whereArgs);
		}
		catch (Exception e){
		    String error =  e.getMessage().toString();
		}
	}
	
	/**
	 * Metodo publico que retorna una persona especifica.
	 * @param id
	 * @return
	 */
	public Datos getNotificacion(int p_id) {
	    String[] columnas = new String[]{"_id", "titulo", "mensaje", "ruta_imagen"};
	    Cursor cursor = this.getReadableDatabase().query("Historial", columnas, "_id" + "= " + p_id,null, null, null, null);
	
	    if (cursor != null){
	    	cursor.moveToFirst();
	    }
	    	
		Datos dato = new Datos(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
		    
		// Retorna la persona especifica.
		return dato;
	}
		
	/**
	 * Metodo publico que cierra la base de datos.
	 */
	public void cerrar(){
		this.close();
	}
		
	/**
	 * Metodo publico que devuelve todas las personas.
	 * @return
	 */
	public Cursor obtenerTodasNotificaciones(){
		String[] columnas = new String[]{"_id", "titulo", "mensaje",  "ruta_imagen"};
		Cursor cursor = this.getReadableDatabase().query("Historial", columnas, null, null, null, null, null);
		
		if(cursor != null) {
		    cursor.moveToFirst();
		}
		return cursor;
	}
	
	
	/**
	 * Metodo publico que elimina una persona especifica.
	 * @param rowId
	 * @return
	 */
	public boolean eliminaPersona(long id){
		return this.getWritableDatabase().delete("Historial", "_id" + "=" + id, null) > 0;
	}	
}
