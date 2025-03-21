package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

//Quem tem que saber a dimensão de um tabuleiro de xadrez e a classe ChessMatch
public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;

	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
	private List<ChessPiece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1; // inicia no 1 a partida e as brancas começam
		currentPlayer = Color.WHITE;
		initialSetup(); // Para ser criado na hora que a partida for criada

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

	public boolean getCheckMate() {
		return checkMate;
	}

	// retorna a matriz de peças da partida de xadrez
	public ChessPiece[][] getPieces() {
		ChessPiece[][] cp = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				cp[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return cp;
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	// cria uma lista de peças de uma cor que foi passada e pega a posição do rei
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			// se a peça for instancia de rei
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Não existe o rei da cor " + color + "no tabuleiro");
	}

	// método para receber as coordenadas do xadrez e a peça
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		// chamada o placepiece e passa a peça e instancia um nova posição no xadrez
		// passando a coluna e a linha e convertendo para posição de matriz
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		// Quando colocar a peça no tabuleiro tem que colocar na lista de peças no
		// tabuleiro
		piecesOnTheBoard.add(piece);
	}

	// testa se o rei esta em check percorrendo uma lista com todas as peças do
	// oponente e verificando se essa peça pode se mover para a casa do rei
	private boolean testCheck(Color color) {
		// pega a posição do rei em formato de matriz
		Position kingPosition = king(color).getChessPosition().toPosition();
		// Cria uma lista com todas as peças do oponente
		List<Piece> opponentPiece = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		// for para percorrer a lista de peças do oponente
		for (Piece p : opponentPiece) {
			// cria uma matriz de boolean que recebe uma matriz com todos os possiveis
			// movimentos daquela peça
			boolean[][] mat = p.possibleMoves();
			// verifica se aquela peça pode se mover para a casa do rei
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	// Lógica de checkmate
	private boolean testCheckMate(Color color) {
		// verifica se esta em check e retorna false caso não
		if (!testCheck(color)) {
			return false;
		}
		// cria uma lista com todas as peças no tabuleiro da cor do rei
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		// percorre todas as peças
		for (Piece p : list) {
			// Cria uma matriz com todas os movimentos possiveis daquela peça
			boolean[][] mat = p.possibleMoves();
			// percorre a matriz
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					// verifica se algum movimento daquela peça tira o rei de check
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capPiece = makeMove(source, target);
						// armazena o resultado de testecheck antes de desfazer o movimento para o teste
						// no if
						boolean test = testCheck(color);
						undoMove(source, target, capPiece);
						if (!test) {
							return false;
						}
					}
				}
			}
		}
		// se todos os ifs falharem e pq e checkmate
		return true;
	}

	// responsavel por iniciar a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		
		placeNewPiece('e', 1, new King(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}

	// Retorna uma matriz de boolean com os possíveis movimentos a partir da peça
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	// transforma de xadrez para matriz, valida a posição, movimenta a peça e
	// retorna a peça capturada
	public ChessPiece peformChessMove(ChessPosition source, ChessPosition target) {
		Position s = source.toPosition();
		Position t = target.toPosition();
		validateSourcePosition(s);
		validateTargetPosition(s, t);
		Piece capturedPiece = makeMove(s, t);
		// testa se o rei esta em check
		if (testCheck(currentPlayer)) {
			undoMove(s, t, capturedPiece);
			throw new ChessException("Posição inválida: o rei ficara em check");
		}
		// testa se o oponente esta em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		// se o oponente da peça que mexeu ficou em xeque-mate acabou o game
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		return (ChessPiece) capturedPiece;
	}

	// Faz o movimento da peça removendo uma posivel peça na posição de destino
	private Piece makeMove(Position s, Position t) {
		Piece p = board.removePiece(s);
		((ChessPiece) p).increaseMoveCount();
		Piece capPiece = board.removePiece(t);
		board.placePiece(p, t);
		if (capPiece != null) {
			piecesOnTheBoard.remove(capPiece);
			capturedPieces.add((ChessPiece) capPiece);
		}
		return capPiece;
	}

	// desfazer a jogada
	private void undoMove(Position source, Position target, Piece capPiece) {
		Piece p = board.removePiece(target);
		((ChessPiece) p).decreaseMoveCount();
		board.placePiece(p, source);
		if (capPiece != null) {
			board.placePiece(capPiece, target);
			capturedPieces.remove(capPiece);
			piecesOnTheBoard.add((ChessPiece) capPiece);
		}
	}

	// validando se existe uma peça na origem
	private void validateSourcePosition(Position s) {
		if (!board.thereIsAPiece(s)) {
			throw new ChessException("Posição inválida: não existe peça na posição de origem");
		}
		// verifica se a peça escolhida e a cor do currentPlayer, caso não lança uma
		// exceção
		if (currentPlayer != ((ChessPiece) board.piece(s)).getColor()) {
			throw new ChessException("Peça escolhida inválida: Escolha uma peça da cor " + currentPlayer);
		}
		if (!board.piece(s).isThereAnyPossibleMove()) {
			throw new ChessException("Posição inválida: não existe movimentos possíveis para a peça escolhida");
		}
	}

	// validando se e possivel mover a peça para o destino em relação a origem
	private void validateTargetPosition(Position s, Position t) {
		if (!board.piece(s).possibleMove(t)) {
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
		}
	}

	private void nextTurn() {
		turn++;
		// Expresão condicional ternaria
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
}
