package com.markopetrovic.simpletictactoe.managers;


public class GameResultResolver 
{
	// we will use this class' methods to resolve if there was game result
	// result can be DRAW, X, O

	public enum StateEnum 
	{
		DRAW, X, O
	};

	public enum Check 
	{
		Row, Column, Diagonal, ReverseDiagonal
	}

	//get state for each check
	static StateEnum getState(StateEnum[][] board, int index, int var, Check check) 
	{
		if (check == Check.Row)
		{
			return board[index][var];
		}
		else if (check == Check.Column)
		{
			return board[var][index];
		}
		else if (check == Check.Diagonal)
		{
			return board[var][var];
		}
		else if (check == Check.ReverseDiagonal)
		{
			return board[board.length - 1 - var][var];
		}

		return StateEnum.DRAW;
	}

	//this method is used in checkForGameResult(). It adds unnecessary runtime 
	//so we won't use it together with checkForGameResult()
	static StateEnum getResultingState(StateEnum[][] board, int fixedIndex, Check check)
	{
		StateEnum state = getState(board, fixedIndex, 0, check);

		if (state == StateEnum.DRAW)
		{
			return StateEnum.DRAW;
		}

		for (int i = 1; i < board.length; i++) 
		{
			if (state != getState(board, fixedIndex, i, check))
			{
				return StateEnum.DRAW;
			}
		}

		return state;
	}

	//checking game result by comparing every StateEnums in every row, column,
	//or diagonal. This is a bit more expensive operation than checking sums
	//therefore, we won't use this method but let's stay here as one of the solutions
	//of Tic Tac Toe board evaluation
	public static StateEnum checkForGameResult(StateEnum[][] board)
	{
		//assume that resulting state is DRAW
		StateEnum resultingState = StateEnum.DRAW;

		//check rows and columns
		for (int i = 0; i < TicTacToeManager.board.getBoardDimension(); i++) 
		{
			resultingState = getResultingState(board, i, Check.Row);

			if (resultingState != StateEnum.DRAW)
			{
				return resultingState;
			}

			resultingState = getResultingState(board, i, Check.Column);

			if (resultingState != StateEnum.DRAW)
			{
				return resultingState;
			}
		}

		//checking first diagonal
		resultingState = getResultingState(board, -1, Check.Diagonal);

		if (resultingState != StateEnum.DRAW)
		{
			return resultingState;
		}

		//checking second diagonal
		resultingState = getResultingState(board, -1, Check.ReverseDiagonal);

		if (resultingState != StateEnum.DRAW)
		{
			return resultingState;
		}
		
		//if there was no return so far, which would be anything but not DRAW, return DRAW
		return StateEnum.DRAW;
	}
	
	//this method has O(N) runtime complexity as 
	//it checks only sums of all rows, columns and diagonals
	//idea is that any row, column or diagonal that has sum equals to N returns X as winner,
	//and if there's one that returns -N then method returns O as winner
	//as this method is called each time after one of the players makes a move, we can't have
	//cases where we would have both -N and N sums
	public static StateEnum checkForGameResultUsingSums()
	{
		//assume that resulting state is DRAW
		StateEnum resultingState = StateEnum.DRAW;
		
		for (int i = 0; i < TicTacToeManager.board.getBoardDimension(); i++) 
		{
			//check row
			if (TicTacToeManager.board.getRowValue()[i] == TicTacToeManager.board.getBoardDimension()) 
			{
				resultingState = StateEnum.X;
				return resultingState;
			}
			else if (TicTacToeManager.board.getRowValue()[i] == -TicTacToeManager.board.getBoardDimension()) 
			{
				resultingState = StateEnum.O;
				return resultingState;
			}
			
			//check column
			if (TicTacToeManager.board.getColumnValue()[i] == TicTacToeManager.board.getBoardDimension()) 
			{
				resultingState = StateEnum.X;
				return resultingState;
			}
			else if (TicTacToeManager.board.getColumnValue()[i] == -TicTacToeManager.board.getBoardDimension()) 
			{
				resultingState = StateEnum.O;
				return resultingState;
			}
		}
		
		//check both diagonals
		for (int i = 0; i < TicTacToeManager.board.getBoardDimension()-1; i++) 
		{
			if (TicTacToeManager.board.getDiagonalValue()[i] == TicTacToeManager.board.getBoardDimension())
			{
				resultingState = StateEnum.X;
				return resultingState;
			}
			else if (TicTacToeManager.board.getDiagonalValue()[i] == -TicTacToeManager.board.getBoardDimension())
			{
				resultingState = StateEnum.O;
				return resultingState;
			}
		}
		
		return resultingState;
	}
}