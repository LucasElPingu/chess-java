package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	// cores
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	// cores de fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// método para ler uma posição usuário quando ele mover a peça
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine().toLowerCase();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler a posição no xadrez. Valores válidos de a1 até h8");
		}
	}

	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		printCapturedPieces(captured, chessMatch);
		Scanner await = new Scanner(System.in);
		System.out.println("\nTurn: " + chessMatch.getTurn());
		// checa se não foi cheque-mate, se for true, entra no if
		if (chessMatch.getCheckMate()) {
			System.out.println("CHECKMATE");
			System.out.println("VENCENDOR: " + chessMatch.getCurrentPlayer());
			await.nextLine();
		} else if (chessMatch.getStalemate()) {
			System.out.println("EMPATE!!!");
			await.nextLine();
		} else { // se for false, imprime na tela
			System.out.println("Esperando o player: " + chessMatch.getCurrentPlayer());
		}
			// chama toda a lógica de check pelo getCheck
			if (chessMatch.getCheck()) {
				System.out.println("CHECK!");
			}


	}

	public static void printBoard(ChessPiece[][] piece) {
		for (int i = 0; i < piece.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < piece[i].length; j++) {
				printPieces(piece[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printBoard(ChessPiece[][] piece, boolean[][] possibleMoves) {
		for (int i = 0; i < piece.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < piece[i].length; j++) {
				printPieces(piece[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	// quando for true o backgroud ele vai ser pintado
	public static void printPieces(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_RED_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private static void printCapturedPieces(List<ChessPiece> captured, ChessMatch chessMatch) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE)
				.collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK)
				.collect(Collectors.toList());
		System.out.println("\nPeças capturadas: ");
		if (chessMatch.getCurrentPlayer() == Color.BLACK)
			// A lista armazena as peças brancas capturadas
			System.out.println(
					"Brancas: [" + white.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]");
		else
			System.out.println(
					"Pretas: [" + black.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]");
	}
}
