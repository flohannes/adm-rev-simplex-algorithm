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
	public void addEntry( int rowIndex,  int columnIndex , double entry)
													throws IllegalArgumentException{
		if( rowIndex > rowNum-1 || columnIndex > colNum-1)
			throw new IllegalArgumentException("Index out of Bounds");
		
		Triple t = new Triple(rowIndex , columnIndex, entry);
		
		if( rows.get(rowIndex).isEmpty())
			rows.get(rowIndex).add(t);
		else if( rows.get(rowIndex).get(rows.get(rowIndex).size() - 1).getColumn() < columnIndex)
			rows.get(rowIndex).add(t);
		else{
			int j;
			for( j=0 ; j<rows.get(rowIndex).size() ; j++){
				if(rows.get(rowIndex).get(j).getColumn() == columnIndex)
					throw new IllegalArgumentException("Element existiert bereits!!!");
				if(rows.get(rowIndex).get(j).getColumn()> columnIndex){
					rows.get(rowIndex).add( j , t);
					break;
				}
			}
			if(j == rows.get(rowIndex).size())
				rows.get(rowIndex).add(t);
		}
		
		if( columns.get(columnIndex).isEmpty())
			columns.get(columnIndex).add(t);
		else if( columns.get(columnIndex).get(columns.get(columnIndex).size() - 1).getRow() < rowIndex)
			columns.get(columnIndex).add(t);
		else{
			int i;
			for( i=0; i<columns.get(columnIndex).size() ; i++){
				if(columns.get(columnIndex).get(i).getRow()== rowIndex)
					throw new IllegalArgumentException("Element existiert bereits!!!");
				if(columns.get(columnIndex).get(i).getRow()> rowIndex){
					columns.get(columnIndex).add( i , t);
					break;
				}
			}
			if( i == columns.get(columnIndex).size())
				columns.get(columnIndex).add(t);
		}
		
	}
	
	public Vector multiplyMatrixMatrixColumn(Matrix m, int index){
//		if( vec.getLength() != colNum)//Dimensions-check
//			throw new IllegalArgumentException("Matrixdimension mismatch");
		
		Vector vec = m.getMatrixColumn(index);
		return multiplyMatrixVektor(vec);
		
	
	}
	
	public double multiplyVectorMatrixColumn(Vector vec, int columnIndex){
		int k=0;
		double sum = 0;

		for( Triple t : columns.get(columnIndex))
			sum += t.getEntry()*vec.get(t.getRow());
		
		return sum;
		
//		for(int j=0 ; j<vec.getLength() ; j++){//alle Eintraege des Vec
//			Tupel<Integer,Double> tmp = vec.get(j);
//			int t = tmp.getNum();
//			//Check ob in der Matrix an Stelle t ein 
//			//von 0 verschiedenes Element steht
//			while ( columns.get(columnIndex).get(k).getRow() < t){
//				k++;
//				if ( k == columns.get(columnIndex).size() ) break;
//			}
//			//falls in Zeile i der Matrix nur noch 0 steht --> Abbruch
//			if ( k == columns.get(columnIndex).size() ) break;
//			if( columns.get(columnIndex).get(k).getRow() == t){
//				sum += columns.get(columnIndex).get(k).getEntry()* tmp.getEntry();
//			}
//		}
//		return sum;
	}
	
	/**
	 * Rechtsseitige Vektormultiplikation
	 * @param vec
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector multiplyMatrixVektor( Vector vec) 
									throws IllegalArgumentException{
		if( vec.getLength() != colNum)//Dimensions-check
			throw new IllegalArgumentException("Matrixdimension mismatch");
		
		double[] result = new double[rowNum];
//		Vector result = new Vector();
		
		for( int k=0 ; k<rowNum ; k++){
			double sum = 0;
			for(Triple trip : rows.get(k)){
				int colInd = trip.getColumn();
				sum += trip.getEntry()*vec.get(colInd);
			}
			result[k] = sum;
		}
		
		return new Vector(result);

		
		
		
		
//		Vector result = new Vector();
//		
//		for(int  i=0 ; i< rowNum ; i++){//alle Zeilen der Matrix
//			int sum = 0;
//			int k = 0;//Laufindizes
//			int n = 0;
//			for(int j=0 ; j<vec.getLength() ; j++){//alle Eintraege des Vec
//				Tupel<Integer,Double> tmp = vec.get(j);
//				int t = tmp.getNum();
//				//Check ob in der Matrix an Stelle t ein 
//				//von 0 verschiedenes Element steht
//				while ( rows.get(i).get(k).getColumn() < t){
//					k++;
//					if ( k == rows.get(i).size() ) break;
//				}
//				//falls in Zeile i der Matrix nur noch 0 steht --> Abbruch
//				if ( k == rows.get(i).size() ) break;
//				if( rows.get(i).get(k).getColumn() == t){
//					sum += rows.get(i).get(k).getEntry()* tmp.getEntry();
//				}
//			}
//			//Eintrag zum Vec hinzufuegen, falls != 0
//			if( sum != 0 ) result.addEntry(i, sum);
//				
//		}
//		
//		return result;
	}
	
	/**
	 * Linksseitige Vektormultiplikation
	 * @param vec
	 * @return
	 * @throws IllegalArgumentException
	 */
