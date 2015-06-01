package com.example.pruebagcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
 
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	
	
	private static final String SENDER_ID ="1068838226217";
	public GCMIntentService() {
	super(SENDER_ID);
	}
	 
	@Override
	protected void onError(Context ctx, String registrationId) {
	Log.d("javahispano",
	"Error recibido");
	 
	}
	 
	@Override
	protected void onMessage(Context ctx, Intent intent) {
	String msg = intent.getExtras().getString("message");
	Log.d("javahispano","Mensaje:" + msg);
	
	
	
	//String message = getString(R.string.hello_world);
    //displayMessage(ctx, message);
    // notifies user
    generateNotification(ctx, msg);
	 
	}
	 
	@Override
	protected void onRegistered(Context ctx, String regId) {
	//Posteriormente enviaremos el id al Tomcat
	Log.d("javahispano", "Registro recibido " + regId);
	}
	 
	@Override
	protected void onUnregistered(Context ctx, String regId) {
	//Posteriormente enviaremos el id al Tomcat
	Log.d("javahispano",
	"Baja:" + regId);
	}
	
	 private static void generateNotification(Context context, String message) {
	        int icon = R.drawable.tesco_logo_not;//cambiar icono
	        long when = System.currentTimeMillis();
	        NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);
	        
	        Notification notification = new Notification(icon, message, when);
	        //String title = context.getString(R.string.app_name);
	         String title = "AppMovilTesco"; 
	       
	         
	         //Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	                  //notification.sound = defaultSound; 
	         
	      // Patrón de vibración: 1 segundo vibra, 0.5 segundos para, 1 segundo vibra
	        // long[] pattern = new long[]{1000,500,1000};
	          
	         // Uso en API 10 o menor
	        // notification.vibrate = pattern;
	        
	         
	        // notification.defaults |= Notification.DEFAULT_VIBRATE;
	        Intent notificationIntent = new Intent(context, HistorialNotificaciones.class);
	        notificationIntent.putExtra("aviso", message);
	        notificationIntent.putExtra("titulo", "AppMovilTesco");
	        // set intent so it does not start a new activity
	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent intent =
	                PendingIntent.getActivity(context, 0, notificationIntent, 0);
	        notification.setLatestEventInfo(context, title, message, intent);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	       // notification.flags |= Notification.FLAG_INSISTENT;
	        notification.defaults |= Notification.DEFAULT_SOUND;
	        notification.defaults |= Notification.DEFAULT_VIBRATE;
	        notification.defaults |= Notification.DEFAULT_LIGHTS;
	        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
	       
	        
	        notificationManager.notify(0, notification);
	    }
	 
	 private static void enviarNotificaciones(Context context, String message) {
		 
	 }
	 
	}
