package com.example.pruebagcm;

import java.io.File;

import com.example.pruebagcm.database.DatabaseHandler;
import com.example.pruebagcm.modelos.Datos;
import com.example.pruebagcm.utilitarios.MensajeToast;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditarPersonaActivity extends Activity {
	// Objetos.
	private Button butonLimpiar;
	private Button butonGuardar;
	private EditText editTextNombre;
	private EditText editTextApellido;
	private EditText editTextEdad;
	private DatabaseHandler baseDatos;
	private Bundle extras;
	private Button botonImagenPersona;
	private ImageView imagenPersona;
	private MensajeToast mensaje;

	// Variables.
	private String ruta_imagen; // La ruta de la imagen que el usuario eligio
								// para la imagen de su persona.
	private int SELECCIONAR_IMAGEN = 237487;
	
	// Constantes privadas.
	private static final int FECHA_DIALOGO_ID = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_persona);

		// Hace referencia a los objetos xml.
		butonGuardar = (Button) findViewById(R.id.botonGuardar);
		butonLimpiar = (Button) findViewById(R.id.botonLimpiar);
		editTextNombre = (EditText) findViewById(R.id.editTextNombre);
		editTextApellido = (EditText) findViewById(R.id.editTextApellido);
		editTextEdad = (EditText) findViewById(R.id.editTextEdad);
		botonImagenPersona = (Button) findViewById(R.id.botonAgregarImagenPersona);
		imagenPersona = (ImageView) findViewById(R.id.imagenPersona);

		// Se crea el objeto mensaje.
		mensaje = new MensajeToast(getApplicationContext());

		/**
		 * Al hacer click en el boton imagen se abre una ventana.
		 */
		botonImagenPersona.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ventanaImagen();
			}
		});
		// Recupera en un Objeto Bundle si tiene valores que fueron pasados como
		// parametro de una actividad.
		extras = getIntent().getExtras();

		if (estadoEditarPersona()) {
			editTextNombre.setText(extras.getString("titulo"));
			editTextApellido.setText(extras.getString("mensaje"));
			//editTextEdad.setText(extras.getString("edad"));
			
			// Se coloca la imagen de una persona.
			ruta_imagen = extras.getString("ruta_imagen");
			Bitmap bitmap = getBitmap(ruta_imagen);

			// La imagen no puede ser mayor a 2048 de ancho o alto.
			if (bitmap.getHeight() >= 2048 || bitmap.getWidth() >= 2048) {
				bitmap = Bitmap.createScaledBitmap(
						bitmap,
						(bitmap.getHeight() >= 2048) ? 2048 : bitmap
								.getHeight(),
						(bitmap.getWidth() >= 2048) ? 2048 : bitmap
								.getWidth(), true);
				
			}
			imagenPersona.setImageBitmap(bitmap);
		}

		// Agrega nuevo registro de una persona.
