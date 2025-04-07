package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch cm = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		// enquanto n der cheque-mate a partida continua
		while (!cm.getCheckMate() && !cm.getStalemate()) {
			try {
				UI.clearScreen();
				UI.printMatch(cm, captured);
				System.out.println("\nSource: ");
				ChessPosition source = UI.readChessPosition(sc);

				// vai armazenar os possiveis movimentos para serem passados como argumento no
				// printBoard
				boolean[][] possibleMoves = cm.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(cm.getPieces(), possibleMoves);
				System.out.println("\nTarget: ");
				ChessPosition target = UI.readChessPosition(sc);

				// Se não tiver nenhuma peça na posição de destino ele retorna null
				ChessPiece capPiece = cm.peformChessMove(source, target);
				if (capPiece != null) {
					captured.add(capPiece);
				}

				if (cm.getPromoted() != null) {
					System.out.print("Digite para qual peça deseja promover (T/C/B/Q): ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
						System.out.print("\033[F\033[K"); // Move o cursor para cima e limpa a linha
						System.out.print("\033[F\033[K"); // Limpa a linha anterior também (caso precise)
						System.out.println("Valor Inválido");
						sc.nextLine();

						System.out.print("\033[F\033[K");
						System.out.print("\033[F\033[K");
						System.out.print("Digite para qual peça deseja promover (T/C/B/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					cm.replacePromotedPiece(type);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(cm, captured);

	}

}
