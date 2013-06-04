package Datenstrukturen;

public class Triple {

	private int row;
	private int column;
	private double entry;
	
	/**
	 * @param row
	 * @param column
	 * @param entry
	 */
	public Triple(int row, int column, double entry) {
		super();
		this.row = row;
		this.column = column;
		this.entry = entry;
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

	public double getEntry() {
		return entry;
	}

	public void setEntry(double entry) {
		this.entry = entry;
	}
	
	
	
}
