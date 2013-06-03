package Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Input {

	
	public Input( String path) throws IOException{
		
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line = null;

		boolean rows = false;
		boolean columns = false;
		boolean rhs = false; 
		
		while ((line = in.readLine()) != null) {
			
			if( line.equals("ROWS")){
				rows = true;
			}else
			if( line.equals("COLUMNS")){
				rows = false;
				columns = true;
			}else
			if( line.equals("RHS")){
				columns = false;
				rhs = true;
			}else	
			if (rows){//Zeilen einlesen
				
				
			}else
			if ( columns){//Spalten einlesen
				
				
			}else
			if( rhs){//rechte seite einlesen
				
			}
		}
		in.close();

	}
	
	public static void main (String[] arg){
		
	}
}
