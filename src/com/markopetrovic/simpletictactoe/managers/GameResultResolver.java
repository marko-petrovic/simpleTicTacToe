package com.markopetrovic.simpletictactoe.managers;

public class GameResultResolver 
{
	// we will use this class' methods to resolve if there was game result
	// result can be DRAW, XWINS, OWINS

	public enum Piece 
	{
		DRAW, XWINS, OWINS
	};

	public enum Check 
	{
		Row, Column, Diagonal, ReverseDiagonal
	}

	static Piece getIthColor(Piece[][] board, int index, int var, Check check) 
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

		return Piece.DRAW;
	}

	static Piece getWinner(Piece[][] board, int fixed_index, Check check)
	{
		Piece color = getIthColor(board, fixed_index, 0, check);

		if (color == Piece.DRAW)
		{
			return Piece.DRAW;
		}

		for (int var = 1; var < board.length; var++) 
		{
			if (color != getIthColor(board, fixed_index, var, check))
			{
				return Piece.DRAW;
			}
		}

		return color;
	}

	public static Piece hasWon(Piece[][] board)
	{
		int N = board.length;

		Piece winner = Piece.DRAW;

		// Check rows and columns
		for (int i = 0; i < N; i++) 
		{
			winner = getWinner(board, i, Check.Row);

			if (winner != Piece.DRAW)
			{
				return winner;
			}

			winner = getWinner(board, i, Check.Column);

			if (winner != Piece.DRAW)
			{
				return winner;
			}
		}

		winner = getWinner(board, -1, Check.Diagonal);

		if (winner != Piece.DRAW)
		{
			return winner;
		}

		// Check diagonal
		winner = getWinner(board, -1, Check.ReverseDiagonal);

		if (winner != Piece.DRAW)
		{
			return winner;
		}

		return Piece.DRAW;
	}
}
