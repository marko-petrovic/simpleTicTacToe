package com.markopetrovic.simpletictactoe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;

public class BoardActivity extends BaseActivity 
{
	//this is Activity where TicTacToe game is being played
	
	private TextView descriptionText;
	private Button endMatchButton;
	private Button boardButton1, boardButton2, boardButton3;
	private Button boardButton4, boardButton5, boardButton6;
	private Button boardButton7, boardButton8, boardButton9;
	
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
		//init description textViews and set font we want
    	descriptionText = (TextView)findViewById(R.id.activity_board_textview_board_description);
    	descriptionText.setTypeface(defaultFont);
    	
    	//end match button
    	endMatchButton = (Button)findViewById(R.id.activity_board_button_end_match);
    	endMatchButton.setTypeface(defaultFont);
    	endMatchButton.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				//just for testing
				TicTacToeManager.boardOponents.getxPlayer().setCurrentWins(5);
				TicTacToeManager.boardOponents.getxPlayer().setCurrentLoses(2);
				TicTacToeManager.boardOponents.getoPlayer().setCurrentWins(2);
				TicTacToeManager.boardOponents.getoPlayer().setCurrentLoses(5);
				
				TicTacToeManager.recordResultsFromThisMatch((Activity) context);
				
				finish();
			}
		});
    	
    	//board buttons
    	boardButton1 = (Button)findViewById(R.id.activity_board_button_1);
    	boardButton2 = (Button)findViewById(R.id.activity_board_button_2);
    	boardButton3 = (Button)findViewById(R.id.activity_board_button_3);
    	boardButton4 = (Button)findViewById(R.id.activity_board_button_4);
    	boardButton5 = (Button)findViewById(R.id.activity_board_button_5);
    	boardButton6 = (Button)findViewById(R.id.activity_board_button_6);
    	boardButton7 = (Button)findViewById(R.id.activity_board_button_7);
    	boardButton8 = (Button)findViewById(R.id.activity_board_button_8);
    	boardButton9 = (Button)findViewById(R.id.activity_board_button_9);
    	
    	LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(TicTacToeManager.deviceScreenWidth/4, TicTacToeManager.deviceScreenWidth/4);
    	
    	boardButton1.setLayoutParams(buttonParams);
    	boardButton2.setLayoutParams(buttonParams);
    	boardButton3.setLayoutParams(buttonParams);
    	boardButton4.setLayoutParams(buttonParams);
    	boardButton5.setLayoutParams(buttonParams);
    	boardButton6.setLayoutParams(buttonParams);
    	boardButton7.setLayoutParams(buttonParams);
    	boardButton8.setLayoutParams(buttonParams);
    	boardButton9.setLayoutParams(buttonParams);
	}
	
	@Override
	public void onBackPressed() 
	{
		//onBackPressed is disabled as it's not a big issue in demo app to handle it too
		//lets force users to end match by pressing end match button
	}
}
