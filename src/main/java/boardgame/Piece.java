package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}

	// A lógica de cada peça e diferente das possibilidades de movimento
	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		// HookMethods um método que faz um gancho com uma possível implementação de um
		// método concreto de uma subclasse
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean mat[][] = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				if (mat[i][j] == true) {
					return true;
				}
			}
		}
		return false;
	}
}
