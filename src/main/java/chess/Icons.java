package chess;

public class Icons {
	public static final String WHITE_KING = "\u2654"; // ♔
	public static final String WHITE_QUEEN = "\u2655"; // ♕
	public static final String WHITE_ROOK = "\u2656"; // ♖
	public static final String WHITE_BISHOP = "\u2657"; // ♗
	public static final String WHITE_KNIGHT = "\u2658"; // ♘
	public static final String WHITE_PAWN = "\u2659"; // ♙

	public static final String BLACK_KING = "\u265A"; // ♚
	public static final String BLACK_QUEEN = "\u265B"; // ♛
	public static final String BLACK_ROOK = "\u265C"; // ♜
	public static final String BLACK_BISHOP = "\u265D"; // ♝
	public static final String BLACK_KNIGHT = "\u265E"; // ♞
	public static final String BLACK_PAWN = "\u265F"; // ♟

	public static final String YELLOW = "\u001B[33m"; // Cor amarela (para peças pretas)
	public static final String WHITE = "\u001B[37m"; // Cor branca
	public static final String RESET = "\u001B[0m"; // Reseta cor

	private Icons() {
		// Construtor privado para evitar instanciação
	}
}
