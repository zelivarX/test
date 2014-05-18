package helper.classes;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.x.velizar.LogedIn;
import com.x.velizar.PostInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class GetAnswerAsyncTask extends AsyncTask <String, Integer, String>{

	private LogedIn logedIn;
	private ProgressDialog dialog;
	private String postTitle;
	
	public GetAnswerAsyncTask(LogedIn logedIn) {
		this.logedIn = logedIn;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(logedIn);
		dialog.setMessage("loading Stack Overflow information");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.show();
	}
	
	@Override
	protected String doInBackground(String... url) {
		try {
			postTitle = url[2];
			URLConnection con = new URL(url[0]+"/"+Integer.parseInt(url[1])+"?body=true"//"http://api.stackoverflow.com/1.1/users/157882/answers"
					).openConnection();
			Scanner in = new Scanner(con.getInputStream());
			String str = "";
			while(in.hasNextLine()) {
				str += in.nextLine();
			}
			in.close();
			return str;
		}catch(final Exception e) {
			Log.i("lll", e.getMessage());
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dialog.dismiss();
		try {
			JSONObject o = new JSONObject(result);
			JSONArray a = o.getJSONArray("answers");
			
			int score = a.getJSONObject(0).getInt("score");
			String seenBy = a.getJSONObject(0).getString("view_count");
			String body = a.getJSONObject(0).getString("body");

			Intent i = new Intent(logedIn, PostInfo.class);
			i.putExtra("body", body);
			i.putExtra("score", score);
			i.putExtra("seenBy", seenBy);
			i.putExtra("title", postTitle);
			logedIn.startActivity(i);
		} catch(Exception e) {}//"http://api.stackoverflow.com/1.1/users/546661/answers"
	}

}
