package com.markopetrovic.simpletictactoe.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;

public class BaseActivity extends Activity 
{
	public Typeface defaultFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//fonts
//		defaultFont = Typeface.createFromAsset(TicTacToeManager.getAppContext().getAssets(), "fonts/TitilliumWeb-Regular.ttf");
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		//set current Activity
		TicTacToeManager.setCurrentContext(this);
	}
}