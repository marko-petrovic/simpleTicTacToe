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

public class BoardActivity extends BaseActivity implements OnClickListener 
{
	//this is Activity where TicTacToe game is being played
	
	private TextView descriptionText;
	private Button endMatchButton;
//	private Button boardButton1, boardButton2, boardButton3;
//	private Button boardButton4, boardButton5, boardButton6;
//	private Button boardButton7, boardButton8, boardButton9;
	private final int[] boardButtonIds = 
	{
		R.id.activity_board_button_1, R.id.activity_board_button_2, R.id.activity_board_button_3,
		R.id.activity_board_button_4, R.id.activity_board_button_5, R.id.activity_board_button_6,
		R.id.activity_board_button_7, R.id.activity_board_button_8, R.id.activity_board_button_9
	};
	
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
    	endMatchButton.setOnClickListener(this);
    	
    	//board buttons
//    	boardButton1 = (Button)findViewById(R.id.activity_board_button_1);
//    	boardButton2 = (Button)findViewById(R.id.activity_board_button_2);
//    	boardButton3 = (Button)findViewById(R.id.activity_board_button_3);
//    	boardButton4 = (Button)findViewById(R.id.activity_board_button_4);
//    	boardButton5 = (Button)findViewById(R.id.activity_board_button_5);
//    	boardButton6 = (Button)findViewById(R.id.activity_board_button_6);
//    	boardButton7 = (Button)findViewById(R.id.activity_board_button_7);
//    	boardButton8 = (Button)findViewById(R.id.activity_board_button_8);
//    	boardButton9 = (Button)findViewById(R.id.activity_board_button_9);
    	Button buttonForBoard;
    	
    	LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(TicTacToeManager.deviceScreenWidth/4, TicTacToeManager.deviceScreenWidth/4);
    	
    	for (int i = 0; i < boardButtonIds.length; i++) 
    	{
			buttonForBoard = (Button)findViewById(boardButtonIds[i]);
			buttonForBoard.setLayoutParams(buttonParams);
			buttonForBoard.setTypeface(defaultFont);
			buttonForBoard.setOnClickListener(this);
		}
    	
//    	boardButton1.setLayoutParams(buttonParams);
//    	boardButton2.setLayoutParams(buttonParams);
//    	boardButton3.setLayoutParams(buttonParams);
//    	boardButton4.setLayoutParams(buttonParams);
//    	boardButton5.setLayoutParams(buttonParams);
//    	boardButton6.setLayoutParams(buttonParams);
//    	boardButton7.setLayoutParams(buttonParams);
//    	boardButton8.setLayoutParams(buttonParams);
//    	boardButton9.setLayoutParams(buttonParams);
//    	
//    	boardButton1.setTypeface(defaultFont);
//    	boardButton2.setTypeface(defaultFont);
//    	boardButton3.setTypeface(defaultFont);
//    	boardButton4.setTypeface(defaultFont);
//    	boardButton5.setTypeface(defaultFont);
//    	boardButton6.setTypeface(defaultFont);
//    	boardButton7.setTypeface(defaultFont);
//    	boardButton8.setTypeface(defaultFont);
//    	boardButton9.setTypeface(defaultFont);
//    	
//    	boardButton1.setOnClickListener(this);
//    	boardButton2.setOnClickListener(this);
//    	boardButton3.setOnClickListener(this);
//    	boardButton4.setOnClickListener(this);
//    	boardButton5.setOnClickListener(this);
//    	boardButton6.setOnClickListener(this);
//    	boardButton7.setOnClickListener(this);
//    	boardButton8.setOnClickListener(this);
//    	boardButton9.setOnClickListener(this);
	}
	
	//after button was clicked on, make it disabled for more clicks
	private void lockButton(Button buttonToLockAfterBeingUsed)
	{
		buttonToLockAfterBeingUsed.setEnabled(false);
	}
	
	//unlock all buttons (make them enabled) after new game start
	//also remove their texts 
	private void unlockAllButtons()
	{
		Button buttonToUnlock;
    	
    	for (int i = 0; i < boardButtonIds.length; i++) 
    	{
    		buttonToUnlock = (Button)findViewById(boardButtonIds[i]);
    		buttonToUnlock.setEnabled(true);
    		buttonToUnlock.setText("");
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		//onBackPressed is disabled as it's not a big issue in demo app to handle it too
		//lets force users to end match by pressing end match button
	}

	//each click on each button changes that button's text to be either X or O
	//then next player is notified to proceed with playing
	//but before that, there is validation check if we have game result
	//which can be Draw, win of X or win of O
	@Override
	public void onClick(View v) 
	{
		switch(v.getId()) 
		{
	        case R.id.activity_board_button_1:
	        	
	    	break;
	    	
	        case R.id.activity_board_button_2:
	        	
	        break;
	        
	        case R.id.activity_board_button_3:
	     
	    	break;
	    	
	        case R.id.activity_board_button_4:
	        	
	        break;
		        
	        case R.id.activity_board_button_5:
		        	
	    	break;
	    	
	        case R.id.activity_board_button_6:
	        	
	        break;
	        
	        case R.id.activity_board_button_7:
			        	
	    	break;
	    	
	        case R.id.activity_board_button_8:
	        	
	        break;
	        
	        case R.id.activity_board_button_9:
	        	
	        break;
	        
	        case R.id.activity_board_button_end_match:
	        	//just for testing
				TicTacToeManager.boardOponents.getxPlayer().setCurrentWins(5);
				TicTacToeManager.boardOponents.getxPlayer().setCurrentLoses(2);
				TicTacToeManager.boardOponents.getoPlayer().setCurrentWins(2);
				TicTacToeManager.boardOponents.getoPlayer().setCurrentLoses(5);
				
				TicTacToeManager.recordResultsFromThisMatch((Activity) context);
				
				finish();
	        break;
		}
	}
}
