package com.x.velizar;

import helper.classes.DownloadStackOverflowInfo;
import helper.classes.GetAnswerAsyncTask;
import helper.classes.UserInfo;
import java.util.ArrayList;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StackOverflowInfoFragment extends Fragment {

    public StackOverflowInfoFragment() {
        // Empty constructor required for fragment subclasses
    }

    public class MyAdapter extends ArrayAdapter<UserInfo> {
		private LogedIn stackOverflowInfo;
		
		MyAdapter (StackOverflowInfoFragment stackOverflowInfo) {
			super(logedIn, R.layout.itemx, stackOverflowInfo.list);
		}
		
		@Override
		public View getView(final int position, View v, ViewGroup parent) {
			if(v == null) {
				LayoutInflater inflater = (LayoutInflater) logedIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.itemx, parent, false);
			}
		
			TextView tit = (TextView) v.findViewById(R.id.title);
			tit.setText(list.get(position).getTitle());
			tit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					int aid = list.get(position).getId();
					String title = list.get(position).getTitle();
					
					new GetAnswerAsyncTask(stackOverflowInfo).execute("http://api.stackoverflow.com/1.1/answers/","" + aid, title);
				}
				
			});
			((TextView) v.findViewById(R.id.score)).setText("score: " + list.get(position).getSocre());

			
			return v;
		}
		
	}
	
	private ArrayList<UserInfo> list = new ArrayList<UserInfo>();
	
	private MyAdapter adapter;
	
	public MyAdapter getAdapter() {
		return adapter;
	}
	
	public ArrayList<UserInfo> getList() {
		return this.list;
	}
	
	private LogedIn logedIn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		logedIn = (LogedIn) getActivity();
		
		adapter = new MyAdapter(this);
		new DownloadStackOverflowInfo(logedIn).execute("http://api.stackoverflow.com/1.1/users/157882/answers");		
		
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_dsa, container, false);
        AdapterView lv = (ListView) rootView.findViewById(R.id.list2);
		lv.setAdapter(adapter);
        return rootView;
    }
}