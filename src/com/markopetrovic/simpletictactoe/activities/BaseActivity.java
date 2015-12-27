package com.markopetrovic.simpletictactoe.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;

public class BaseActivity extends Activity 
{
	protected Typeface defaultFont; 
	protected Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		context = this;
		
		//fonts
		defaultFont = Typeface.createFromAsset(TicTacToeManager.getInstance().getAssets(), "fonts/TitilliumWeb-Regular.ttf");
		
		TicTacToeManager.getInstance();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
//		//set current Activity
//		TicTacToeManager.setCurrentContext(this);
	}
	
	public void setUIstuff() 
	{
		//override it in child Activities
	}
}