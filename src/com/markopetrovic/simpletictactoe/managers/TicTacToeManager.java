package com.markopetrovic.simpletictactoe.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.activities.ScoreBoardTableActivity;
import com.markopetrovic.simpletictactoe.managers.GameResultResolver.StateEnum;
import com.markopetrovic.simpletictactoe.models.Board;
import com.markopetrovic.simpletictactoe.models.BoardOpponents;
import com.markopetrovic.simpletictactoe.models.BoardPlayer;
import com.markopetrovic.simpletictactoe.models.Player;
import com.markopetrovic.simpletictactoe.models.Scoreboard;
import com.markopetrovic.simpletictactoe.utils.ObjectSerializer;

public class TicTacToeManager extends Application 
{
	public enum TransitionType   
	{
		SLIDE_UP, SLIDE_DOWN, SLIDE_LEFT, SLIDE_RIGHT
	};

	private static Context currentContext;
	public static SharedPreferences appPreferences;
	public static int deviceScreenWidth;
    public static int deviceScreenHeight;
    public static Scoreboard scoreboardPlayers;
    public static BoardOpponents boardOponents;
    private static int indexX, indexO;
    public static Board board;
    public static int boardDimension = 3;
	
	private static TicTacToeManager sInstance;
	
    public static TicTacToeManager getInstance() 
    {
        return sInstance;
    }

	public void onCreate()
	{
		super.onCreate();
		
		//get Singleton instance of Application and initialize other stuff
		sInstance = this;
        sInstance.initializeInstance();

		System.out.println("APPLICATION CREATED");
	}
	
	private void initializeInstance() 
	{
		//get screen dimens values here, we can use them for XO fields dimensions later
        screenConfiguration();
		
		// set application shared prefs
        loadScoreboard();
    }
	
	@SuppressWarnings("deprecation")
	public void screenConfiguration() 
	{
        Point size = new Point();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        try 
        {
            display.getSize(size);
            deviceScreenWidth = size.x;
            deviceScreenHeight = size.y;
        } 
        catch (NoSuchMethodError e) 
        {
            deviceScreenWidth = display.getWidth();
            deviceScreenHeight = display.getHeight();
        }
        
//        System.out.println("SCREEN DIMENSIONS ARE ");
//        System.out.println("deviceScreenWidth is " + deviceScreenWidth);
//        System.out.println("deviceScreenHeight is " + deviceScreenHeight);
    }

