package com.markopetrovic.simpletictactoe.activities;

import android.os.Bundle;

import com.markopetrovic.simpletictactoe.R;

public class ScoreBoardTableActivity extends BaseActivity 
{
	//this is Activity for showing scoreboard to users
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //TODO
        setContentView(R.layout.activity_board);
        
        setUIstuff();
         
        System.out.println
        ("This is SCOREBOARD TABLE ACTIVITY");
    } 
	
	@Override
	public void setUIstuff()
	{
		//TODO
	}
	
	@Override
	public void onBackPressed() 
	{
		finish();
	}
}
