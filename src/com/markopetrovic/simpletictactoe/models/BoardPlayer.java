package com.markopetrovic.simpletictactoe.models;

public class BoardPlayer
{
	//this is model class for board players
	//board player is Player with current wins and lost games
	
	public static BoardPlayer createBoardPlayer(Player player, int currentWins, int currentLoses)
	{
		return new BoardPlayer(player, currentWins, currentLoses);
	}

	private Player player;
	private int currentWins;
	private int currentLoses;
	
	private BoardPlayer(Player player, int currentWins, int currentLoses) 
	{
		super();
		this.player = player;
		this.currentWins = currentWins;
		this.currentLoses = currentLoses;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public int getCurrentWins() 
	{
		return currentWins;
	}

	public void setCurrentWins(int currentWins) 
	{
		this.currentWins = currentWins;
	}

	public int getCurrentLoses()
	{
		return currentLoses;
	}

	public void setCurrentLoses(int currentLoses) 
	{
		this.currentLoses = currentLoses;
	}
}
