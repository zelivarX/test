package helper.classes;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.x.velizar.LogedIn;
import com.x.velizar.R;

public class FileManager {
	/*
	private SecondPage secondPage;
	
	public FileManager(SecondPage secondPage) {
		this.secondPage = secondPage;
	}
	
	
	
	public void addUser() {
		try {
			PrintWriter out = new PrintWriter(secondPage.openFileOutput("users", Context.MODE_APPEND));
			String user = null;
			String img = null;
			switch(new Random().nextInt(3)) {
			case 0:
				user = "user_0";
				img = "img.png";
				secondPage.getImagesId().add(R.drawable.img);
				break;
			case 1:
				user = "user_1";
				img = "img1.png";
				secondPage.getImagesId().add(R.drawable.img1);
				break;
			default:
				user = "user_2";
				img = "img2.png";
				secondPage.getImagesId().add(R.drawable.img2);
				break;
			}
			secondPage.getItems().add(user);
			
			out.println(user + " :: " + img);
			out.close();
			//Toast.makeText(ma, "DONE1", Toast.LENGTH_SHORT).show();
		} catch(Exception e) {
			Toast.makeText(secondPage, "error1", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void load() {
		//logedIn.items = new ArrayList<String>();
		//logedIn.getImagesId() = new ArrayList<Integer>();
		
		try {//Toast.makeText(ma, "0", Toast.LENGTH_SHORT).show();
			Scanner in = new Scanner(secondPage.openFileInput("users"));
			//Toast.makeText(ma, "11", Toast.LENGTH_SHORT).show();
			while(in.hasNextLine()) {
				String str = in.nextLine();
				secondPage.getItems().add(str.substring(0, str.indexOf(":")));
				String img = str.substring(str.indexOf(":")+2).trim();
				//ImageView iv = (ImageView) ma.findViewById(R.id.img);
				if(img.equals("img.png")) secondPage.getImagesId().add(R.drawable.img);
				else if(img.equals("img1.png")) secondPage.getImagesId().add(R.drawable.img1);
				else if(img.equals("img2.png")) secondPage.getImagesId().add(R.drawable.img2);
			}
			in.close();
			//Toast.makeText(ma, "DONE2", Toast.LENGTH_SHORT).show();
		} catch(Exception e) {
			Toast.makeText(secondPage, "error2", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void deleteUser(int position) {
		try {
			PrintWriter out = new PrintWriter(secondPage.openFileOutput("users_tmp", Context.MODE_PRIVATE));
			Scanner in = new Scanner(secondPage.openFileInput("users"));
			int c = 0;
			while(in.hasNextLine()) {
				String str = in.nextLine();
				if(c ++ != position) {
					out.println(str);
				}
			}
			out.close();
			in.close();
			
			File f1 = new File(secondPage.getFilesDir(), "users_tmp");
			File f2 = new File(secondPage.getFilesDir(), "users");
			f1.renameTo(f2);
			
			//Toast.makeText(ma, "DONE1", Toast.LENGTH_SHORT).show();
		} catch(Exception e) {
			Toast.makeText(secondPage, "error1", Toast.LENGTH_SHORT).show();
		}
	} */
	
	private LogedIn logedIn;
	
	public FileManager(LogedIn logedIn) {
		this.logedIn = logedIn;
	}
	public void addUser() {
		try {
			PrintWriter out = new PrintWriter(logedIn.openFileOutput("users", Context.MODE_APPEND));
			String user = null;
			String img = null;
			switch(new Random().nextInt(3)) {
			case 0:
				user = "user_0";
				img = "img.png";
				logedIn.mainPage.getImagesId().add(R.drawable.img);
				break;
			case 1:
				user = "user_1";
				img = "img1.png";
				logedIn.mainPage.getImagesId().add(R.drawable.img1);
				break;
			default:
				user = "user_2";
				img = "img2.png";
				logedIn.mainPage.getImagesId().add(R.drawable.img2);
				break;
			}
			logedIn.mainPage.getItems().add(user);
			
			out.println(user + " :: " + img);
			out.close();
			//Toast.makeText(ma, "DONE1", Toast.LENGTH_SHORT).show();
		} catch(Exception e) {
			Toast.makeText(logedIn, "error1", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void load() {
		//logedIn.items = new ArrayList<String>();
		//logedIn.getImagesId() = new ArrayList<Integer>();
		
		try {//Toast.makeText(ma, "0", Toast.LENGTH_SHORT).show();
			Scanner in = new Scanner(logedIn.openFileInput("users"));
			//Toast.makeText(ma, "11", Toast.LENGTH_SHORT).show();
			while(in.hasNextLine()) {
				String str = in.nextLine();
				logedIn.mainPage.getItems().add(str.substring(0, str.indexOf(":")));
				String img = str.substring(str.indexOf(":")+2).trim();
				//ImageView iv = (ImageView) ma.findViewById(R.id.img);
				if(img.equals("img.png")) logedIn.mainPage.getImagesId().add(R.drawable.img);
				else if(img.equals("img1.png")) logedIn.mainPage.getImagesId().add(R.drawable.img1);
				else if(img.equals("img2.png")) logedIn.mainPage.getImagesId().add(R.drawable.img2);
			}
			in.close();
			//Toast.makeText(ma, "DONE2", Toast.LENGTH_SHORT).show();
		} catch(Exception e) {
			//Log.i("lll", e.getMessage());
			Toast.makeText(logedIn, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void deleteUser(int position) {
		try {
			PrintWriter out = new PrintWriter(logedIn.openFileOutput("users_tmp", Context.MODE_PRIVATE));
			Scanner in = new Scanner(logedIn.openFileInput("users"));
			int c = 0;
			while(in.hasNextLine()) {
				String str = in.nextLine();
				if(c ++ != position) {
					out.println(str);
				}
			}
			out.close();
			in.close();
			
			File f1 = new File(logedIn.getFilesDir(), "users_tmp");
			File f2 = new File(logedIn.getFilesDir(), "users");
			f1.renameTo(f2);
			
			//Toast.makeText(ma, "DONE1", Toast.LENGTH_SHORT).show();
		} catch(Exception e) {
			Toast.makeText(logedIn, "error1", Toast.LENGTH_SHORT).show();
		}
	}
	
}
