package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

//Quem tem que saber a dimensão de um tabuleiro de xadrez e a classe ChessMatch
public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup(); //Para ser criado na hora que a partida for criada
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
	
	//responsavel por iniciar a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() { 
		board.placePiece(new Rook(board, Color.WHITE), new Position(7, 0));
		board.placePiece(new Rook(board, Color.WHITE), new Position(7, 7));
		board.placePiece(new Rook(board, Color.BLACK), new Position(0, 0));
		board.placePiece(new Rook(board, Color.BLACK), new Position(0, 7));
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
	}
}
