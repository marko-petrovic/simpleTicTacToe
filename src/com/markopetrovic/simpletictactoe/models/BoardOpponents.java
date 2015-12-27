package com.markopetrovic.simpletictactoe.models;

public class BoardOpponents 
{
	//this is a model container class of the two board players
	
	public static BoardOpponents createBoardOpponents(BoardPlayer xPlayer, BoardPlayer oPlayer) 
	{
		return new BoardOpponents(xPlayer, oPlayer);
	}

	private BoardPlayer xPlayer;
	private BoardPlayer oPlayer;
	
	private BoardOpponents(BoardPlayer xPlayer, BoardPlayer oPlayer)
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
