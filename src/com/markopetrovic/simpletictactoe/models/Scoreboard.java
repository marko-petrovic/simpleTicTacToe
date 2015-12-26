package com.markopetrovic.simpletictactoe.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Scoreboard implements Serializable
{
	//Scoreboard is a simple ArrayList of Players who played so far
	private ArrayList<Player> scoreBoardPlayers;
	private static final long serialVersionUID = 1L;
	
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
