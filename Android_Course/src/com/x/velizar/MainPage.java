package com.x.velizar;

import java.io.StringReader;
import java.util.Scanner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainPage extends Activity implements View.OnClickListener {

	private EditText accField;
	private EditText passField;
	private SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("loged", false)) {
			this.startActivity(new Intent(this, LogedIn.class));
			this.finish();
		}
		
		((Button) this.findViewById(R.id.submit)).setOnClickListener(this);
		accField = (EditText) this.findViewById(R.id.acc);
		passField = (EditText) this.findViewById(R.id.pass);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		String acc = pref.getString("user", "");
		
		accField.setText(acc);
		if(!acc.trim().equals("")) passField.requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.submit: 
			login();
			return;
		}
		
	}
	
	private void login() {
		if(check(accField.getText().toString(), passField.getText().toString())) {
			pref.edit()
				.putString("user", accField.getText().toString())
				.putBoolean("loged", true)
				.commit();
			
			this.startActivity(new Intent(this, LogedIn.class));
			this.finish();
			
		} else {
			passField.setText("");
			AlertDialog dialog = new AlertDialog.Builder(this)
				.setMessage("Invalid username or password")
				.setTitle("Login error")
				.setPositiveButton(android.R.string.ok, new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
					
				})
				.create();
			dialog.show();
		}
	}
	
	private boolean check(String acc, String pass) {
		// информацията за потребителите се държи в xml файл в Assets
		Scanner in = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(true);
		    XmlPullParser xpp = factory.newPullParser();

		    xpp.setInput(this.getAssets().open("users.xml"), null);

		    int eventType = xpp.getEventType();
		    String acc1 = "";
		    String pass1 = "";
		    
		    while (eventType != XmlPullParser.END_DOCUMENT) {
		    	
			     if(eventType == XmlPullParser.START_TAG) {
			    	 String tag = xpp.getName();
			    	 if(tag.equals("acc")) {
			    		 eventType = xpp.next();
			    		 acc1 = xpp.getText();
			    	 }
			    	 else if (tag.equals("pass")) {
			    		 eventType = xpp.next();
			    		 pass1 = xpp.getText();
			    		 //Toast.makeText(this, acc1 + " " + pass1, Toast.LENGTH_LONG).show();
			    		 if(acc.equals(acc1) && pass.equals(pass1)) return true;
			    	 }
			     }
			     eventType = xpp.next();
		    }
		} catch(Exception e) {
			Toast.makeText(this, "Err", Toast.LENGTH_SHORT).show();
		}
		finally {
			if(in != null) in.close();
		}
		
		return false;
	}
	
	


}
