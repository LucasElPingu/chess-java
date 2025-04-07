package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public class ChessAI {

	private static final int MAX_DEPTH = 3;

	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();

	// retorna uma lista com todas as peças de uma cor
	private List<Piece> getAllPiecesByColor(Color color) {
		List<Piece> blackPiece = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		return blackPiece;
	}

	// retorna a melhor posição
	public Position findBestMove(Board board, ChessMatch match) {
		int bestScore = Integer.MIN_VALUE;
		Position bestMove = null;
		// pega todas as peças pretas
		List<Piece> allBlack = getAllPiecesByColor(Color.BLACK);
		// percorre todas as peças pretas
		for (Piece piece : allBlack) {
			// pega todos os movimentos possiveis da piece
			boolean[][] possiblePieceMove = piece.possibleMoves();
			// percorre todo o tabuleiro testando as jogadas possiveis
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {

					if (possiblePieceMove[i][j]) {
						Position source = ((ChessPiece) piece).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = match.makeMoveAI(source, target);
						if (match.getJogadaValida()) {
							int score = minimax(match, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

							if (score > bestScore) {
								bestScore = score;
								bestMove = target;
							}
						}
						match.undoMoveAI(source, target, capturedPiece);
					}
				}
			}
		}
		return bestMove;
	}

	private int minimax(ChessMatch match, int depth, int alpha, int beta, boolean maximizingPlayer) {
		if (depth == 0 || match.getCheckMate() || match.isStalemate()) {
			return evaluateBoard(match);
		}

		Color currentColor = maximizingPlayer ? Color.BLACK : Color.WHITE;
		List<ChessMove> allMoves = getAllPossibleMoves(match, currentColor);

		if (maximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			for (ChessMove move : allMoves) {
				ChessPiece captured = match.performTestMove(move);
				int eval = minimax(match, depth - 1, alpha, beta, false);
				match.undoTestMove(move, captured);
				maxEval = Math.max(maxEval, eval);
				alpha = Math.max(alpha, eval);
				if (beta <= alpha)
					break;
			}
			return maxEval;
		} else {
			int minEval = Integer.MAX_VALUE;
			for (ChessMove move : allMoves) {
				ChessPiece captured = match.performTestMove(move);
				int eval = minimax(match, depth - 1, alpha, beta, true);
				match.undoTestMove(move, captured);
				minEval = Math.min(minEval, eval);
				beta = Math.min(beta, eval);
				if (beta <= alpha)
					break;
			}
			return minEval;
		}
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
		return score;
	}

	private int getPieceValue(ChessPiece piece) {
		switch (piece.toString().toUpperCase()) {
		case "P":
			return 10;
		case "N":
		case "B":
			return 30;
		case "R":
			return 50;
		case "Q":
			return 90;
		case "K":
			return 900;
		default:
			return 0;
		}
	}
	/*
	 * outro método possivel para pegar todos os movimentos de todas as peças pretas
	 * mas irei optar pelo outro msm, para manter a consistencia na logica do inicio
	 * do projeto
	 */
//	private List<ChessMove> getAllPossibleMoves(ChessMatch match, Color color) {
//		List<ChessMove> moves = new ArrayList<>();
//		for (int i = 0; i < 8; i++) {
//			for (int j = 0; j < 8; j++) {
//				Position pos = new Position(i, j);
//				ChessPiece piece = match.piece(pos);
//				if (piece != null && piece.getColor() == color) {
//					boolean[][] legalMoves = piece.possibleMoves();
//					for (int x = 0; x < 8; x++) {
//						for (int y = 0; y < 8; y++) {
//							if (legalMoves[x][y]) {
//								moves.add(new ChessMove(pos, new Position(x, y)));
//							}
//						}
//					}
//				}
//			}
//		}
//		return moves;
//	}
}
