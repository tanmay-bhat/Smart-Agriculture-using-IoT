package com.vehicle.owner;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class smsReceiver extends BroadcastReceiver 
{

	static String incomingno;
	Context cntxt;
	SharedPreferences sp;
	SmsManager smanager;
	static MediaPlayer mp;
	NotificationManager notificationManager;

	@Override
	public void onReceive(Context context, Intent intent)
 {
		

		cntxt = context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		smanager = SmsManager.getDefault();
		notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);

		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		mp = new MediaPlayer();

		if (bundle != null) 
{
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				incomingno = msgs[i].getOriginatingAddress();
				String body = msgs[i].getMessageBody().toString();

TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
String m_deviceId = TelephonyMgr.getDeviceId();

Toast.makeText(context, "" + body, 60000).show();
				
		
Notification updateComplete = new Notification();
updateComplete.icon = android.R.drawable.stat_notify_sync;
updateComplete.tickerText = "Click here to go to alert";
updateComplete.when = System.currentTimeMillis();
Intent notificationIntent = new Intent(context,VehicleTheftOwner.class);
PendingIntent contentIntent = PendingIntent.getActivity(
			context, 0, notificationIntent, 0);
updateComplete.setLatestEventInfo(context,
"The Notification", "please click here",contentIntent);
notificationManager.notify(100, updateComplete);

mp = new MediaPlayer();
					try {
						Toast.makeText(context, "found", 					60000).show();
						mp.setDataSource(getFilePath("iot"));
						mp.prepare();
						mp.start();

					} catch (IllegalArgumentException e) {
						
						e.printStackTrace();
							
					} catch (IllegalStateException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
				
						e.printStackTrace();
					}

				}

													 									if(body.contains("WATER")) {
					mp = new MediaPlayer();
					try {

						mp.setDataSource(getFilePath("water"));
						mp.prepare();
						mp.start();

					} catch (IllegalArgumentException e) {
							e.printStackTrace();

					} catch (IllegalStateException e) {
							e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					}

				}


	if (body.contains("INTRUDER")) {
					mp = new MediaPlayer();
					try {

						mp.setDataSource(getFilePath("intruder"));
						mp.prepare();
						mp.start();

					} catch (IllegalArgumentException e) {
						
						e.printStackTrace();

					} catch (IllegalStateException e) {
					
						e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

								
				if (body.contains("TEMP")) {
					mp = new MediaPlayer();
					try {
							
						mp.setDataSource(getFilePath("temp"));
						mp.prepare();
						mp.start();
						}	
	 catch (IllegalArgumentException e) {
												e.printStackTrace();

					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
												e.printStackTrace();
					}
				}
				
							
								
				if (body.contains("PH")) {
					mp = new MediaPlayer();
					try {

						mp.setDataSource(getFilePath("ph"));
						mp.prepare();
						mp.start();
							
					} catch (IllegalArgumentException e) {
												e.printStackTrace();

					} catch (IllegalStateException e) {
												e.printStackTrace();
					} catch (IOException e) {
												e.printStackTrace();
					}
				}
				

	if (body.contains("HUMIDITY")) {
					mp = new MediaPlayer();
					try {						mp.setDataSource(getFilePath("humidity"));
						mp.prepare();
						mp.start();
							
					} catch (IllegalArgumentException e) {
												e.printStackTrace();

					} catch (IllegalStateException e) {
												e.printStackTrace();
					} catch (IOException e) {
												e.printStackTrace();
					}
				}
				
				if (body.contains("LAND IS DRY")) {
					mp = new MediaPlayer();
					try {
	mp.setDataSource(getFilePath("land"));
						mp.prepare();
						mp.start();
							
					} catch (IllegalArgumentException e) {
												e.printStackTrace();

					} catch (IllegalStateException e) {
												e.printStackTrace();
					} catch (IOException e) {
												e.printStackTrace();
					}
				}

			}
		}

		mp.setOnCompletionListener(new OnCompletionListener() 			{

			@Override
			public void onCompletion(MediaPlayer mp1) {
				// TODO Auto-generated method stub

				mp.stop();
				mp.release();
				/*
				 * mp = new MediaPlayer(); try {
				 * mp.setDataSource("/sdcard/voice.amr"); mp.prepare();
				 * mp.setOnCompletionListener(this); mp.start(); } catch
				 * (IllegalArgumentException e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); } catch (IllegalStateException e)
				 * { // TODO Auto-generated catch block e.printStackTrace(); }
				 * catch (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
			}
		});
	}

	public void LocationSaver(String[] temp) {

		try {
			FileOutputStream mFileOutputStream = new FileOutputStream(
					"/sdcard/location.txt", true);
			mFileOutputStream.write((temp[1] + temp[2]).getBytes());
			mFileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void alertDialog(String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder d1 = new AlertDialog.Builder(cntxt);
		d1.setMessage(string);
		d1.setTitle("Warning");
		d1.setPositiveButton("OK", null);
		d1.setCancelable(true);
		d1.create().show();

	}

	public String getFilePath(String filename) {
		String filepath = "/sdcard/iot/";
		File file = new File(filepath);
		if (file.exists()) {
			File[] list = file.listFiles();
			for (File f : list) {
				if (f.getName().toLowerCase().contains(filename)) {
					return f.getAbsolutePath();
				}
			}
		}
		return "";
	}

}

guardian app code.java
Displaying guardian app code.java.
