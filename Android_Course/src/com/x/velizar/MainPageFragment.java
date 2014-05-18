package com.x.velizar;

import helper.classes.FileManager;
import java.util.ArrayList;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainPageFragment extends Fragment {

	private class MyAdapter extends ArrayAdapter<String> {
		
		private ArrayList<String> items;
		MainPageFragment frag;
		
		MyAdapter(MainPageFragment frag) {
			super(logedIn, R.layout.list_item, frag.items);
			this.items = frag.items;
			this.frag = frag;
		}
		
		@Override
		public View getView(final int position, View v, ViewGroup parent) {
			if(v == null) {
				LayoutInflater inflater = (LayoutInflater) logedIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.list_item, parent, false);
			}
			
			TextView tv = (TextView) v.findViewById(R.id.item_text);
			tv.setText(items.get(position));
			ImageView iv = (ImageView) v.findViewById(R.id.item_img);
			iv.setImageDrawable(logedIn.getResources().getDrawable(imageId.get(position)));
			
			final Button b = (Button) v.findViewById(R.id.item_but);
			b.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(v.getId() == b.getId() && items.size() > 0) {
						frag.fileManager.deleteUser(position);
						items.remove(position);
						imageId.remove(position);
						notifyDataSetChanged();
					}
				}
				
			});
			
			return v;
		}
		
	}
	
	public int getFirstListItem() {
		return this.adapterView.getFirstVisiblePosition();
	}
	
	public void setListPosition(int position) {
		this.adapterView.setSelection(position);
	}
	
	public void addItem() {
		fileManager.addUser();
    	adapter.notifyDataSetChanged();
	}

	public void showHide(MenuItem item) {
		if(cover.getVisibility() == View.VISIBLE) {
			item.setTitle(R.string.show);
    		cover.setVisibility(View.GONE);
    	} else {
    		item.setTitle(R.string.hide);
    		cover.setVisibility(View.VISIBLE);
    	}
	}
	
	private FileManager fileManager;
	protected ArrayList<Integer> imageId;
	protected ArrayList<String> items;
	private MyAdapter adapter;
	private SharedPreferences pref;
	private ImageView cover;
	private MyFragment headlessFrag;
	
	public ArrayList<String> getItems() {
		return this.items;
	}
	
	public ArrayList<Integer> getImagesId() {
		return this.imageId;
	}
	 
	 public void save() {
	    pref.edit()
			.putInt("first_pos", getFirstListItem())
			.putBoolean("isHidden", isCoverHidden())
			.commit();
	 }
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.logedIn = (LogedIn) getActivity();
        MSG("in frag onCreate");
    }
	
	private LogedIn logedIn;
	private View root;
	
	public MainPageFragment() {
		
	}
	
	private AdapterView adapterView;

	public static void MSG(String str) {
		Log.i("tag1", str == null ? "null!" : str);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        MSG("in frag onCreateView");
        root = rootView;
        
        imageId = new ArrayList<Integer>();
        items = new ArrayList<String>();
        
        this.fileManager = new FileManager(logedIn);
        pref = PreferenceManager.getDefaultSharedPreferences(logedIn);
        
        TextView wellcome = (TextView) rootView.findViewById(R.id.wellcome);
        wellcome.setText("Wellcome " + pref.getString("user", ""));
        
        cover = (ImageView) rootView.findViewById(R.id.img);
        cover.setVisibility(pref.getBoolean("isHidden", false) ? View.GONE : View.VISIBLE);
        
        FragmentManager fm = getFragmentManager();
        headlessFrag = (MyFragment) fm.findFragmentById(R.id.frag_cont);
        
        if(headlessFrag == null) {
        	headlessFrag = new MyFragment();
        	fm.beginTransaction().add(R.id.container, headlessFrag).commit();
        }
        
        ((Button) root.findViewById(R.id.button))
    	.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!headlessFrag.startTask()) 
					logedIn.displayDialog();
			}
			
		});
        fileManager.load();
        
        View list = rootView.findViewById(R.id.list);
        if(list instanceof ListView) adapterView = (ListView) list;
        else adapterView = (GridView) list;
        
        adapterView.setAdapter(adapter = new MyAdapter(this));
        
        setListPosition(pref.getInt("first_pos", 0));
        
        return rootView;
    }
    
    public boolean isCoverHidden() {
    	return cover.getVisibility() == View.GONE ? true : false;
    }
}