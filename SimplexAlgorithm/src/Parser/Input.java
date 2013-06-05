package Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Datenstrukturen.Matrix;
import Datenstrukturen.Tupel;
import Datenstrukturen.Vector;

public class Input {

	private Matrix m;	
	private ArrayList<String> cn;	//column names
	private ArrayList<String> rn;	//row names
	private ArrayList<Tupel<String, String>> ec;	//Is row equation or inequality? (eq?, name)
	private Vector c;	//objective function /cost
	
	
	
	public Input( String path) throws IOException{
		
		m = new Matrix();
		cn = new ArrayList<String>();
		rn = new ArrayList<String>();
		ec = new ArrayList<Tupel<String, String>>();
		c = new  Vector();
//		b = 
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line = null;

		boolean rows = false;
		boolean columns = false;
		boolean rhs = false; 
		
		while ((line = in.readLine()) != null) {
			if(line.isEmpty()){
				continue;
			}
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
			}else if(line.equals("ENDATA")){
				break;
			}
			else if (rows){//Zeilen einlesen
				String[] zeile = line.split(" ");
				ec.add(new Tupel<String, String>(zeile[0], zeile[1]));
				rn.add(zeile[1]);
				m.addRow();
			}else
			if ( columns){//Spalten einlesen
				String[] zeile = line.split(" ");
				if(!cn.contains(zeile[0])){
					cn.add(zeile[0]);
				}
				if(zeile[1].equals("COST")){
					c.addEntry(cn.indexOf(zeile[0]), Double.parseDouble(zeile[2]));
				} else{
					m.addEntry(rn.indexOf(zeile[1]), cn.indexOf(zeile[0]), Double.parseDouble(zeile[2]));
				}
				if(zeile.length > 2){
					if(zeile[3].equals("COST")){
						c.addEntry(cn.indexOf(zeile[0]), Double.parseDouble(zeile[4]));
					} else{
						m.addEntry(rn.indexOf(zeile[3]), cn.indexOf(zeile[0]), Double.parseDouble(zeile[4]));
					}
				} 
				
			}else
			if( rhs){//rechte seite einlesen
				String[] zeile = line.split(" ");
			}
		}
		in.close();

	}
	
	public static void main (String[] arg){
		
	}

	public Matrix getM() {
		return m;
	}
	
	public ArrayList<String> getCn() {
		return cn;
	}

	public ArrayList<String> getRn() {
		return rn;
	}

}
