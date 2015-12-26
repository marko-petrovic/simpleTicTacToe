package com.markopetrovic.simpletictactoe.models;

public class Player
{
	//this is a model class of our player
	//player has name and total score
	
	public static Player createPlayer(String name, int matchesWon, int matchesLost) 
	{
		return new Player(name, matchesWon, matchesLost);
	}

	private String name;
	private int matchesWon;
	private int matchesLost;
	
	private Player(String name, int matchesWon, int matchesLost)
	{
		super();
		this.name = name;
		this.matchesWon = matchesWon;
		this.matchesLost = matchesLost;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getMatchesWon()
	{
		return matchesWon;
	}
	
	public void setMatchesWon(int matchesWon)
	{
		this.matchesWon = matchesWon;
	}
	
	public int getMatchesLost() 
	{
		return matchesLost;
	}
	
	public void setMatchesLost(int matchesLost)
	{
		this.matchesLost = matchesLost;
	}
}