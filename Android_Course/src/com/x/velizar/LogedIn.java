package com.x.velizar;

import helper.classes.MyAsyncTask;
import helper.classes.MyCallback;
import helper.classes.UserInfo;

import java.util.ArrayList;

import com.x.velizar.StackOverflowInfoFragment.MyAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LogedIn extends Activity implements MyCallback {
	
	private ProgressDialog dialog;
    private boolean active;
    
    public boolean isActive() {
    	return active;
    }
    
    public boolean isDialogNull() {
    	return dialog == null;
    }
    
    public ProgressDialog getDialog() {
    	return dialog;
    	
    }
    
    public void displayDialog() {
    	if(dialog != null && !dialog.isShowing())
    		onPreExecute();
    }
    
    @Override
	public void onPause() {
    	super.onPause();
    	this.active = false;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	this.active = true;
    }
    
	@Override
	public void onProcess(int prog) {
		
		if(dialog != null) {
			dialog.setProgress(prog);
		}
	}

	@Override
	public void onPreExecute() {
			dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMessage("Just example ProcessDialog...");
			dialog.setProgress(0);
			dialog.setMax((int) (MyAsyncTask.MAX_VALUE / MyAsyncTask.INTERVAL));
			
			dialog.show();
		
	}

	@Override
	public void done(String result) {
		if(dialog != null) dialog.dismiss();
		if(active) {
			AlertDialog dialog = new AlertDialog.Builder(this)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setMessage(result)
				.create();
			dialog.show();
			
		}			
	}
	
	private class DrawerList extends ArrayAdapter<String> {
		LogedIn ma;
		String[] titles;
		int[] logos;
		
		DrawerList(LogedIn ma, String[] titles, int[] logos) {
			super(ma, R.layout.drawer_item, titles);
			this.ma = ma;
			this.titles = titles;
			this.logos = logos;
		}
		
		@Override
		public View getView(int position, View v, ViewGroup parent) {
			if(v == null) {
				LayoutInflater inflater = (LayoutInflater) ma.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.drawer_item, parent, false);
			}
			TextView tv = (TextView) v.findViewById(R.id.text1);
			tv.setText(titles[position]);
			
			ImageView iv = (ImageView) v.findViewById(R.id.drawer_item_logo);
			iv.setImageDrawable(getResources().getDrawable(logos[position]));
			
			return v;
		}
	}
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
    private CharSequence mTitle;
    private String[] mPlanetTitles;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mainPage.save();
    	
    	MainPageFragment.MSG("in onSaveInstanceState");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        MainPageFragment.MSG("in onCreate");
        mainPage = new MainPageFragment();
        stackOverflowInfoFragment = new StackOverflowInfoFragment();
        
        
        mPlanetTitles = getResources().getStringArray(R.array.options);
        int[] logos = {R.drawable.home, R.drawable.stack};
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, mPlanetTitles));
        
        mDrawerList.setAdapter(new DrawerList(this, mPlanetTitles, logos));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        selectItem(pref.getInt("current_window", 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
       // mainPage.menu = menu;
        if (mainPage.isCoverHidden()) menu.findItem(R.id.show_hide).setTitle("show");
        else menu.findItem(R.id.show_hide).setTitle("hide");
        MainPageFragment.MSG("in onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
    	
        int id = item.getItemId();
        
        switch(id) {
        case R.id.add:
        	if(currentFragment.equals(mainPage)) 
        		mainPage.addItem(); 
        	
            return true;
        case R.id.show_hide:
        	if(currentFragment.equals(mainPage)) 
        		mainPage.showHide(item);
        	
        	return true;
        case R.id.logout:
        	logout();
        	return true;
        case android.R.id.home:
        	logout();
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
    public MainPageFragment mainPage;
    StackOverflowInfoFragment stackOverflowInfoFragment;
    
    private SharedPreferences pref;
    private Fragment currentFragment;
    
    public void goToMain() {
    	this.selectItem(0);
    }
    
    private void selectItem(int position) {
        // update the main content by replacing fragments
    	pref.edit().putInt("current_window", position).commit();
    	if(position == 0) currentFragment = mainPage;//new Frag1();
    	else currentFragment = stackOverflowInfoFragment;

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, currentFragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

   
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
        MainPageFragment.MSG("in onConfigurationChanged");
    }
    
    
    private void onLogout() {
		pref.edit()
			.putBoolean("loged", false)
			.remove("current_window")
			.remove("first_pos")
			.remove("isHidden")
			.commit();
		this.startActivity(new Intent(this, MainPage.class));
		this.finish();
	}
	
	private void logout() {

		new AlertDialog.Builder(this)
			.setMessage("Are you sure you want to logout?")
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					onLogout();
				}
				
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
				
			})
			.create()
			.show();
	}

	public ArrayList<UserInfo> getList() {
		return stackOverflowInfoFragment.getList();
	}

	public MyAdapter getAdapter() {
		return stackOverflowInfoFragment.getAdapter();
	}
    
}