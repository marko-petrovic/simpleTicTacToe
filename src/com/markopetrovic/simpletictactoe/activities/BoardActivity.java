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
	private final int[] boardButtonIds = 
	{
		R.id.activity_board_button_1, R.id.activity_board_button_2, R.id.activity_board_button_3,
		R.id.activity_board_button_4, R.id.activity_board_button_5, R.id.activity_board_button_6,
		R.id.activity_board_button_7, R.id.activity_board_button_8, R.id.activity_board_button_9
	};
	private Button buttonToUnlock;
	private Button buttonToLock;
	private Button buttonForBoard;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        
        //set all UI components to be ready to start match and games
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
    	LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(TicTacToeManager.deviceScreenWidth/4, TicTacToeManager.deviceScreenWidth/4);
    	
    	for (int i = 0; i < boardButtonIds.length; i++) 
    	{
			buttonForBoard = (Button)findViewById(boardButtonIds[i]);
			buttonForBoard.setLayoutParams(buttonParams);
			buttonForBoard.setTypeface(defaultFont);
			buttonForBoard.setOnClickListener(this);
		}
    	
    	//now just unlock all buttons and set their text to ""
    	//not really necessary to do but do it anyway, costs next to nothing
    	unlockOrLockAllButtons(false);
	}
	
	//after button was clicked on, make it disabled for more clicks
	private void lockButton(int id)
	{
		buttonToLock = (Button)findViewById(id);
		buttonToLock.setEnabled(false);
	}
	
	//unlock all buttons (make them enabled) after new game start
	//also remove their texts
	//or lock them all and do not remove texts (case when we have a game result)
	private void unlockOrLockAllButtons(boolean lockThemAll)
	{
		if (lockThemAll) 
		{
			for (int i = 0; i < boardButtonIds.length; i++) 
	    	{
	    		buttonToUnlock = (Button)findViewById(boardButtonIds[i]);
	    		buttonToUnlock.setEnabled(false);
			}
		}
		else
		{
			for (int i = 0; i < boardButtonIds.length; i++) 
	    	{
	    		buttonToUnlock = (Button)findViewById(boardButtonIds[i]);
	    		buttonToUnlock.setEnabled(true);
	    		buttonToUnlock.setText("");
			}
		}
	}
	
	//set button value (player's move)
	private void updateButton(int id)
	{
		buttonForBoard = (Button)findViewById(id);
		
		if (TicTacToeManager.boardOponents.getxPlays()) 
		{
			//it means that X has played, therefore set Button text to "X" and set other values
			//to correspond with this move. Then set xPlays to ! value of a current value
			buttonForBoard.setText("X");
			buttonForBoard.setTextColor(TicTacToeManager.getInstance().getResources().getColor(R.color.blue));
			TicTacToeManager.boardOponents.setxPlays(false);
		}
		else
		{
			//this means that O player just played. Set text to "O" and set xPlays to true
			//so next button will know that X player played
			buttonForBoard.setText("O");
			buttonForBoard.setTextColor(TicTacToeManager.getInstance().getResources().getColor(R.color.red));
			TicTacToeManager.boardOponents.setxPlays(true);
		}
		
		lockButton(id);
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
		if (v.getId() == R.id.activity_board_button_end_match) 
		{
			//just for testing
			TicTacToeManager.boardOponents.getxPlayer().setCurrentWins(5);
			TicTacToeManager.boardOponents.getxPlayer().setCurrentLoses(2);
			TicTacToeManager.boardOponents.getoPlayer().setCurrentWins(2);
			TicTacToeManager.boardOponents.getoPlayer().setCurrentLoses(5);
			
			TicTacToeManager.recordResultsFromThisMatch((Activity) context);
			
			finish();
		}
		else
		{
			//must be any of board buttons
			updateButton(v.getId());
		}
	}
}
