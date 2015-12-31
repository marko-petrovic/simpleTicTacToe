package com.markopetrovic.simpletictactoe.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;

public class ScoreBoardTableActivity extends FragmentActivity /*implements ActionBar.TabListener*/
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
        public ScoreboardSectionsAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) 
        {
            switch (i) 
            {
                case 0:
                    return new ScoreboardSectionFragment(0);
                    
                case 1:
                    return new ScoreboardSectionFragment(1);

                default:
                	return new ScoreboardSectionFragment(0);
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
	
	public static class ScoreboardSectionFragment extends Fragment 
	{
		private int whichList = 0;
		
		public ScoreboardSectionFragment(int which)
        {
            whichList = which;
        }
		
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) 
        {
            View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
            TextView tv = (TextView)rootView.findViewById(R.id.textDesc);
            
            if (whichList == 0) 
            {
				tv.setText("WINNERS");
			}
            else
            {
            	tv.setText("LOSERS");
            }

            return rootView;
        }
    }
	
	@Override
	public void onBackPressed() 
	{
		finish();
	}
}
