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

	public static StateEnum checkForGameResult(StateEnum[][] board)
	{
		//get board dimension. in this demo app we will have n = 3
		int n = board.length;

		//assume that resulting state is DRAW
		StateEnum resultingState = StateEnum.DRAW;

		//check rows and columns
		for (int i = 0; i < n; i++) 
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
}
