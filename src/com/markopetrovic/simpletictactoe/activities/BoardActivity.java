package com.markopetrovic.simpletictactoe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.GameResultResolver.StateEnum;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;

public class BoardActivity extends BaseActivity implements OnClickListener 
{
	//this is Activity where TicTacToe game is being played
	
	private TextView descriptionText;
	private TextView xPlayerName, oPlayerName;
	private TextView xPlayerCurrentWins, oPlayerCurrentWins;
	private TextView xPlayerPreviousWins, xPlayerPreviousLoses;
	private TextView oPlayerPreviousWins, oPlayerPreviousLoses;
	private Button endMatchButton;
	private Button playAgainButton;
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
    } 
	
	@Override
	public void setUIstuff()
	{
		//init description textViews and set font we want
    	descriptionText = (TextView)findViewById(R.id.activity_board_textview_board_description);
    	descriptionText.setTypeface(defaultFont);
    	
    	//xPlayer name and scores values
    	xPlayerName = (TextView)findViewById(R.id.activity_board_textview_xplayer_name);
    	xPlayerCurrentWins = (TextView)findViewById(R.id.activity_board_textview_xplayer_current_wins);
    	xPlayerPreviousWins = (TextView)findViewById(R.id.activity_board_textview_xplayer_prev_wins);
    	xPlayerPreviousLoses = (TextView)findViewById(R.id.activity_board_textview_xplayer_prev_loses);
    	xPlayerName.setTypeface(defaultFont);
    	xPlayerCurrentWins.setTypeface(defaultFont);
    	xPlayerPreviousWins.setTypeface(defaultFont);
    	xPlayerPreviousLoses.setTypeface(defaultFont);
    	
    	//oPlayer name and scores values
    	oPlayerName = (TextView)findViewById(R.id.activity_board_textview_oplayer_name);
    	oPlayerCurrentWins = (TextView)findViewById(R.id.activity_board_textview_oplayer_current_wins);
    	oPlayerPreviousWins = (TextView)findViewById(R.id.activity_board_textview_oplayer_prev_wins);
    	oPlayerPreviousLoses = (TextView)findViewById(R.id.activity_board_textview_oplayer_prev_loses);
    	oPlayerName.setTypeface(defaultFont);
    	oPlayerCurrentWins.setTypeface(defaultFont);
    	oPlayerPreviousWins.setTypeface(defaultFont);
    	oPlayerPreviousLoses.setTypeface(defaultFont);
    	
    	//play again button
    	playAgainButton = (Button)findViewById(R.id.activity_board_button_play_next_game);
    	playAgainButton.setTypeface(defaultFont);
    	playAgainButton.setOnClickListener(this);
    	playAgainButton.setEnabled(false);
    	
    	//end match button
    	endMatchButton = (Button)findViewById(R.id.activity_board_button_end_match);
    	endMatchButton.setTypeface(defaultFont);
    	endMatchButton.setOnClickListener(this);
    	endMatchButton.setEnabled(false);
    	
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
	
	//doing the opposite
	private void unlockButton(int id)
	{
		buttonToUnlock = (Button)findViewById(id);
		buttonToUnlock.setEnabled(true);
	}
	
	//unlock all board buttons (make them enabled) after new game start
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
		}
		else
		{
			//this means that O player just played. Set text to "O" and set xPlays to true
			//so next button will know that X player played
			buttonForBoard.setText("O");
			buttonForBoard.setTextColor(TicTacToeManager.getInstance().getResources().getColor(R.color.red));
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
			//end the whole match by saving gained results and going to scoreboard list
			TicTacToeManager.recordResultsFromThisMatch((Activity) context);
			
			finish();
		}
		else if (v.getId() == R.id.activity_board_button_play_next_game)
		{
			//users want to play another game so reset everything and let them play
			unlockOrLockAllButtons(false);
			
			TicTacToeManager.boardOponents.setxPlays(true);
			TicTacToeManager.boardOponents.setCounter(0);
			TicTacToeManager.initBoard();
			
			//lock this button again until game is over
			lockButton(R.id.activity_board_button_play_next_game);
		}
		else
		{
			//must be any of board buttons
			updateButton(v.getId());
			
			//check if we have game result
			StateEnum gameResult = TicTacToeManager.updateBoard(v.getId());
			
			if (TicTacToeManager.boardOponents.getxPlays() == null) 
			{
				//update UI with data of wins and loses
				System.out.println("winner is " + gameResult.toString());
				System.out.println(TicTacToeManager.boardOponents.getxPlayer().getPlayer().getName() + ": wins " + TicTacToeManager.boardOponents.getxPlayer().getCurrentWins()
						+ ", loses " + TicTacToeManager.boardOponents.getxPlayer().getCurrentLoses());
				System.out.println(TicTacToeManager.boardOponents.getoPlayer().getPlayer().getName() + ": wins " + TicTacToeManager.boardOponents.getoPlayer().getCurrentWins()
						+ ", loses " + TicTacToeManager.boardOponents.getoPlayer().getCurrentLoses());
				//TODO
				
				//game resulted and ended, we have to restart it, but users will do that by pressing
				//PLAY AGAIN button. That button will do this job, so just enable button here
				unlockButton(R.id.activity_board_button_play_next_game);
			}
		}
	}
}
