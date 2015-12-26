package com.markopetrovic.simpletictactoe.models;

public class BoardOponents 
{
	//this is a model container class of the two board players
	
	public static BoardOponents createBoardOponents(BoardPlayer xPlayer, BoardPlayer oPlayer) 
	{
		return new BoardOponents(xPlayer, oPlayer);
	}

	private BoardPlayer xPlayer;
	private BoardPlayer oPlayer;
	
	private BoardOponents(BoardPlayer xPlayer, BoardPlayer oPlayer)
	{
		super();
		this.xPlayer = xPlayer;
		this.oPlayer = oPlayer;
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
}
