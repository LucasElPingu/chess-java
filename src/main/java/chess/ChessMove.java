package chess;

import boardgame.Position;

public class ChessMove {

	private Position source;
    private Position target;

    public ChessMove(Position source, Position target) {
        this.source = source;
        this.target = target;
    }

    public Position getSource() {
        return source;
    }

    public Position getTarget() {
        return target;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChessMove [source=");
		builder.append(source);
		builder.append(", target=");
		builder.append(target);
		builder.append("]");
		return builder.toString();
	}
    
    
}
