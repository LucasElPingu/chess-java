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

public class ChessAI {

	private static final int MAX_DEPTH = 3;
	
	

	// retorna uma lista com todas as peças de uma cor
	private List<ChessPiece> getAllPiecesByColor(Color color, ChessMatch match) {
		List<ChessPiece> pieces = match.getPiecesOnTheBoard().stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		return pieces;
	}

	// retorna a melhor posição
	public Position findBestMove(Board board, ChessMatch match) {
		int bestScore = Integer.MIN_VALUE;
		Position bestMove = null;
		// pega todas as peças pretas
		List<ChessPiece> allBlack = getAllPiecesByColor(Color.BLACK, match);
		// percorre todas as peças pretas
		for (ChessPiece piece : allBlack) {
			// pega todos os movimentos possiveis da piece
			System.err.println("Peça: " + piece + " Posição: " + ((ChessPiece)piece).getChessPosition().toPosition());
			boolean[][] possiblePieceMove = piece.possibleMoves();
			
			// percorre todo o tabuleiro testando as jogadas possiveis
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {

					Position source = ((ChessPiece) piece).getChessPosition().toPosition();
					Position target = new Position(i, j);
					if (possiblePieceMove[i][j]) {
						match.makeMoveAI(source, target, Color.BLACK);
					}
					if (match.getJogadaValida()) {
						int score = minimax(match, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, board);

						if (score > bestScore) {
							bestScore = score;
							bestMove = target;
						}
					}

				}
			}
		}
		return bestMove;
	}

	private int minimax(ChessMatch match, int depth, int alpha, int beta, boolean maximizingPlayer, Board board) {
		if (depth == 0 || match.getCheckMate() || match.getStalemate()) {
			return evaluateBoard(match);
		}

		Color currentColor = maximizingPlayer ? Color.BLACK : Color.WHITE;
		List<ChessMove> allMoves = getAllPossibleMoves(match, currentColor, board);

		int bestEval = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		for (ChessMove move : allMoves) {
			Position source = move.getSource();
			Position target = move.getTarget();
			if (isValidSource(source, currentColor, board)) {
				match.makeMoveAI(source, target, currentColor);

				if (match.getJogadaValida()) {
					int eval = minimax(match, depth - 1, alpha, beta, !maximizingPlayer, board);
					bestEval = maximizingPlayer ? Math.max(bestEval, eval) : Math.min(bestEval, eval);
					alpha = maximizingPlayer ? Math.max(alpha, eval) : alpha;
					beta = !maximizingPlayer ? Math.min(beta, eval) : beta;
				}
			}
			if (beta <= alpha)
				break;
		}

		return bestEval;
	}

	private int evaluateBoard(ChessMatch match) {
		int score = 0;
		for (ChessPiece[] row : match.getPieces()) {
			for (ChessPiece piece : row) {
				if (piece != null) {
					int value = getPieceValue(piece);
					score += (piece.getColor() == Color.BLACK) ? value : -value;
				}
			}
		}
		match.undoMoveAI();
		return score;
	}

	private int getPieceValue(ChessPiece piece) {
		if (piece instanceof Pawn)
			return 100;
		if (piece instanceof Knight)
			return 320;
		if (piece instanceof Bishop)
			return 330;
		if (piece instanceof Rook)
			return 500;
		if (piece instanceof Queen)
			return 900;
		if (piece instanceof King)
			return 0;
		/*
		 * ajuda o Minimax a focar nas peças móveis e capturáveis, o que é mais
		 * realista, já que o rei não será trocado. Pode por valor alto
		 */

		return 0;
	}

	private boolean isValidSource(Position pos, Color color, Board board) {
		ChessPiece piece = (ChessPiece) board.piece(pos);
		return piece != null && piece.getColor() == color;
	}

	private List<ChessMove> getAllPossibleMoves(ChessMatch match, Color color, Board board) {
		List<ChessMove> moves = new ArrayList<>();
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				Position pos = new Position(i, j);
				ChessPiece piece = (ChessPiece) board.piece(pos);
				if (piece != null && piece.getColor() == color) {
					boolean[][] legalMoves = piece.possibleMoves();
					for (int x = 0; x < board.getRows(); x++) {
						for (int y = 0; y < board.getColumns(); y++) {
							if (legalMoves[x][y]) {
								moves.add(new ChessMove(pos, new Position(x, y)));
							}
						}
					}
				}
			}
		}
		return moves;
	}
}
