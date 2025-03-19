package chess;

import boardgame.Position;

public class ChessPosition {

	public char column;
	public int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {
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
	
	//do xadrez para a matriz
	protected Position toPosition() {
		return new Position((8 - row), (column - 'a'));
	}
	
	//da matriz para o xadrez sublinhada no UML=static
	protected static ChessPosition fromPosition(Position position) {
		//necessário fazer o casting para o char
		return new ChessPosition((char)('a' - position.getColumn()) , position.getRow() - 8);
		
	}

	@Override
	public String toString() {
		return "" + column + row; //precisa dos espaço no inicio para o compilador entender que e uma concatenação de strings
	}
	
	
}
