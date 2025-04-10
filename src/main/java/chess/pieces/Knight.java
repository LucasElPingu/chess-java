package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	// Retorna se o cavalo pode mover dada uma determinada posição
	private boolean canMove(Position position) {
		// Armazena a peça na posição passada em p e retorna true se a peça for
		// igual a nulo ou a cor for diferente
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		try {
			p.setValue(position.getRow() - 1, position.getColumn() - 2);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() - 2, position.getColumn() - 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() - 2, position.getColumn() + 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() - 1, position.getColumn() + 2);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() + 1, position.getColumn() + 2);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() + 2, position.getColumn() + 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() + 2, position.getColumn() - 1);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() + 1, position.getColumn() - 2);
			if (getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return mat;
	}

	@Override
	public String toString() {
		return "C";
	}

}
