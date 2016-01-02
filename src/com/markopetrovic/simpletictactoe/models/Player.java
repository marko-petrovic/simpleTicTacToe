package com.markopetrovic.simpletictactoe.models;

import java.io.Serializable;
import java.util.Comparator;

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
	
	//comparators for sorting lists of players by total games won and lost
	
	public static Comparator<Player> ComparatorTotalGamesWon = new Comparator<Player>() 
	{
		public int compare(Player p1, Player p2)
		{
		   int p1TotalGamesWon = p1.getTotalGamesWon();
		   int p2TotalGamesWon = p2.getTotalGamesWon();

		   return p2TotalGamesWon - p1TotalGamesWon;
	   }
	};
	
	public static Comparator<Player> ComparatorTotalGamesLost = new Comparator<Player>() 
	{
		public int compare(Player p1, Player p2)
		{
		   int p1TotalGamesLost = p1.getTotalGamesLost();
		   int p2TotalGamesLost = p2.getTotalGamesLost();

		   return p2TotalGamesLost - p1TotalGamesLost;
	   }
	};
}