package com.markopetrovic.simpletictactoe.models;

public class BoardOpponents 
{
	//this is a model container class of the two board players, who plays and counting moves
	
	public static BoardOpponents createBoardOpponents(BoardPlayer xPlayer, BoardPlayer oPlayer) 
	{
		return new BoardOpponents(xPlayer, oPlayer);
	}

	private BoardPlayer xPlayer;
	private BoardPlayer oPlayer;
	//this property is added into BoardOpponents model so we don't have to control move state outside
	//of needed BoardOpponents model. If we use this model instance to get results of a match and
	//of all games played, then we can put move state switch here as well. We don't have to explicitly
	//construct a value for this property if we assume that X plays first (it's demo app after all)
	//Boolean is used instead of boolean because we might want to use third state too (true, false and null)
	private Boolean xPlays = true;
	//we need counter so if counter gets == n^2 we stop with playing and decide winner state (DRAW, or x or O)
	private int counter = 0;
	private boolean previousPlayerWasX = true;
	
	private BoardOpponents(BoardPlayer xPlayer, BoardPlayer oPlayer)
	{
		super();
		this.xPlayer = xPlayer;
		this.oPlayer = oPlayer;
		this.xPlays = true;
		this.counter = 0;
		this.previousPlayerWasX = true;
	}
	
	public BoardPlayer getxPlayer() 
	{
		return xPlayer;
	}
	
	public void setxPlayer(BoardPlayer xPlayer) 
	{
		this.xPlayer = xPlayer;
	}
	
	public BoardPlayer getoPlayer()
	{
		return oPlayer;
	}
	
	public void setoPlayer(BoardPlayer oPlayer) 
	{
		this.oPlayer = oPlayer;
	}

	public Boolean getxPlays() 
	{
		return xPlays;
	}

	public void setxPlays(Boolean xPlays)
	{
		this.xPlays = xPlays;
	}

	public int getCounter()
	{
		return counter;
	}

	public void setCounter(int counter) 
	{
		this.counter = counter;
	}

	public boolean isPreviousPlayerWasX() 
	{
		return previousPlayerWasX;
	}

	public void setPreviousPlayerWasX(boolean previousPlayerWasX)
	{
		this.previousPlayerWasX = previousPlayerWasX;
	}

}
