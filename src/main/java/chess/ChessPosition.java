package chess;

import boardgame.Position;

public class ChessPosition {

	public Character column;
	public Integer row;

	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error: posição inválida \n posições válidas de a1 a h8");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	// do xadrez para a matriz
	protected Position toPosition() {
		if(column == null || row == null) {
			throw new NullPointerException("Valores de coluna ou linha nulos");
		}
		return new Position((8 - row), (column - 'a'));
	}

	// da matriz para o xadrez sublinhada no UML=static
	protected static ChessPosition fromPosition(Position position) {
	    // Verificação de nulidade
	    if (position == null) {
	        throw new NullPointerException("Erro em ChessPosition.fromPosition: O parâmetro 'position' é nulo!");
	    }
		// necessário fazer o casting para o char
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());

	}

	@Override
	public String toString() {
		return "" + column + row; // precisa dos espaço no inicio para o compilador entender que e uma
									// concatenação de strings
	}

}
