package com.markopetrovic.simpletictactoe.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.markopetrovic.simpletictactoe.R;

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
    	//init description textView and set font we want
    	descriptionText = (TextView)findViewById(R.id.activity_main_textview_main_description);
    	descriptionText.setTypeface(defaultFont);
    	
    	//init editText fields for both players
    	//TODO
    	
    	//init startGame button
    	//TODO
    }
}
