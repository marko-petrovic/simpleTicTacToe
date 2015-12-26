package com.markopetrovic.simpletictactoe.managers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class TicTacToeManager extends Application 
{
	private static Context currentContext;
	public static SharedPreferences appPreferences;
	public static int deviceScreenWidth;
    public static int deviceScreenHeight;
	
	private static TicTacToeManager sInstance;
	
    public static TicTacToeManager getInstance() 
    {
        return sInstance;
    }

	public void onCreate()
	{
		super.onCreate();
		
		//get Singleton instance of Application and initialize other stuff
		sInstance = this;
        sInstance.initializeInstance();

		System.out.println("APPLICATION CREATED");
	}
	
	private void initializeInstance() 
	{
		//get screen dimens values here, we can use them for XO fields dimensions later
        screenConfiguration();
		
		// set application shared prefs
        loadAppPreferences();
    }
	
	@SuppressWarnings("deprecation")
	public void screenConfiguration() 
	{
        Configuration config = getResources().getConfiguration();

        Point size = new Point();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        try 
        {
            display.getSize(size);
            deviceScreenWidth = size.x;
            deviceScreenHeight = size.y;
        } 
        catch (NoSuchMethodError e) 
        {
            deviceScreenWidth = display.getWidth();
            deviceScreenHeight = display.getHeight();
        }
        
        System.out.println("SCREEN DIMENSIONS ARE ");
        System.out.println("deviceScreenWidth is " + deviceScreenWidth);
        System.out.println("deviceScreenHeight is " + deviceScreenHeight);
    }

	//preferences 
	public static void loadAppPreferences()
	{
		appPreferences = sInstance.getApplicationContext().getSharedPreferences("appPrefs", MODE_PRIVATE);
	}
	
	//setting app preference with any String as ID and preference is String
	public static void setAppPreference(String someStringValue, String prefValue)
	{
		SharedPreferences.Editor appPrefsEditor = appPreferences.edit();
		appPrefsEditor.putString(someStringValue, prefValue);
		appPrefsEditor.commit();
	}

	//setting current Activity
	//careful with setting this value cos with Activity recreations you might create
	//possible memory leaks if reference is left and Activity or Fragment is recreated
	public static Context getCurrentContext() 
	{
		return currentContext;
	}

	//setting current Context if we need it
	public static void setCurrentContext(Context currentContext) 
	{
		TicTacToeManager.currentContext = currentContext;
	}
	
	//getting Strings throughout the app
	public static String getStringValue(int resId)
	{
		return sInstance.getApplicationContext().getResources().getString(resId);
	}
}