package com.markopetrovic.simpletictactoe.activities;

import android.os.Bundle;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;

public class BoardActivity extends BaseActivity 
{
	//this is Activity where TicTacToe game is being played
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        
        setUIstuff();
         
        System.out.println
        (
        	"BOARD OPONENTS ARE " + 
        	TicTacToeManager.boardOponents.getxPlayer().getPlayer().getName() + 
        	" and " + 
        	TicTacToeManager.boardOponents.getoPlayer().getPlayer().getName()
        );
    } 
	
	@Override
	public void setUIstuff()
	{
		//TODO
	}
}
