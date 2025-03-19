package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) {
			throw new BoardException("Erroa ao criar um tabuleiro: número de linhas e colunas devem ser maior que 1");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if(!positionExists(new Position(row, column))) {
			throw new BoardException("Erro: Posição inexistente");
		}
		return pieces[row][column];
	}
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Erro: Posição inexistente");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position positon) { //responsavel por mover a peça inicialmente
		if(thereIsAPiece(positon)) {
			throw new BoardException("Erro: Posição inválida, já possui uma peça na posição " + positon);
		}
		pieces[positon.getRow()][positon.getColumn()] = piece; //atribui uma nova posição a peça
		piece.position = positon; //Tira da posição anterior
	}

	public boolean positionExists(Position position) {
		//Verifica se a linha e colunas passadas existem
		return position.getRow() >= 0 && position.getRow() < rows && position.getColumn() >= 0 && position.getColumn() < columns; 
	}

	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Erro: Posição inexistente");
		}
		//return pieces[position.getRow()][position.getColumn()] != null; outro método 
		return piece(position) != null;
	}
	//método para remove a peça
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Erro: Posição inexistente");
		}
		if(piece(position) == null)
			return null;
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
}
