package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private boolean check;

	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
	private List<ChessPiece> capturedPieces = new ArrayList<>();
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
	public boolean getCheck() {
		return check;
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
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			//se a peça for instancia de rei
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Não existe o rei da cor " + color + "no tabuleiro");
	}
	
	//método para receber as coordenadas do xadrez e a peça
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		//chamada o placepiece e passa a peça e instancia um nova posição no xadrez passando a coluna e a linha e convertendo para posição de matriz
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		//Quando colocar a peça no tabuleiro tem que colocar na lista de peças no tabuleiro
		piecesOnTheBoard.add(piece);
	}
	//testa se o rei esta em check percorrendo uma lista com todas as peças do oponente e verificando se essa peça pode se mover para a casa do rei
	private boolean testCheck(Color color) {
		//pega a posição do rei em formato de matriz
		Position kingPosition = king(color).getChessPosition().toPosition();
		//Cria uma lista com todas as peças do oponente
		List<Piece> opponentPiece = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color))
				.collect(Collectors.toList());
		//for para percorrer a lista de peças do oponente
		for(Piece p : opponentPiece) {
			//cria uma matriz de boolean que recebe uma matriz com todos os possiveis movimentos daquela peça
			boolean[][] mat = p.possibleMoves();
			//verifica se aquela peça pode se mover para a casa do rei
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true; 
			}
		}
		return false;
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
		//testa se o rei esta em check
		if (testCheck(currentPlayer)) {
			undoMove(s, t, capturedPiece);
			throw new ChessException("Posição inválida: o rei ficara em check");
		}
		//testa se o oponente esta em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		nextTurn();
		return (ChessPiece) capturedPiece; 
	}


	//Faz o movimento da peça removendo uma posivel peça na posição de destino
	private Piece makeMove(Position s, Position t) {
		Piece p = board.removePiece(s);
		Piece capPiece = board.removePiece(t);
		board.placePiece(p, t);
		if(capPiece != null) {
			piecesOnTheBoard.remove(capPiece);
			capturedPieces.add((ChessPiece)capPiece);
		}
		return capPiece;
	}
	//desfazer a jogada que errada que o jogador fez
	private void undoMove(Position source, Position target, Piece capPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		if(capPiece != null) {
			board.placePiece(capPiece, target);
			capturedPieces.remove(capPiece);
			piecesOnTheBoard.add((ChessPiece)capPiece);
		}
	}
	
	//validando se existe uma peça na origem
	private void validateSourcePosition(Position s) {
		if (!board.thereIsAPiece(s)) {
			throw new ChessException("Posição inválida: não existe peça na posição de origem");
		}
		//verifica se a peça escolhida e a cor do currentPlayer, caso não lança uma exceção
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
