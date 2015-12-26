package com.markopetrovic.simpletictactoe.managers;

import java.util.ArrayList;
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
import com.markopetrovic.simpletictactoe.models.BoardOponents;
import com.markopetrovic.simpletictactoe.models.BoardPlayer;
import com.markopetrovic.simpletictactoe.models.Player;
import com.markopetrovic.simpletictactoe.models.Scoreboard;
import com.markopetrovic.simpletictactoe.utils.ObjectSerializer;

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
    public static Scoreboard scoreboardPlayers;
    public static BoardOponents boardOponents;
	
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
        
//        System.out.println("SCREEN DIMENSIONS ARE ");
//        System.out.println("deviceScreenWidth is " + deviceScreenWidth);
//        System.out.println("deviceScreenHeight is " + deviceScreenHeight);
    }

	//preferences and loading stuff from them
	//we use prefs for storing player scores in this simple app
	//that means that we serialize and deserialize Scoreboard into prefs
	//this is more efficient approach than using heavy Gson for such simple usecase
	//as our Player objects have only String and int values, this is pretty light and safe to serialize and deserialize
	//we won't be using Parcelable interface here cos we aren't communicating between processes so we don't need speed
	//and if Android changes Parcelable API in the meantime, we have no choice but to raise white flag
	//byte[] to String and back is in my opinion fair approach to our data storing usecase
	public static void loadAppPreferences()
	{
		appPreferences = sInstance.getApplicationContext().getSharedPreferences("appPrefs", MODE_PRIVATE);
		
		//now get scoreboard arraylist of Players
		//if app was never used to play tic tac toe, result will be empty array [], exactly what we need
		try 
		{
			scoreboardPlayers = (Scoreboard) ObjectSerializer.deserializeObject
					(appPreferences.getString("SCOREBOARD", ObjectSerializer.serializeObject(Scoreboard.createScoreboard(new ArrayList<Player>()))));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//method to save scoreboardPlayers into shared prefs by overriding over already saved scoreboard in prefs
	public static void updateScoreboard(Scoreboard scoreboardPlayers)
	{
		SharedPreferences.Editor appPrefsEditor = appPreferences.edit();
		
        try 
        {
        	appPrefsEditor.putString("SCOREBOARD", ObjectSerializer.serializeObject(scoreboardPlayers));
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
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
		if (scoreboardPlayers.getScoreBoardPlayers() != null && scoreboardPlayers.getScoreBoardPlayers().size() != 0) 
		{
			Player xPlayerObj = null;
			Player oPlayerObj = null;
			boolean xFound = false;
			boolean oFound = false;
			
			//loop through all players from scoreboard and see if there's one named as any of the two who will play now
			//this is silly without a way to login players, but it's silly approach in this demo simple app
			//not to have doubled entries in our scoreboard
			for (int i = 0; i < scoreboardPlayers.getScoreBoardPlayers().size(); i++) 
			{
				if (scoreboardPlayers.getScoreBoardPlayers().get(i).getName().contentEquals(xPlayer) && !xFound) 
				{
					//player found
					xPlayerObj = Player.createPlayer
					(
						scoreboardPlayers.getScoreBoardPlayers().get(i).getName(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getMatchesWon(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getMatchesLost()
					);
					xFound = true;
				}
				else if (scoreboardPlayers.getScoreBoardPlayers().get(i).getName().contentEquals(oPlayer) && !oFound) 
				{
					//player found
					oPlayerObj = Player.createPlayer
					(
						scoreboardPlayers.getScoreBoardPlayers().get(i).getName(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getMatchesWon(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getMatchesLost()
					);
					oFound = true;
				}
			}
			
			//now lets construct boardOponents
			if (!xFound) 
			{
				xPlayerObj = Player.createPlayer(xPlayer, 0, 0);
			}
			
			if (!oFound) 
			{
				oPlayerObj = Player.createPlayer(oPlayer, 0, 0);
			}
			
			//finally
			boardOponents = BoardOponents.createBoardOponents
				(
					BoardPlayer.createBoardPlayer
					(
						xPlayerObj, 0, 0
					), 
					BoardPlayer.createBoardPlayer
					(
						oPlayerObj, 0, 0
					)
				);
		}
		else
		{
			//game is started for the very first time so there's nobody in scoreboard arraylist
			//therefore, create brand new boardOponents and boardPlayers
			boardOponents = BoardOponents.createBoardOponents
			(
				BoardPlayer.createBoardPlayer
				(
					Player.createPlayer(xPlayer, 0, 0), 0, 0
				), 
				BoardPlayer.createBoardPlayer
				(
					Player.createPlayer(oPlayer, 0, 0), 0, 0
				)
			);
		}
	}
	
	//this is maybe not too necessary in our simple demo app case, but if we want to pass arguments for intent.putExtra
	//we can prepare them into HashMap<String, String> and pass it as parameter in this method
	public static void startActivity(Activity context, @SuppressWarnings("rawtypes") Class activity, HashMap<String, String> arguments, TransitionType transition)
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