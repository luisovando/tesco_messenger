package com.example.pruebagcm;

//import net.proyectosbeta.pruebabasedatos.EditarPersonaActivity;
//import net.proyectosbeta.pruebabasedatos.MainActivity;
//import net.proyectosbeta.pruebabasedatos.R;
//import net.proyectosbeta.pruebabasedatos.basedatos.DatabaseHandler;
//import net.proyectosbeta.pruebabasedatos.modelos.Persona;
//import net.proyectosbeta.pruebabasedatos.utilitarios.ImagenAdapter;
import com.example.pruebagcm.database.DatabaseHandler;
import com.example.pruebagcm.modelos.Datos;
import com.example.pruebagcm.utilitarios.ImagenAdapter;
import com.example.pruebagcm.utilitarios.MensajeToast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HistorialNotificaciones extends Activity {
	// Objetos.
	private DatabaseHandler baseDatos;
	private ImagenAdapter cursorAdapter;
	private ListView listViewPersonas;
	//private Button botonAgregarPersona;
	private MensajeToast mensaje;
	String message;
	String title;
	private String ruta_imagen;
	//="/mnt/sdcard/Download/3819319411425735373.jpg";
	
	// Constantes privadas.
	private int CODIGO_RESULT_EDITAR_PERSONA = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.historial_notificaciones);
		
		//se obtienen los datos del intent
		 Bundle extras=getIntent().getExtras();
	      
	        message =extras.getString("aviso"); 
		    title =extras.getString("titulo");
		// Hace referencia a la parte xml (activity_main.xml).
		listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);
		//botonAgregarPersona = (Button)findViewById(R.id.botonAgregarPersona);
		
		insertarNuevoNotificacion(message, title,ruta_imagen);

		/**
		 * Al hacer click en el boton agregar Persona se abre una ventana para la edicion de una
		 * nueva persona..
		 */
		/*botonAgregarPersona.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Se crea una nueva persona.
				editarPersona(0);
			}
		});    */
		
		// Se recuperan todas las personas de la base de datos.
		recuperarTodasPersonas();
		
		// Asociamos los menús contextuales al listViewPersonas.
	    registerForContextMenu(listViewPersonas);
	}
	
	/**
	 * Metodo publico que se sobreescribe. En este metodo crearmos el menu contextual
	 * para el ListView de personas. 
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
	    super.onCreateContextMenu(menu, v, menuInfo);
	    android.view.MenuInflater inflater = getMenuInflater();
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    inflater.inflate(R.menu.opciones_notificaciones, menu);
	}
		
	/**
	 * Metodo publico que se sobreescribe. En este metodo colocamos las acciones de las opciones del menu contextual
	 * para el ListView de personas. 
	 */
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		
	    switch (item.getItemId()) {
	        /*case R.id.menu_contextual_editar_persona:
	        	editarPersona((int)info.id);
	            return true; */
	        case R.id.menu_contextual_eliminar_persona:
	        	eliminarNotificacion((int)info.id);
	        	recuperarTodasPersonas();
	            return true;
	        default:
	            return super.onContextItemSelected((android.view.MenuItem) item);
	    }
	}

	@Override
	protected void onStart(){
		super.onStart();
	}

	@Override
	protected void onResume(){
		super.onResume();;
	}
	
	/**
	 * se inserta un nuevo mensaje en la base de datos
	 */
	private void insertarNuevoNotificacion(String msj, String tit, String img) {
		baseDatos = new DatabaseHandler(HistorialNotificaciones.this);

		try {
			// Crear objeto Datos.
			Datos dato = new Datos(tit,msj, img);

			// Se inserta una nueva notificacion.
			baseDatos.insertarPersona(dato);
		} catch (Exception e) {
			mensaje.mostrarMensajeCorto("Error al insertar!!!");
			e.printStackTrace();
		} finally {
			// Se cierra la base de datos.
			baseDatos.cerrar();
		}
	}
	
	
	/**
	 * Metodo privado que recupera todos las personas existentes de la base de datos.
	 */
	private void recuperarTodasPersonas() {
		try{
			baseDatos = new DatabaseHandler(this);
			
			// Devuelve todas las personas en el objeto Cursor.
			Cursor cursor = baseDatos.obtenerTodasNotificaciones();
		
		    String[] from = new String[]{
		    	"titulo", 
		    	"mensaje", 
		    	"ruta_imagen"
		    };
		    int [] img = new int []{
		    		
		    };
			    
		    int[] to = new int[]{
		    	R.id.TextViewNombre,
		    	R.id.TextViewApellido,
		    	//R.id.TextViewEdad,
		    	R.id.thumb_persona,
		    };
	    	cursorAdapter = new ImagenAdapter(this, cursor, from, to);
	    	listViewPersonas.setAdapter(cursorAdapter);
	    }catch(Exception e){
	    	Log.d("Error", "El mensaje de error es: " + e.getMessage());
	    }finally{
	    	// Se cierra la base de datos.
	    	baseDatos.cerrar();
	    }
	}
	
	/**
	 * Metodo publico que edita una persona.
	 * @param p_id
	 */
	public void editarPersona(int p_id){
		// Si el p_id es 0, entonces se crea una nueva persona.
		if(p_id == 0){
			// Se dirige a la actividad EditarPersonaActivity.
	        Intent actividad_editarPersona = new Intent(HistorialNotificaciones.this, EditarPersonaActivity.class);
	        startActivityForResult(actividad_editarPersona, CODIGO_RESULT_EDITAR_PERSONA); 
		}else{
			// Recupera una persona especifica.
			Datos persona;
			
			try{    		
				persona = baseDatos.getNotificacion(p_id);
		   	    
		   	    // Se dirige a la actividad EditarPersonaActivity.
		        Intent actividad_editarPersona = new Intent(this, EditarPersonaActivity.class);
		            
		        // Se le coloca parametros para enviar a la actividad EditarPersonaActivity.
		        actividad_editarPersona.putExtra("id", p_id);
		        actividad_editarPersona.putExtra("titulo", persona.getTitulo());
		        actividad_editarPersona.putExtra("mensaje", persona.getMensaje());
		        //actividad_editarPersona.putExtra("edad", persona.getEdad());
		        actividad_editarPersona.putExtra("ruta_imagen", persona.getRutaImagen());
		            
		        startActivityForResult(actividad_editarPersona, CODIGO_RESULT_EDITAR_PERSONA); 
			}catch (Exception e){
			     Toast.makeText(getApplicationContext(), "Error al editar persona!!!", Toast.LENGTH_SHORT).show();
			     e.printStackTrace();
			}finally{
			     baseDatos.cerrar();
			}
		}
	}
	
	/**
	 * Metodo privado que elimina una persona.
	 * @param id_persona
	 */
	private void eliminarNotificacion(int id_notificacion){
		// Objetos. 
		AlertDialog.Builder mensaje_dialogo = new AlertDialog.Builder(this);  	
		
		// Variables.
		final int v_id_notificacion = id_notificacion;
		    
		mensaje_dialogo.setTitle("Importante");  
		mensaje_dialogo.setMessage("Esta seguro que quiere eliminar esto?");            
		mensaje_dialogo.setCancelable(false);  
		mensaje_dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	            try{    		
	    	        baseDatos.eliminaPersona(v_id_notificacion);
	    		    
	    		    recuperarTodasPersonas();
	    		}catch(Exception e){
	    		     Toast.makeText(getApplicationContext(), "Error al eliminar!!!", Toast.LENGTH_SHORT).show();
	    			 e.printStackTrace();
	    		}finally{
	    		     baseDatos.cerrar();
	    	    }
	        }  
	    });  
		mensaje_dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	        	recuperarTodasPersonas();
	        }  
	    });            
		mensaje_dialogo.show();  
	}
	
	/**
	 * El metodo protegido se sobreescribe. Se llama con el resultado de otra actividad
	 * requestCode es el codigo original que se manda a la actividad
	 * resultCode es el codigo de retorno, 0 significa que todo salió bien
	 * intent es usado para obtener alguna información del caller.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    super.onActivityResult(requestCode, resultCode, intent);
	    recuperarTodasPersonas();
	}
} // Fin de la actividad HistorialNotificaciones.