package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

//Quem tem que saber a dimensão de um tabuleiro de xadrez e a classe ChessMatch
public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1; //inicia no 1 a partida e as brancas começam
		currentPlayer = Color.WHITE;
		initialSetup(); //Para ser criado na hora que a partida for criada
		
	}

	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	//retorna a matriz de peças da partida de xadrez
	public ChessPiece[][] getPieces(){
		ChessPiece[][] cp = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				cp[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return cp;
	}
	//método para receber as coordenadas do xadrez e a peça
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		//chamada o placepiece e passa a peça e instancia um nova posição no xadrez passando a coluna e a linha e convertendo para posição de matriz
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	//responsavel por iniciar a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() { 
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1,new King(board, Color.WHITE));

		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('a', 8,new Rook(board, Color.BLACK));
	}
	//Retorna uma matriz de boolean com os possíveis movimentos a partir da peça
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	//transforma de xadrez para matriz, valida a posição, movimenta a peça e retorna a peça capturada
	public ChessPiece peformChessMove(ChessPosition source, ChessPosition target) {
		Position s = source.toPosition();
		Position t = target.toPosition();
		validateSourcePosition(s);
		validateTargetPosition(s, t);
		Piece capturedPiece = makeMove(s, t);
		nextTurn();
		return (ChessPiece) capturedPiece; 
	}


	//Faz o movimento da peça removendo uma posivel peça na posição de destino
	private Piece makeMove(Position s, Position t) {
		Piece p = board.removePiece(s);
		Piece capPiece = board.removePiece(t);
		board.placePiece(p, t);
		return capPiece;
	}
	//validando se existe uma peça na origem
	private void validateSourcePosition(Position s) {
		if (!board.thereIsAPiece(s)) {
			throw new ChessException("Posição inválida: não existe peça na posição de origem");
		}
		if(currentPlayer != ((ChessPiece)board.piece(s)).getColor()) {
			throw new ChessException("Peça escolhida inválida: Escolha uma peça da cor " + currentPlayer);
		}
		if(!board.piece(s).isThereAnyPossibleMove()) {
			throw new ChessException("Posição inválida: não existe movimentos possíveis para a peça escolhida");
		}
	}
	//validando se e possivel mover a peça para o destino em relação a origem
	private void validateTargetPosition(Position s, Position t) {
		if(!board.piece(s).possibleMove(t)) {
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	private void nextTurn() {
		turn++;
		//Expresão condicional ternaria
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
}