//	public Vector multiplyCbMatrix( Vector vec, int[] basis){
////		if( vec.getSize() != rowNum)//Dimensions-check
////			throw new IllegalArgumentException("Dimension mismatch");
//		
//		Vector result = new Vector();
//		
//		loop1: for(int  i=0 ; i< colNum ; i++){//alle Spalten der Matrix
//			int sum = 0;
//			int k = 0;//Laufindizes
////			int n = 0;
//			loop2: for(int j=0 ; j<vec.getLength() ; j++){//alle Eintraege des Vec
//				Tupel<Integer,Double> tmp = vec.get(j);
//				int t = tmp.getNum();
//				for(int b = 0; b<basis.length;b++){
//					if(t == basis[b]){
//						
//						//Check ob in der Matrix an Stelle t ein 
//						//von 0 verschiedenes Element steht
//						while ( columns.get(i).get(k).getRow() < t){
//							k++;
//							if ( k == columns.get(i).size() ) break;
//						}
//						//falls in Zeile i der Matrix nur noch 0 steht --> Abbruch
//						if ( k == columns.get(i).size() ) break loop2;
//						if( columns.get(i).get(k).getRow() == t){
//							System.out.println(columns.get(i).get(k).getEntry() +" mal "+ tmp.getEntry());
//							sum += columns.get(i).get(k).getEntry()* tmp.getEntry();
//							System.out.println(sum);
//
//						}
////						break;
//					}
//				}
//			}
//			//Eintrag zum Vec hinzufuegen, falls != 0
//			
//			if( sum != 0 ){
//				result.addEntry(i, sum);
//				
//			}
//			
//				
//		}
//		return result;
//	}
	/**
	 * Linksseitige Vektormultiplikation
	 * @param vec
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Vector multiplyVectorMatrix( Vector vec){
//								throws IllegalArgumentException{
//		if( vec.getSize() != rowNum)//Dimensions-check
//			throw new IllegalArgumentException("Dimension mismatch");
		double[] result = new double[colNum];
//		Vector result = new Vector();
		
		for( int k=0 ; k<colNum ; k++){
			double sum = 0;
			for(Triple trip : columns.get(k)){
				int rowInd = trip.getRow();
				sum += trip.getEntry()*vec.get(rowInd);
			}
			result[k] = sum;
		}
		
		return new Vector(result);
	}
	
	
	/**
	 * Multipliziert Eta-Matrix mit this (E*A)
	 * (Attention!!! Overwrites the given Matrix)
	 * @param eta Eta-Spalte
	 * @param index Spaltenindex der Eta- Spalte
	 */
	public void multiplyEta( Vector eta , int index){
		
		
		for(int x = 0; x<eta.getVec().length; x++){// Tupel<Integer,Double> tup : eta.getVec()){//suche von 0 versch. Eintrag in eta
			int row = x;						//und bearbeite entsprechende Zeile von A
			for( int i=0 ; i<colNum ; i++){
				double entry_index = 0;//Eintrag in A, Zeile 'index'
				//double entry_row =0;//Eintrag in A, Zeile 'row'
				int k=0;
				while( columns.get(i).get(k).getRow() < index ){//suche Element in der aktuellen Spalte
					k++;										// das an 'index'- Stellt steht
					if(k == columns.get(i).size() ) break;
				}
				if( k == columns.get(i).size() || columns.get(i).get(k).getRow() > index){
					continue;						//es ist Null, und bleibt Null --> naechste Spalte
					
				}
				if( columns.get(i).get(k).getRow() == index){//es ist ungl. 0 mit Wert 'entry'
					entry_index = columns.get(i).get(k).getEntry();
				}
				if(row == index){// 'index'- Zeile
					columns.get(i).get(k).setEntry(entry_index * eta.getVec()[x]);
					//er sollte den Eintrag implizit auch in rows aendern, tut er hoffentlich
					//sollte aber noch ueberprueft werden TODO
				}else if( row < index){
					int j=0;
					while( columns.get(i).get(j).getRow() < row){
						j++;
					}
					if( columns.get(i).get(j).getRow() > row){//Element ist 0 
						Triple trip = new Triple( row , i , entry_index*eta.getVec()[x]);
						columns.get(i).add(j, trip);
						insertNewElementToRows(row, i, trip);
				
					}else
					if( columns.get(i).get(j).getRow() == row){
						columns.get(i).get(j).setEntry(entry_index * eta.getVec()[x] + columns.get(i).get(j).getEntry());
					}
				}else{
					while( columns.get(i).get(k).getRow() < row ){
						k++;										
						if(k == columns.get(i).size() ) break;
					}
					if(k == columns.get(i).size() ){//Element ist 0
						Triple trip = new Triple( row , i , entry_index*eta.getVec()[x]);
						columns.get(i).add(trip);
						insertNewElementToRows(row, i, trip);
					
					}else
					if( columns.get(i).get(k).getRow() == row){
						columns.get(i).get(k).setEntry(entry_index * eta.getVec()[x] + columns.get(i).get(k).getEntry());

					}

				}
				
				
					
			}
		}
	}
	
	private void insertNewElementToRows( int row , int col , Triple entry){
		int k=0;
		while( rows.get(row).get(k).getColumn() < col){
			k++;
			if( k == rows.get(row).size()) break;
		}
		rows.get(row).add(k, entry);
	}
	
	public void negateRow(int row){
		for(Triple e : this.rows.get(row)){
			e.setEntry(-e.getEntry());
		}
	}
	
	public int getRowNum() {
		return rowNum;
	}

	public int getColNum() {
		return colNum;
	}
	
	public void createI(int m){
		for(int i = 0; i < m; i++){
			this.addColumn();
			this.addRow();
			this.addEntry(i, i, 1);
		}
	}

	public String toString(){
		String erg = "";
	
		for(int x = 0; x < rows.size(); x++){
//			erg = "";
			int i =0;
			for(Triple t : rows.get(x)){
				if( t.getColumn() > i){
					while( i< t.getColumn()){
						erg += " ; 0";
						i++;
					}
				}
				i++;
				erg = erg + " ; " + t.getEntry();
			}
			if(i < colNum){
				while( i< colNum){
					erg += " ; 0";
					i++;
				}
			}
			erg += "\n";
		}
		return erg;
	}
	
	private Vector getMatrixColumn(int i){
		double[] vec = new double[rowNum];
		
		for( Triple t : columns.get(i)){
			vec[t.getRow()] = t.getEntry();
		}
		return new Vector(vec);
	}
	
	public static void main(String[] args) {
		Matrix test = new Matrix();
		for (int i=0 ; i<3 ;i++){
			test.addColumn();
			test.addRow();
		}
		
/*		test.addEntry(0, 1, 2);//0  2
		test.addEntry(1, 0, 1);//1  2
		test.addEntry(1, 1, 2);
		vec.addEntry(0, 2);
		vec.addEntry(1, 2);//2  2
		//Vector res = test.multiplyMatrixVektor(vec);
		Vector res = test.multiplyVectorMatrix(vec);
		System.out.println("mat: "+ test);
		System.out.println(res);
*/		
		test.addEntry(0, 0, 1);
//		test.addEntry(0, 1, 2);
		test.addEntry(1, 1, 1);
//		test.addEntry(2, 1, 1);
		test.addEntry(2, 2, 2);

		double[] vec = new double[3];
		vec[0]=0;
		vec[1]=1;
		vec[2]=2;
		Vector vect = new Vector(vec);

		test.multiplyEta(vect, 1);
		System.out.println(test);
		int[] b = new int[3];
		b[0]= 1; b[1]=2 ; b[2]=3;

//		System.out.println(test.multiplyVectorMatrixColumn(vec, 1));
//		Vector v = test.multiplyCbMatrix(vec, b);
//		System.out.println(v);
	}

}
