package com.markopetrovic.simpletictactoe.models;

import java.util.ArrayList;

public class Scoreboard 
{
	//Scoreboard is a simple ArrayList of Players who played so far
	private ArrayList<Player> scoreBoardPlayers;
	
	public static Scoreboard createScoreboard(ArrayList<Player> scoreBoardPlayers) 
	{
		return new Scoreboard(scoreBoardPlayers);
	}

	private Scoreboard(ArrayList<Player> scoreBoardPlayers) 
	{
		this.scoreBoardPlayers = scoreBoardPlayers;
	}

	public ArrayList<Player> getScoreBoardPlayers()
	{
		return scoreBoardPlayers;
	}

	public void setScoreBoardPlayers(ArrayList<Player> scoreBoardPlayers)
	{
		this.scoreBoardPlayers = scoreBoardPlayers;
	}
}
