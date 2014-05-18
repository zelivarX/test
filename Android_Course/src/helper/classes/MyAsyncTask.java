package helper.classes;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.x.velizar.MyFragment;

public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
	
	public MyFragment frag;
	public static MyAsyncTask currentTask;
	public static final long MAX_VALUE = 5000000;//1l << 60l;
	public static final int INTERVAL = 20000;//1000000;
	
	// wifi 00795487

	public MyAsyncTask(MyFragment frag) {
		this.frag = frag;
		
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (frag != null && frag.callback != null) 
			frag.callback.onProcess(values[0]);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(frag != null && frag.callback != null) 
			frag.callback.onPreExecute();
	}
	
	
	
	@Override
	protected String doInBackground(Integer... arg0) {
		for(long i = 0l; i < MAX_VALUE; i++) {
			if(i != 0 && i % INTERVAL == 0) {
				this.publishProgress(new Integer[] {(int) (i / INTERVAL)});
			}
		}
		return "END";
	}
	
	@Override
	protected void onPostExecute(String result) {
		
		super.onPostExecute(result);
		if(frag != null && frag.callback != null)
			frag.callback.done(result);
		frag.callback = null;
		currentTask = null;
	}
	
}
