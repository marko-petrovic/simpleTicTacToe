package com.markopetrovic.simpletictactoe.managers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class TicTacToeManager extends Application 
{
	private static Context context;
	private static Context currentContext;
	public static SharedPreferences appPreferences;

	public void onCreate()
	{
		super.onCreate();

		TicTacToeManager.context = getApplicationContext();

		loadAppPreferences();
	}

	//application context
	public static Context getAppContext()
	{
		return TicTacToeManager.context;
	}
	
	//preferences 
	public static void loadAppPreferences()
	{
		appPreferences = TicTacToeManager.getAppContext().getSharedPreferences("appPrefs", MODE_PRIVATE);
	}
	
	//setting app preference with any String as ID and preference is String
	public static void setAppPreference(String someStringValue, String prefValue)
	{
		SharedPreferences.Editor appPrefsEditor = appPreferences.edit();
		appPrefsEditor.putString(someStringValue, prefValue);
		appPrefsEditor.commit();
	}

	//setting current Activity
	public static Context getCurrentContext() 
	{
		return currentContext;
	}

	public static void setCurrentContext(Context currentContext) 
	{
		TicTacToeManager.currentContext = currentContext;
	}
	
	//getting Strings throughout the app
	public static String getStringValue(int resId)
	{
		return TicTacToeManager.getAppContext().getResources().getString(resId);
	}
}
