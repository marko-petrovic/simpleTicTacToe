package com.markopetrovic.simpletictactoe.managers;

public class GameResultResolver 
{
	// we will use this class' methods to resolve if there was game result
	// result can be DRAW, XWINS, OWINS

	public enum StateEnum 
	{
		DRAW, XWINS, OWINS
	};

	public enum Check 
	{
		Row, Column, Diagonal, ReverseDiagonal
	}

	static StateEnum getIthColor(StateEnum[][] board, int index, int var, Check check) 
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

	static StateEnum getWinner(StateEnum[][] board, int fixed_index, Check check)
	{
		StateEnum color = getIthColor(board, fixed_index, 0, check);

		if (color == StateEnum.DRAW)
		{
			return StateEnum.DRAW;
		}

		for (int var = 1; var < board.length; var++) 
		{
			if (color != getIthColor(board, fixed_index, var, check))
			{
				return StateEnum.DRAW;
			}
		}

		return color;
	}

	public static StateEnum checkForGameResult(StateEnum[][] board)
	{
		int N = board.length;

		StateEnum winner = StateEnum.DRAW;

		// Check rows and columns
		for (int i = 0; i < N; i++) 
		{
			winner = getWinner(board, i, Check.Row);

			if (winner != StateEnum.DRAW)
			{
				return winner;
			}

			winner = getWinner(board, i, Check.Column);

			if (winner != StateEnum.DRAW)
			{
				return winner;
			}
		}

		winner = getWinner(board, -1, Check.Diagonal);

		if (winner != StateEnum.DRAW)
		{
			return winner;
		}

		// Check diagonal
		winner = getWinner(board, -1, Check.ReverseDiagonal);

		if (winner != StateEnum.DRAW)
		{
			return winner;
		}

		return StateEnum.DRAW;
	}
}
