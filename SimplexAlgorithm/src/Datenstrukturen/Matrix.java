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
//		Triple t = new Triple( rowNum , 0 , 0);
		ArrayList<Triple> l = new ArrayList<Triple>();
//		l.add(t);
		
		rows.add(l);
		
		rowNum++;
	}
	
	public void addColumn(){
		//Dummy- knoten der am anfang jeder Spalte steht 
		//und die zeilenzahl angibt
//		Triple t = new Triple(0, colNum , 0);
		ArrayList<Triple> l = new ArrayList<Triple>();
//		l.add(t);
		
		columns.add(l);
		
		colNum++;
	}
	
	/**
	 * Erstellt Tupel und fügt es der jeweiligen Spalte UND Zeile hinzu
	 * @param row
	 * @param col
	 * @param entry
	 */
	public void addEntry( int rowIndex,  int columnIndex , double entry){
		Triple t = new Triple(rowIndex , columnIndex, entry);
		
		rows.get(rowIndex).add(t);
		
		columns.get(columnIndex).add(t);
	}
	
	/**
	 * Rechtsseitige Vektormultiplikation
	 * @param vec
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector multiplyMatrixVektor( Vector vec) 
									throws IllegalArgumentException{
		if( vec.getSize() != colNum)//Dimensions-check
			throw new IllegalArgumentException("Matrixdimension mismatch");
		
		Vector result = new Vector();
		
		for(int  i=0 ; i< rowNum ; i++){//alle Zeilen der Matrix
			int sum = 0;
			int k = 0;//Laufindizes
			int n = 0;
			for(int j=0 ; j<vec.getLength() ; j++){//alle Eintraege des Vec
				Tupel<Integer,Double> tmp = vec.get(j);
				int t = tmp.getNum();
				//Check ob in der Matrix an Stelle t ein 
				//von 0 verschiedenes Element steht
				while ( rows.get(i).get(k).getColumn() < t){
					k++;
					if ( k == rows.get(i).size() ) break;
				}
				//falls in Zeile i der Matrix nur noch 0 steht --> Abbruch
				if ( k == rows.get(i).size() ) break;
				if( rows.get(i).get(k).getColumn() == t){
					sum += rows.get(i).get(k).getEntry()* tmp.getEntry();
				}
			}
			//Eintrag zum Vec hinzufuegen, falls != 0
			if( sum != 0 ) result.addEntry(i, sum);
				
		}
		
		return result;
	}
	
	public Vector multiplyVectorMatrix( Vector vec){
		
		return null;
	}
	
	
	
	
	
	
	
	public int getRowNum() {
		return rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public String toString(){
		String erg = "";
		for(int x = 0; x < rows.size(); x++){
//			erg = "";
			for(Triple t : rows.get(x)){
				erg = erg + " ; " + t.getEntry();
			}
			erg += "\n";
		}
		return erg;
	}
	
	
	public static void main(String[] args) {
		Matrix test = new Matrix();
		Vector vec = new Vector(2);
		for (int i=0 ; i<2 ;i++){
			test.addColumn();
			test.addRow();
		}
		test.addEntry(0, 1, 2);
		test.addEntry(1, 0, 1);
		test.addEntry(1, 1, 2);
		vec.addEntry(0, 2);
		vec.addEntry(1, 2);
		Vector res = test.multiplyMatrixVektor(vec);
		System.out.println(res);
		
		
		

		
	}

}
