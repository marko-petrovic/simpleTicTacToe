package com.markopetrovic.simpletictactoe.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;
import com.markopetrovic.simpletictactoe.models.Player;

public class ScoreboardListAdapter extends ArrayAdapter<Player>
{
	private boolean isThisWinnersList = true;
	protected Typeface listAdapterFont; 
	
    public ScoreboardListAdapter(Context context, ArrayList<Player> items, boolean isThisWinnersList) 
    {
        super(context, R.layout.scoreboard_listview_item, items);
        
        this.isThisWinnersList = isThisWinnersList;
        listAdapterFont = Typeface.createFromAsset(TicTacToeManager.getInstance().getAssets(), "fonts/TitilliumWeb-Regular.ttf");
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        
        if(convertView == null)
        {
            //inflate item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.scoreboard_listview_item, parent, false);
            
            //init view holder
            viewHolder = new ViewHolder();
            viewHolder.playerName = (TextView) convertView.findViewById(R.id.activity_scoreboard_listview_item_name);
            viewHolder.playerScore = (TextView) convertView.findViewById(R.id.activity_scoreboard_listview_item_score);
            viewHolder.playerName.setTypeface(listAdapterFont);
            viewHolder.playerScore.setTypeface(listAdapterFont);
            convertView.setTag(viewHolder);
        }
        else
        {
            //recycle already inflated view by tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        //update the item view with data for Player and then return our View
        Player player = getItem(position);
        viewHolder.playerName.setText(player.getName());
        
        if (isThisWinnersList) 
        {
        	viewHolder.playerScore.setText(String.valueOf(player.getTotalGamesWon()));
		}
        else
        {
        	viewHolder.playerScore.setText(String.valueOf(player.getTotalGamesLost()));
        }
        
        return convertView;
    }
    
    //lets use view holder pattern here so we dont have to use costly findViewById() every time
    //when we call getView() up there in adapter. Therefore, we will have smoother scrolling
    private static class ViewHolder 
    {
        TextView playerName;
        TextView playerScore;
    }
}