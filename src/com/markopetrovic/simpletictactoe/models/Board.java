package com.markopetrovic.simpletictactoe.models;

import com.markopetrovic.simpletictactoe.managers.GameResultResolver.StateEnum;

public class Board 
{
	//this is a model class for Board
	//it has StateEnum[][] 2d array that represents board states
	//it also has int[] arrays that represent sums of rows, columns and diagonals
	//idea is that X player adds 1 to row/column/diagonal and O player adds -1
	//evaluation of a board will be based on checking if one of these three is
	//either N either -N by its total value
	
	private StateEnum[][] board;
	private int[] rowValue;
	private int[] columnValue;
	private int[] diagonalValue;
	private int centralPosition;
	private int boardDimension;

	public static Board createBoard
	(StateEnum[][] board, int[] rowValue, int[] columnValue, int[] diagonalValue, int boardDimension)
	{
		//in this factory constructor we will set initial board
		//and set initial values of sums of rows, columns and diagonals
		for (int i = 0; i < boardDimension; i++) 
		{
			for (int j = 0; j < boardDimension; j++) 
			{
				board[i][j] = StateEnum.DRAW;
			}
		}
		
		//we can do both rows and columns here cos their dimension is always the same
		for (int i = 0; i < rowValue.length; i++) 
		{
			rowValue[i] = 0;
			columnValue[i] = 0;
		}
		
		//do the same for diagonals
		for (int i = 0; i < diagonalValue.length; i++) 
		{
			diagonalValue[i] = 0;
		}
		
		//now return new Board
		return new Board(board, rowValue, columnValue, diagonalValue, boardDimension);
	}
	
	private Board(StateEnum[][] board, int[] rowValue, int[] columnValue, int[] diagonalValue, int boardDimension)
	{
		super();
		this.board = board;
		this.rowValue = rowValue;
		this.columnValue = columnValue;
		this.diagonalValue = diagonalValue;
		this.centralPosition = (boardDimension - 1) / 2;
		this.boardDimension = boardDimension;
	}
	
	public StateEnum[][] getBoard() 
	{
		return board;
	}
	
	public void setBoard(StateEnum[][] board) 
	{
		this.board = board;
	}
	
	public int[] getRowValue()
	{
		return rowValue;
	}
	
	public void setRowValue(int[] rowValue) 
	{
		this.rowValue = rowValue;
	}
	
	public int[] getColumnValue() 
	{
		return columnValue;
	}
	
	public void setColumnValue(int[] columnValue) 
	{
		this.columnValue = columnValue;
	}
	
	public int[] getDiagonalValue() 
	{
		return diagonalValue;
	}
	
	public void setDiagonalValue(int[] diagonalValue) 
	{
		this.diagonalValue = diagonalValue;
	}

	public int getCentralPosition() 
	{
		return centralPosition;
	}

	public void setCentralPosition(int centralPosition) 
	{
		this.centralPosition = centralPosition;
	}

	public int getBoardDimension() 
	{
		return boardDimension;
	}

	public void setBoardDimension(int boardDimension) 
	{
		this.boardDimension = boardDimension;
	}
}