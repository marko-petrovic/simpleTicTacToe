package com.markopetrovic.simpletictactoe.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;
import com.markopetrovic.simpletictactoe.managers.TicTacToeManager;

public class MainActivity extends BaseActivity
{
	private TextView descriptionText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setUIstuff();
    }
    
    private void setUIstuff()
    {
    	descriptionText = (TextView)findViewById(R.id.activity_main_textview_main_description);
//    	descriptionText.setTypeface(defaultFont);
//    	descriptionText.setTextSize(TicTacToeManager.getAppContext().getResources().getDimension(R.dimen.main_activity_description_font_size));
//    	descriptionText.setTextColor(TicTacToeManager.getAppContext().getResources().getColor(R.color.textViewDescriptionOnMainActivity));
    }
}
