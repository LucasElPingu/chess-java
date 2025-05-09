package boardgame;

public class Position {

	private int row;
	private int column;

	public Position() {
	}

	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setValue(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("row = ");
		builder.append(row);
		builder.append(", column = ");
		builder.append(column);
		return builder.toString();
	}
}
