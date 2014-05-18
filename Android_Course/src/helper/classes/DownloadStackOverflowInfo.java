package helper.classes;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.x.velizar.LogedIn;

public class DownloadStackOverflowInfo extends AsyncTask <String, Integer, String>{

	private ArrayList<UserInfo> list;
	private LogedIn logedIn;
	private ProgressDialog dialog;
	
	public DownloadStackOverflowInfo(LogedIn logedIn) {
		this.logedIn = logedIn;
		list = logedIn.getList();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//list = new ArrayList<Integer>();
		dialog = new ProgressDialog(logedIn);
		dialog.setMessage("loading Stack Overflow information");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.show();
		
	}
	
	@Override
	protected String doInBackground(String... url) {
		try {
			URLConnection con = new URL(url[0]//"http://api.stackoverflow.com/1.1/users/157882/answers"
					).openConnection();
			Scanner in = new Scanner(con.getInputStream());
			String str = "";
			while(in.hasNextLine()) {
				str += in.nextLine();
			}
			in.close();
			return str;
		}catch(final Exception e) {
			error();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//Log.i("lll", "DONEEE!!");
		dialog.dismiss();
		try {
			JSONObject o = new JSONObject(result);
			JSONArray a = o.getJSONArray("answers");
			list.clear();
			for(int i = 0; i < a.length();i++) {
				JSONObject obj = a.getJSONObject(i);
				list.add(new UserInfo(obj.getString("title"), obj.getInt("score"), obj.getInt("answer_id")));
			}
			
			logedIn.getAdapter().notifyDataSetChanged();
		} catch(Exception e) {
			//Log.i("lll", e.getMessage());
			error();
		}//"http://api.stackoverflow.com/1.1/users/546661/answers"
	}
	
	private void error() {
		AlertDialog dialog = new AlertDialog.Builder(logedIn)
			.setPositiveButton("retrun to main page", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					logedIn.goToMain();
				}
				
			})
			.setCancelable(false)
			.setMessage("Error loading information")
			.create();
		if(logedIn.isActive())
		dialog.show();
				
		
	}
	
}