/*		butonGuardar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (verificarCampoNombre() && verificarCampoApellido()
						&& verificarCampoEdad()) {
					if (estadoEditarPersona()) {
						editarPersona();
					} else {
						insertarNuevoPersona();
					}
					// Finaliza la actividad EditarPersonaActivity.
					finish();
				} else {
					if (editTextNombre.getText().toString().equals("")) {
						mensaje.mostrarMensajeCorto("Introduzca un Nombre!!!");
					}
					if (editTextApellido.getText().toString().equals("")) {
						mensaje.mostrarMensajeCorto("Introduzca un Apellido!!!");
					}
					//if (editTextEdad.getText().toString().equals("")) {
						//mensaje.mostrarMensajeCorto("Introduzca una Edad!!!");
					//}
				}
			}
		});
		*/

		// Limpia los campos.
		butonLimpiar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				limpiarCampos();
			}
		});
	}

	/**
	 * Metodo privado que limpia los campos.
	 */
	private void limpiarCampos() {
		editTextNombre.setText("");
		editTextApellido.setText("");
		editTextEdad.setText("");
	}

	/**
	 * Metodo privado que verifica que se cambio el valor de Nombre o no está en
	 * blanco (vacio).
	 */
	private boolean verificarCampoNombre() {
		if (editTextNombre.getText().toString().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo privado que verifica que se cambio el valor de Apellido o no
	 * está en blanco (vacio).
	 */
	private boolean verificarCampoApellido() {
		if (editTextApellido.getText().toString().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo privado que verifica que se cambio el valor de la Edad o no está
	 * en blanco (vacio).
	 */
	private boolean verificarCampoEdad() {
		if (editTextEdad.getText().toString().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo privado que insertar una nueva Persona.
	 */
/*	private void insertarNuevoPersona() {
		baseDatos = new DatabaseHandler(EditarPersonaActivity.this);

		try {
			// Crear objeto Persona.
			Datos dato = new Datos(editTextNombre.getText().toString(),
					editTextApellido.getText().toString(),  ruta_imagen);

			// Se inserta una nueva persona.
			baseDatos.insertarPersona(dato);
		} catch (Exception e) {
			mensaje.mostrarMensajeCorto("Error al insertar!!!");
			e.printStackTrace();
		} finally {
			// Se cierra la base de datos.
			baseDatos.cerrar();
		}
	}
*/
	/**
	 * Metodo privado que edita una persona existente.
	 */
/*	private void editarPersona() {
		baseDatos = new DatabaseHandler(EditarPersonaActivity.this);

		try {
			// Crear objeto persona.
			int id = extras.getInt("id");

			Datos persona = new Datos(id, editTextNombre.getText()
					.toString(), editTextApellido.getText().toString(), ruta_imagen);

			baseDatos.actualizarRegistros(id, persona.getTitulo(),
					persona.getMensaje(), 
					persona.getRutaImagen());
			mensaje.mostrarMensajeCorto("Se cambio correctamente el registro!!!");
		} catch (Exception e) {
			mensaje.mostrarMensajeCorto("Error al querer editar un registro!!!");
			e.printStackTrace();
		} finally {
			baseDatos.cerrar();
		}
	}
*/
	/**
	 * 
	 */
	public boolean estadoEditarPersona() {
		// Si extras es diferente a null es porque tiene valores. En este caso
		// es porque se quiere editar una persona.
		if (extras != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo privado que abre la ventana.
	 */
	private void ventanaImagen() {
		try {
			final CharSequence[] items = { "Seleccionar de la galería" };

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Seleccionar una foto");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					switch (item) {
						case 0:
							Intent intentSeleccionarImagen = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
							intentSeleccionarImagen.setType("image/*");
							startActivityForResult(intentSeleccionarImagen, SELECCIONAR_IMAGEN);
							break;
						}
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			mensaje.mostrarMensajeCorto("El error es: " + e.getMessage());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		try {
			if (requestCode == SELECCIONAR_IMAGEN) {
				if (resultCode == Activity.RESULT_OK) {
					Uri selectedImage = data.getData();
					ruta_imagen = obtieneRuta(selectedImage);
					Bitmap bitmap = getBitmap(ruta_imagen);

					// La imagen no puede ser mayor a 2048 de ancho o alto.
					if (bitmap.getHeight() >= 2048 || bitmap.getWidth() >= 2048) {
						bitmap = Bitmap.createScaledBitmap(
								bitmap,
								(bitmap.getHeight() >= 2048) ? 2048 : bitmap
										.getHeight(),
								(bitmap.getWidth() >= 2048) ? 2048 : bitmap
										.getWidth(), true);
						imagenPersona.setImageBitmap(bitmap);
					} else {
						imagenPersona.setImageURI(selectedImage);
					}
				}
			}
		} catch (Exception e) {
		}

	}

	private Bitmap getBitmap(String ruta_imagen) {
		// Objetos.
		File imagenArchivo = new File(ruta_imagen);
		Bitmap bitmap = null;

		if (imagenArchivo.exists()) {
			bitmap = BitmapFactory.decodeFile(imagenArchivo.getAbsolutePath());
		}
		return bitmap;
	}

	/**
	 * Metodo privado
	 * 
	 * @param uri
	 * @return
	 */
	private String obtieneRuta(Uri uri) {
		String[] projection = { android.provider.MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			// Cerrar EditarPersonaActivity.
			EditarPersonaActivity.this.finish();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		*/
	//}
}

