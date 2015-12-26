package com.markopetrovic.simpletictactoe.managers;

import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.markopetrovic.simpletictactoe.R;

public class TicTacToeManager extends Application 
{
	public enum TransitionType   
	{
		SLIDE_UP, SLIDE_DOWN, SLIDE_LEFT, SLIDE_RIGHT
	};

	public enum PreferenceNames
	{
		SCOREBOARD
	};
	
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
	
	//taking care of players is here
	public static void takeCareOfTheseTwoOponents(String xPlayer, String oPlayer)
	{
		
	}
	
	//this is maybe not too necessary in our simple demo app case, but if we want to pass arguments for intent.putExtra
	//we can prepare them into HashMap<String, String> and pass it as parameter in this method
	public static void startActivity(Activity context, Class activity, HashMap<String, String> arguments, TransitionType transition)
	{
		//define intent, add flags, arguments and set transition
		final Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (arguments != null)
		{
			for (Entry<String, String> entry : arguments.entrySet())
			{
				intent.putExtra(entry.getKey(), entry.getValue());
			}
		}

		if (transition == null)
		{
			context.overridePendingTransition(R.animator.animation_slide_left, R.animator.animation_slide_left_out);
		}
		else
			switch (transition)
			{
				case SLIDE_UP:
					context.overridePendingTransition(R.animator.animation_slide_up, R.animator.animation_slide_up_out);
				break;
				case SLIDE_DOWN:
					context.overridePendingTransition(R.animator.animation_slide_down_out, R.animator.animation_slide_down);
				break;
				case SLIDE_LEFT:
					context.overridePendingTransition(R.animator.animation_slide_left, R.animator.animation_slide_left_out);
				break;
				case SLIDE_RIGHT:
					context.overridePendingTransition(R.animator.animation_slide_right, R.animator.animation_slide_right_out);
				break;
			}

		//starting another activity from the one where we called this method
		context.startActivity(intent);
	}
	
	//simplified starting of Activity without unnecessary transitions and extras
	public static void startActivity(Activity context, Class activity)
	{
		TicTacToeManager.startActivity(context, activity, null, null);
	}
}