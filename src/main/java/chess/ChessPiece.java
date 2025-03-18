package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board); //repassa a chamda para o construtor da super classe
		this.color = color;
	}

	//A cor da peça não pode ser modificada
	public Color getColor() {
		return color;
	}
}
