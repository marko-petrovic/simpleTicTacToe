package com.markopetrovic.simpletictactoe.activities;

import android.os.Bundle;

import com.markopetrovic.simpletictactoe.R;

public class BoardActivity extends BaseActivity 
{
	//this is Activity where TicTacToe game is being played
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        
        setUIstuff();
    } 
	
	@Override
	public void setUIstuff()
	{
		//
	}
}
