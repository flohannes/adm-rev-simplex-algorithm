package Datenstrukturen;

import java.util.ArrayList;

public class Matrix {

	private ArrayList<ArrayList<Triple>> rows;
	
	private ArrayList<ArrayList<Triple>> columns;
	
	private int rowNum;
	private int colNum;
	
	/**
	 * Initialize empty Matrix
	 */
	public Matrix(){
		rows = new ArrayList<ArrayList<Triple>>();
		columns = new ArrayList<ArrayList<Triple>>();
		rowNum = colNum = 0;
	}

	public void addRow(){
		//Dummy- knoten der am anfang jeder Zeile steht 
		//und die zeilenzahl angibt
		Triple t = new Triple( rowNum , 0 , 0);
		ArrayList<Triple> l = new ArrayList<Triple>();
		l.add(t);
		
		rows.add(l);
		
		rowNum++;
	}
	
	public void addColumn(){
		//Dummy- knoten der am anfang jeder Spalte steht 
		//und die zeilenzahl angibt
		Triple t = new Triple( 0 , colNum , 0);
		ArrayList<Triple> l = new ArrayList<Triple>();
		l.add(t);
		
		columns.add(l);
		
		colNum++;
	}
	
	/**
	 * Erstellt Triple und fügt es der jeweiligen Spalte UND Zeile hinzu
	 * @param row
	 * @param col
	 * @param entry
	 */
	public void addEntry( int row , int col , double entry){
		Triple t = new Triple( row , col , entry);
		
		rows.get(row).add(t);
		
		columns.get(col).add(t);
	}
	
	
	
	
	
	
	public int getRowNum() {
		return rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	
	

}
