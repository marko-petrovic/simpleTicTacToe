package com.markopetrovic.simpletictactoe.models;

import java.io.Serializable;

public class Player implements Serializable
{
	//this is a model class of our player
	//player has name and total score
	
	public static Player createPlayer(String name, int matchesWon, int matchesLost) 
	{
		return new Player(name, matchesWon, matchesLost);
	}

	private static final long serialVersionUID = 1L;
	private String name;
	private int totalGamesWon;
	private int totalGamesLost;
	
	private Player(String name, int matchesWon, int matchesLost)
	{
		super();
		this.name = name;
		this.totalGamesWon = matchesWon;
		this.totalGamesLost = matchesLost;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getTotalGamesWon()
	{
		return totalGamesWon;
	}
	
	public void setTotalGamesWon(int matchesWon)
	{
		this.totalGamesWon = matchesWon;
	}
	
	public int getTotalGamesLost() 
	{
		return totalGamesLost;
	}
	
	public void setTotalGamesLost(int matchesLost)
	{
		this.totalGamesLost = matchesLost;
	}
}