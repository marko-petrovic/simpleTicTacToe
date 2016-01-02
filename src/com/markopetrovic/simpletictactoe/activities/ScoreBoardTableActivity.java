package com.markopetrovic.simpletictactoe.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.adapters.ScoreboardListAdapter;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;
import com.markopetrovic.simpletictactoe.models.Player;
import com.markopetrovic.simpletictactoe.models.Scoreboard;

public class ScoreBoardTableActivity extends FragmentActivity 
{
	//this is Activity for showing scoreboard to users
	ScoreboardSectionsAdapter scoreboardSectionsAdapter;
	ViewPager pager;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        
        scoreboardSectionsAdapter = new ScoreboardSectionsAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(scoreboardSectionsAdapter);
    }
	
	public static class ScoreboardSectionsAdapter extends FragmentPagerAdapter
	{
		Scoreboard winnersList, losersList;
		
        public ScoreboardSectionsAdapter(FragmentManager fm)
        {
            super(fm);
            
            winnersList = Scoreboard.createScoreboard(new ArrayList<Player>(TicTacToeManager.scoreboardPlayers.sortScoreboardPlayersByTotalGamesWon()));
            losersList = Scoreboard.createScoreboard(new ArrayList<Player>(TicTacToeManager.scoreboardPlayers.sortScoreboardPlayersByTotalGamesLost()));
        }

        @Override
        public Fragment getItem(int i) 
        {
            switch (i) 
            {
                case 0:
                    return ScoreboardSectionFragment.newInstance(winnersList, true);
                    
                case 1:
                    return ScoreboardSectionFragment.newInstance(losersList, false);

                default:
                	return ScoreboardSectionFragment.newInstance(winnersList, true);
            }
        }

        @Override
        public int getCount() 
        {
        	//we will have winners and losers tabs, so that's why it's 2
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) 
        {
            if (position == 0) 
            {
            	//first tab, lets call it winners
            	return TicTacToeManager.getStringValue(R.string.activity_scoreboard_first_tab_label);
			}
            else
            {
            	//must be second tab, lets call it losers
            	return TicTacToeManager.getStringValue(R.string.activity_scoreboard_second_tab_label);
            }
        }
    }
	
	//lets not complicate things in this app, we will only have two lists, therefore boolean parameter
	//one will be for the biggest winners and another for players who have the biggest number of lost games
	public static class ScoreboardSectionFragment extends ListFragment 
	{
		//static factory method that takes parameters, puts them into arguments
		//and returns new instance of this fragment to whoever called it
		//therefore we skip custom constructors for ListFragment this way, what could cause crashes on recreating
		public static ScoreboardSectionFragment newInstance(Scoreboard scoreboard, boolean isThisWinnersList)
		{
			ScoreboardSectionFragment scoreboardSectionFragment = new ScoreboardSectionFragment();
	        Bundle args = new Bundle();
	        args.putSerializable("s", scoreboard);
	        args.putBoolean("isThisWinnersList", isThisWinnersList);
	        scoreboardSectionFragment.setArguments(args);
	        return scoreboardSectionFragment;
	    }
		
		@Override
		public void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);

			Bundle args = getArguments();
			if (args != null)
			{
				Scoreboard scoreboardToShow = (Scoreboard) args.get("s");
				
				//we needed this parameter so that our list adapter knows what data to insert into list items
				if (args.getBoolean("isThisWinnersList")) 
				{
					setListAdapter(new ScoreboardListAdapter(TicTacToeManager.getInstance().getApplicationContext(), scoreboardToShow.getScoreBoardPlayers(), true));
				}
				else
				{
					setListAdapter(new ScoreboardListAdapter(TicTacToeManager.getInstance().getApplicationContext(), scoreboardToShow.getScoreBoardPlayers(), false));
				}
			}
		}
    }
	
	@Override
	public void onBackPressed() 
	{
		finish();
	}
}