	//preferences and loading stuff from them
	//we use prefs for storing player scores in this simple app
	//that means that we serialize and deserialize Scoreboard into prefs
	//this is more efficient approach than using heavy Gson for such simple usecase
	//as our Player objects have only String and int values, this is pretty light and safe to serialize and deserialize
	//we won't be using Parcelable interface here cos we aren't communicating between processes so we don't need speed
	//and if Android changes Parcelable API in the meantime, we have no choice but to raise white flag
	//byte[] to String and back is in my opinion fair approach to our data storing usecase
	public static void loadScoreboard()
	{
		appPreferences = sInstance.getApplicationContext().getSharedPreferences("appPrefs", MODE_PRIVATE);
		
		//now get scoreboard arraylist of Players
		//if app was never used to play tic tac toe, result will be empty array [], exactly what we need
		try 
		{
			scoreboardPlayers = (Scoreboard) ObjectSerializer.deserializeObject
					(appPreferences.getString("SCOREBOARD", ObjectSerializer.serializeObject(Scoreboard.createScoreboard(new ArrayList<Player>()))));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//method to save scoreboardPlayers into shared prefs by overriding over already saved scoreboard in prefs
	public static void updateScoreboard(Activity anyBoardActivity)
	{
		SharedPreferences.Editor appPrefsEditor = appPreferences.edit();
		
        try 
        {
        	appPrefsEditor.putString("SCOREBOARD", ObjectSerializer.serializeObject(scoreboardPlayers));
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        appPrefsEditor.commit();
        
        //we will call this method only when match is over, therefore now when updating is done
        //app can transit into ScoreBoardTableActivity, that's why we have anyBoardActivity
        startActivity(anyBoardActivity, ScoreBoardTableActivity.class);
	}
	
	//this is called when match is over
	//players gave up playing more games, so app has to take their scores and write them into
	//scoreBoard. That's where we use indexes, in order not to loop again through arraylist
	//if indexes are -1 that tells us that this were totally new players to we have to 
	//add them to the end of arraylist as new entries
	//otherwise, just change values of scoreBoard objects at indexes
	//after this, updateScoreboard() and we're done so app can go to ScoreBoardTableActivity
	public static void recordResultsFromThisMatch(Activity anyBoardActivity)
	{
		//for player who was X
		if (indexX != -1) 
		{
			//just update player at this index
			scoreboardPlayers.getScoreBoardPlayers().get(indexX).setTotalGamesWon
			(boardOponents.getxPlayer().getCurrentWins() + boardOponents.getxPlayer().getPlayer().getTotalGamesWon());
			
			scoreboardPlayers.getScoreBoardPlayers().get(indexX).setTotalGamesLost
			(boardOponents.getxPlayer().getCurrentLoses() + boardOponents.getxPlayer().getPlayer().getTotalGamesLost());
		}
		else
		{
			//add player into arraylist as a totally new player in scoreboard
			Player resultingXPlayer = Player.createPlayer
			(
				boardOponents.getxPlayer().getPlayer().getName(),
				boardOponents.getxPlayer().getCurrentWins(),
				boardOponents.getxPlayer().getCurrentLoses()
			);
			scoreboardPlayers.getScoreBoardPlayers().add(resultingXPlayer);
		}
		
		//for player who was O, exactly the same as above
		if (indexO != -1) 
		{
			//just update player at this index
			scoreboardPlayers.getScoreBoardPlayers().get(indexO).setTotalGamesWon
			(boardOponents.getoPlayer().getCurrentWins() + boardOponents.getoPlayer().getPlayer().getTotalGamesWon());
			
			scoreboardPlayers.getScoreBoardPlayers().get(indexO).setTotalGamesLost
			(boardOponents.getoPlayer().getCurrentLoses() + boardOponents.getoPlayer().getPlayer().getTotalGamesLost());
		}
		else
		{
			//add new player in scoreboard
			Player resultingOPlayer = Player.createPlayer
			(
				boardOponents.getoPlayer().getPlayer().getName(),
				boardOponents.getoPlayer().getCurrentWins(),
				boardOponents.getoPlayer().getCurrentLoses()
			);
			scoreboardPlayers.getScoreBoardPlayers().add(resultingOPlayer);
		}
		
		//now when we're done updating values, call updateScoreboard()
		updateScoreboard(anyBoardActivity);
	}

	//taking care of players is here
	public static void takeCareOfTheseTwoOponents(String xPlayer, String oPlayer)
	{
		if (scoreboardPlayers.getScoreBoardPlayers() != null && scoreboardPlayers.getScoreBoardPlayers().size() != 0) 
		{
			Player xPlayerObj = null;
			Player oPlayerObj = null;
			boolean xFound = false;
			boolean oFound = false;
			
			//loop through all players from scoreboard and see if there's one named as any of the two who will play now
			//this is silly without a way to login players, but it's silly approach in this demo simple app
			//not to have doubled entries in our scoreboard
			for (int i = 0; i < scoreboardPlayers.getScoreBoardPlayers().size(); i++) 
			{
				if (scoreboardPlayers.getScoreBoardPlayers().get(i).getName().contentEquals(xPlayer) && !xFound) 
				{
					//player found
					xPlayerObj = Player.createPlayer
					(
						scoreboardPlayers.getScoreBoardPlayers().get(i).getName(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getTotalGamesWon(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getTotalGamesLost()
					);
					xFound = true;
					indexX = i;
				}
				else if (scoreboardPlayers.getScoreBoardPlayers().get(i).getName().contentEquals(oPlayer) && !oFound) 
				{
					//player found
					oPlayerObj = Player.createPlayer
					(
						scoreboardPlayers.getScoreBoardPlayers().get(i).getName(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getTotalGamesWon(),
						scoreboardPlayers.getScoreBoardPlayers().get(i).getTotalGamesLost()
					);
					oFound = true;
					indexO = i;
				}
			}
			
			//now lets construct boardOponents
			if (!xFound) 
			{
				xPlayerObj = Player.createPlayer(xPlayer, 0, 0);
				indexX = -1;
			}
			
			if (!oFound) 
			{
				oPlayerObj = Player.createPlayer(oPlayer, 0, 0);
				indexO = -1;
			}
			
			//finally
			boardOponents = BoardOpponents.createBoardOpponents
				(
					BoardPlayer.createBoardPlayer
					(
						xPlayerObj, 0, 0
					), 
					BoardPlayer.createBoardPlayer
					(
						oPlayerObj, 0, 0
					)
				);
		}
		else
		{
			//game is started for the very first time so there's nobody in scoreboard arraylist
			//therefore, create brand new boardOponents and boardPlayers
			boardOponents = BoardOpponents.createBoardOpponents
			(
				BoardPlayer.createBoardPlayer
				(
					Player.createPlayer(xPlayer, 0, 0), 0, 0
				), 
				BoardPlayer.createBoardPlayer
				(
					Player.createPlayer(oPlayer, 0, 0), 0, 0
				)
			);
			
			indexX = -1;
			indexO = -1;
		}
		
		//now board opponents are created and we only have to init empty board and that's 
		//what's needed for a game to start
		initBoard(boardDimension);
	}
	
	//this is maybe not too necessary in our simple demo app case, but if we want to pass arguments for intent.putExtra
	//we can prepare them into HashMap<String, String> and pass it as parameter in this method
	public static void startActivity(Activity context, @SuppressWarnings("rawtypes") Class activity, HashMap<String, String> arguments, TransitionType transition)
	{
		//define intent, add flags, arguments and set transition
		final Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (arguments != null)
		{
			for (Entry<String, String> entry : arguments.entrySet())
			{
				intent.putExtra(entry.getKey(), entry.getValue());
			}
		}

		if (transition == null)
		{
			context.overridePendingTransition(R.animator.animation_slide_left, R.animator.animation_slide_left_out);
		}
		else
			switch (transition)
			{
				case SLIDE_UP:
					context.overridePendingTransition(R.animator.animation_slide_up, R.animator.animation_slide_up_out);
				break;
				case SLIDE_DOWN:
					context.overridePendingTransition(R.animator.animation_slide_down_out, R.animator.animation_slide_down);
				break;
				case SLIDE_LEFT:
					context.overridePendingTransition(R.animator.animation_slide_left, R.animator.animation_slide_left_out);
				break;
				case SLIDE_RIGHT:
					context.overridePendingTransition(R.animator.animation_slide_right, R.animator.animation_slide_right_out);
				break;
			}

		//starting another activity from the one where we called this method
		context.startActivity(intent);
	}
	
	//simplified starting of Activity without unnecessary transitions and extras
	public static void startActivity(Activity context, @SuppressWarnings("rawtypes") Class activity)
	{
		TicTacToeManager.startActivity(context, activity, null, null);
	}
	
	//setting current Activity
	//careful with setting this value cos with Activity recreations you might create
	//possible memory leaks if reference is left and Activity or Fragment is recreated
	public static Context getCurrentContext() 
	{
		return currentContext;
	}

	//setting current Context if we need it
	public static void setCurrentContext(Context currentContext) 
	{
		TicTacToeManager.currentContext = currentContext;
	}
	
	//getting Strings throughout the app
	public static String getStringValue(int resId)
	{
		return sInstance.getApplicationContext().getResources().getString(resId);
	}

	//method to update board and return result if there's result
	public static StateEnum updateBoard(int id) 
	{
		//positions in board
		int rowPosition = 0;
		int columnPosition = 0;
		
		//depending on which button was clicked we will set positions
		switch (id) 
		{
			case R.id.activity_board_button_1:
				rowPosition = 0;
				columnPosition = 0;
				break;

			case R.id.activity_board_button_2:
				rowPosition = 0;
				columnPosition = 1;
				break;
				
			case R.id.activity_board_button_3:
				rowPosition = 0;
				columnPosition = 2;
				break;
				
			case R.id.activity_board_button_4:
				rowPosition = 1;
				columnPosition = 0;
				break;
				
			case R.id.activity_board_button_5:
				rowPosition = 1;
				columnPosition = 1;
				break;
				
			case R.id.activity_board_button_6:
				rowPosition = 1;
				columnPosition = 2;
				break;
				
			case R.id.activity_board_button_7:
				rowPosition = 2;
				columnPosition = 0;
				break;
				
			case R.id.activity_board_button_8:
				rowPosition = 2;
				columnPosition = 1;
				break;
				
			case R.id.activity_board_button_9:
				rowPosition = 2;
				columnPosition = 2;
				break;
		}
		
		//now see who played and set board values accordingly
		//setting state enums in board[][] is possibly not needed, but that
		//comes as if we want to evaluate board with GameResultResolver.checkForGameResult() method
		if (boardOponents.getxPlays().booleanValue())
		{
			board.getBoard()[rowPosition][columnPosition] = StateEnum.X;
			updateBoardSums(rowPosition, columnPosition, StateEnum.X);
			boardOponents.setxPlays(false);
		}
		else
		{
			board.getBoard()[rowPosition][columnPosition] = StateEnum.O;
			updateBoardSums(rowPosition, columnPosition, StateEnum.O);
			boardOponents.setxPlays(true);
		}
		
		//button was clicked, so increase counter for one more move played
		boardOponents.setCounter(boardOponents.getCounter() + 1);
		
		//check if we have game result
		
//		StateEnum gameResult = GameResultResolver.checkForGameResult(board.getBoard());
		StateEnum gameResult = GameResultResolver.checkForGameResultUsingSums();
		
		//we will end our game by settingxPlayes to null only if gameResult is not DRAW
		//or if counter counted up to NxN so there are no more fields to play with
		if (gameResult != StateEnum.DRAW || boardOponents.getCounter() == (board.getBoardDimension() * board.getBoardDimension())) 
		{
			//we have a result for X or O player, so lets use null for xPlays Boolean as third state
			//we will check for this state in BoardActivity
			boardOponents.setxPlays(null);
			
			//lets also record who played the last so we can switch who plays next by reading this value
			if (boardOponents.isPreviousPlayerWasX()) 
			{
				boardOponents.setPreviousPlayerWasX(false);
			}
			else
			{
				boardOponents.setPreviousPlayerWasX(true);
			}
			
			if (gameResult == StateEnum.X) 
			{
				boardOponents.getxPlayer().setCurrentWins(boardOponents.getxPlayer().getCurrentWins() + 1);
				boardOponents.getoPlayer().setCurrentLoses(boardOponents.getoPlayer().getCurrentLoses() + 1);
			}
			else if (gameResult == StateEnum.O) 
			{
				boardOponents.getxPlayer().setCurrentLoses(boardOponents.getxPlayer().getCurrentLoses() + 1);
				boardOponents.getoPlayer().setCurrentWins(boardOponents.getoPlayer().getCurrentWins() + 1);
			}
		}
		
		return gameResult;
	}
	
	//update sums of rows, columns and possibly diagonals based on which player played what
	public static void updateBoardSums(int rowPosition, int columnPosition, StateEnum whoPlayed)
	{
		int valueToBeAdded = 0;
		
		//if X played, we add 1, if O played, we add -1
		//complete winning row, column or diagonal is the one with all X or O adds
		//which means it's either having value of N either -N
		if (whoPlayed == StateEnum.X) 
		{
			valueToBeAdded++;
		}
		else
		{
			valueToBeAdded--;
		}
		
		board.getRowValue()[rowPosition] = board.getRowValue()[rowPosition] + valueToBeAdded;
		board.getColumnValue()[columnPosition] = board.getColumnValue()[columnPosition] + valueToBeAdded;
		if (rowPosition == columnPosition)
		{
			//this is definitely \ diagonal 
			board.getDiagonalValue()[0] = board.getDiagonalValue()[0] + valueToBeAdded;
		}
		
		//it can also be / diagonal if position is central or if second condition is fulfilled
		if (
				rowPosition == board.getCentralPosition() && columnPosition == board.getCentralPosition() ||
				(rowPosition + columnPosition) == board.getBoardDimension() - 1
		   )
		{
			board.getDiagonalValue()[1] = board.getDiagonalValue()[1] + valueToBeAdded;
		}
	}
	
	//initialization of NxN board and its row, column and diagonal sums
	public static void initBoard(int boardDimension)
	{
		board = Board.createBoard
				(
					new StateEnum[boardDimension][boardDimension],
					new int[boardDimension],
					new int[boardDimension],
					new int[2],
					boardDimension
				);
	}
}