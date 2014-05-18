package com.x.velizar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

public class PostInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_info);
		
		
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) {
			((TextView) findViewById(R.id.title)).setText(extras.getString("title"));
			//((TextView) findViewById(R.id.body)).setText(extras.getString("body"));
			TextView score = (TextView) findViewById(R.id.score1);
			int points = extras.getInt("score");
			score.setText(this.getString(R.string.score) + points);
			//score.setBackgroundColor(points < 0 ? red : green);
		
			((TextView) findViewById(R.id.seenby)).setText(this.getString(R.string.seen) + extras.getString("seenBy"));
			((WebView) findViewById(R.id.body)).loadData(extras.getString("body"), "text/html", "UTF-8");
		}
		
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.post_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



}
