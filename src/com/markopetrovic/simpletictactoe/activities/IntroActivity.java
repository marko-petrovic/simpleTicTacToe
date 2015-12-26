package com.markopetrovic.simpletictactoe.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager.TransitionType;

public class IntroActivity extends BaseActivity
{
	private TextView descriptionText;
	private TextView enterXname, enterOname;
	private EditText xName, oName;
	private Button startGameButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setUIstuff();
    } 
    
    @Override
	public void setUIstuff()
    {
    	//init description textViews and set font we want
    	descriptionText = (TextView)findViewById(R.id.activity_main_textview_main_description);
    	descriptionText.setTypeface(defaultFont);
    	
    	enterXname = (TextView)findViewById(R.id.activity_main_textview_x_player_desc);
    	enterXname.setTypeface(defaultFont);
    	enterOname = (TextView)findViewById(R.id.activity_main_textview_o_player_desc);
    	enterOname.setTypeface(defaultFont);
    	
    	//init editText fields for both players
    	xName = (EditText)findViewById(R.id.activity_main_edittext_x_player);
    	xName.setTypeface(defaultFont);
    	oName = (EditText)findViewById(R.id.activity_main_edittext_o_player);
    	oName.setTypeface(defaultFont);
    	
    	//init startGame button
    	startGameButton = (Button)findViewById(R.id.activity_main_button_start_game);
    	startGameButton.setTypeface(defaultFont);
    	startGameButton.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				validateNames();
			}
		});
    }
    
    //if names are valid (non empty) then board players will be created and set to the board to play 
    private void validateNames()
    {
    	// if email or password are empty
		if (xName.getText().toString().trim().isEmpty())
		{
			xName.setHintTextColor(TicTacToeManager.getInstance().getResources().getColor(R.color.red));
		}

		if (oName.getText().toString().trim().isEmpty())
		{
			oName.setHintTextColor(TicTacToeManager.getInstance().getResources().getColor(R.color.red));
		}
		
		if (!xName.getText().toString().trim().isEmpty() && !oName.getText().toString().trim().isEmpty())
		{
			System.out.println("STARTING GAME FOR PLAYERS " + xName.getText().toString() + " and " + oName.getText().toString());
			
			//tell TicTacToeManager to take care of this two users who want to play
			//TicTacToeManager will take their names and check if they already played before
			//if so, their BoardPlayer models will have Player model with previous scores
			//if not, they will be created into primitive sharedprefs storage as new Players
			//and they will anyway get their new BoardPlayer objects to play with
			//this is pretty simple and primitive logic but we don't really have to 
			//introduce login logic into this simple game demo app
			TicTacToeManager.takeCareOfTheseTwoOponents(xName.getText().toString(), oName.getText().toString());
			
			//TicTacToeManager prepared BoardPlayers in BoardOponents object so all we have to do now
			//is to fire up an Intent towards BoardActivity and start playing , but even that will be given to
			//TicTacToeManager as it's nice to centralize this stuff somewhere as we might have more Activity switching points elsewhere
			TicTacToeManager.startActivity(this, BoardActivity.class);
		}
    }
}
