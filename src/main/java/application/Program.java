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

		while (true) {
			try {
				UI.clearScreen();
				UI.printMatch(cm, captured);
				System.out.println("\nSource: ");
				ChessPosition source = UI.readChessPosition(sc);
				//vai armazenar os possiveis movimentos para serem passados como argumento no printBoard
				boolean[][] possibleMoves = cm.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(cm.getPieces(), possibleMoves);
				
				System.out.println("\nTarget: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				//Se não tiver nenhuma peça na posição de destino ele retorna null
				ChessPiece capPiece = cm.peformChessMove(source, target);
				if(capPiece != null) {
					captured.add(capPiece);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

		}

	}

}
