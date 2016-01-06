package com.markopetrovic.simpletictactoe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.GameResultResolver;
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
	//set gameResult initially to DRAW
	private StateEnum gameResult = StateEnum.DRAW;
	
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
    	xPlayerName.setText(TicTacToeManager.boardOponents.getxPlayer().getPlayer().getName());
    	xPlayerCurrentWins.setText(String.valueOf(TicTacToeManager.boardOponents.getxPlayer().getCurrentWins()));
    	xPlayerPreviousWins.setText(String.valueOf(TicTacToeManager.boardOponents.getxPlayer().getPlayer().getTotalGamesWon()));
    	xPlayerPreviousLoses.setText(String.valueOf(TicTacToeManager.boardOponents.getxPlayer().getPlayer().getTotalGamesLost()));
    	
    	//oPlayer name and scores values
    	oPlayerName = (TextView)findViewById(R.id.activity_board_textview_oplayer_name);
    	oPlayerCurrentWins = (TextView)findViewById(R.id.activity_board_textview_oplayer_current_wins);
    	oPlayerPreviousWins = (TextView)findViewById(R.id.activity_board_textview_oplayer_prev_wins);
    	oPlayerPreviousLoses = (TextView)findViewById(R.id.activity_board_textview_oplayer_prev_loses);
    	oPlayerName.setTypeface(defaultFont);
    	oPlayerCurrentWins.setTypeface(defaultFont);
    	oPlayerPreviousWins.setTypeface(defaultFont);
    	oPlayerPreviousLoses.setTypeface(defaultFont);
    	oPlayerName.setText(TicTacToeManager.boardOponents.getoPlayer().getPlayer().getName());
    	oPlayerCurrentWins.setText(String.valueOf(TicTacToeManager.boardOponents.getoPlayer().getCurrentWins()));
    	oPlayerPreviousWins.setText(String.valueOf(TicTacToeManager.boardOponents.getoPlayer().getPlayer().getTotalGamesWon()));
    	oPlayerPreviousLoses.setText(String.valueOf(TicTacToeManager.boardOponents.getoPlayer().getPlayer().getTotalGamesLost()));
    	
    	//play again button
    	playAgainButton = (Button)findViewById(R.id.activity_board_button_play_next_game);
    	playAgainButton.setTypeface(defaultFont);
    	playAgainButton.setOnClickListener(this);
    	playAgainButton.setEnabled(false);
    	
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

	//onClickListener for board buttons and for new game and end match buttons
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
			
			//tell who's playing first
			if (TicTacToeManager.boardOponents.isPreviousPlayerWasX()) 
			{
				TicTacToeManager.boardOponents.setxPlays(true);
				descriptionText.setText(TicTacToeManager.boardOponents.getxPlayer().getPlayer().getName() +
						TicTacToeManager.getStringValue(R.string.activity_board_has_the_first_move));
				descriptionText.setTextColor(TicTacToeManager.getInstance().getResources().getColor(R.color.blue));
			}
			else
			{
				TicTacToeManager.boardOponents.setxPlays(false);
				descriptionText.setText(TicTacToeManager.boardOponents.getoPlayer().getPlayer().getName() +
						TicTacToeManager.getStringValue(R.string.activity_board_has_the_first_move));
				descriptionText.setTextColor(TicTacToeManager.getInstance().getResources().getColor(R.color.red));
			}
			
			//re init board and set counter to 0
			TicTacToeManager.boardOponents.setCounter(0);
			TicTacToeManager.initBoard(TicTacToeManager.boardDimension);
			
			//lock this button again until game is over
			lockButton(R.id.activity_board_button_play_next_game);
		}
		else
		{
			//must be any of board buttons clicked
			//this method only updates button representation and locks it 
			updateButton(v.getId());
			
			//check if we have game result by updating board 
			gameResult = TicTacToeManager.updateBoard(v.getId());
			
			//we can use Booleans's null value to indicate that this game has result
			if (TicTacToeManager.boardOponents.getxPlays() == null) 
			{
				//lock all board buttons cos there's game result so users can't go on playing
				unlockOrLockAllButtons(true);
				
				//update UI with data of wins and loses
				if (gameResult.toString().contains(GameResultResolver.StateEnum.DRAW.toString()))
				{
					descriptionText.setText(TicTacToeManager.getStringValue(R.string.activity_board_description_no_winner_this_time));
				}
				else if (gameResult.toString().contains(GameResultResolver.StateEnum.X.toString()))
				{
					descriptionText.setText(TicTacToeManager.getStringValue(R.string.activity_board_description_winner_is) + 
							TicTacToeManager.boardOponents.getxPlayer().getPlayer().getName());
					xPlayerCurrentWins.setText(String.valueOf(TicTacToeManager.boardOponents.getxPlayer().getCurrentWins()));
					xPlayerPreviousWins.setText(String.valueOf(TicTacToeManager.boardOponents.getxPlayer().getCurrentWins() + TicTacToeManager.boardOponents.getxPlayer().getPlayer().getTotalGamesWon()));
					oPlayerPreviousLoses.setText(String.valueOf(TicTacToeManager.boardOponents.getoPlayer().getCurrentLoses() + TicTacToeManager.boardOponents.getoPlayer().getPlayer().getTotalGamesLost()));
				}
				else
				{
					descriptionText.setText(TicTacToeManager.getStringValue(R.string.activity_board_description_winner_is) + 
							TicTacToeManager.boardOponents.getoPlayer().getPlayer().getName());
					oPlayerCurrentWins.setText(String.valueOf(TicTacToeManager.boardOponents.getoPlayer().getCurrentWins()));
					oPlayerPreviousWins.setText(String.valueOf(TicTacToeManager.boardOponents.getoPlayer().getCurrentWins() + TicTacToeManager.boardOponents.getoPlayer().getPlayer().getTotalGamesWon()));
					xPlayerPreviousLoses.setText(String.valueOf(TicTacToeManager.boardOponents.getxPlayer().getCurrentLoses() + TicTacToeManager.boardOponents.getxPlayer().getPlayer().getTotalGamesLost()));
				}
				
				//game resulted and ended, we have to restart it, but users will do that by pressing
				//PLAY AGAIN button. That button will do this job, so just enable button here
				unlockButton(R.id.activity_board_button_play_next_game);
			}
		}
	}
}