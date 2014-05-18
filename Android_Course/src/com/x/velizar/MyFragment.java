package com.x.velizar;

import helper.classes.MyAsyncTask;
import helper.classes.MyCallback;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment {

	public MyCallback callback;
	private boolean taskDone;
	MyAsyncTask asyncTask;
	
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callback = (MyCallback) activity;
	}
	
	public boolean startTask() {
		/*if(MyAsyncTask.currentTask != null && !MyAsyncTask.currentTask.isRunning) {
			if(asyncTask != null) {
				asyncTask.execute();
				return true;
			}
		}*/
		
		if(MyAsyncTask.currentTask == null) {
			asyncTask = new MyAsyncTask(this);
			MyAsyncTask.currentTask = asyncTask;
			asyncTask.execute();
			return true;
			
		}
		return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
		//WeakReference<MyAsyncTask> weakRef;
		taskDone = false;
		Log.i("lqlq", "BOOM");
		
		if(MyAsyncTask.currentTask == null) {
			//asyncTask = new MyAsyncTask(this);
			//MyAsyncTask.currentTask = asyncTask;
			
			
		} else {
			MyAsyncTask.currentTask.frag = this;
			//callback.onPreExecute();
		}
		
	    //weakRef = new WeakReference<MyAsyncTask>(asyncTask);
	    
	}
	
}
