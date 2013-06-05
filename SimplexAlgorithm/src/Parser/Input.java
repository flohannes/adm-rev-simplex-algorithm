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
	private Vector b;	//RHS
	
	
	
	public Input( String path) throws IOException{
		
		m = new Matrix();
		cn = new ArrayList<String>();
		rn = new ArrayList<String>();
		ec = new ArrayList<Tupel<String, String>>();
		c = new  Vector();
		b = new  Vector();
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line = null;

		boolean rows = false;
		boolean columns = false;
		boolean rhs = false; 
		
		int counter = 0;
		while ((line = in.readLine()) != null) {
//			line.trim();
//			String s = line.trim().replaceAll(" +"," ");
////			System.out.println();
//			String[] zeile2 = s.split(" ");
//			System.out.println(counter + " : "+ line.isEmpty() +" . length:" +  zeile2.length);
//			counter++;
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
				String tempZeile = line.trim().replaceAll(" +", " ");
				String[] zeile = tempZeile.split(" ");
				ec.add(new Tupel<String, String>(zeile[0], zeile[1]));
				rn.add(zeile[1]);
				m.addRow();
			}else
			if ( columns){//Spalten einlesen
				String tempZeile = line.trim().replaceAll(" +", " ");
				String[] zeile = tempZeile.split(" ");
				if(!cn.contains(zeile[0])){
					cn.add(zeile[0]);
					m.addColumn();
				}
				if(zeile[1].equals("COST")){
					c.addEntry(cn.indexOf(zeile[0]), Double.parseDouble(zeile[2]));
				} else{
					m.addEntry(rn.indexOf(zeile[1]), cn.indexOf(zeile[0]), Double.parseDouble(zeile[2]));
				}
				if(zeile.length > 3){
					if(zeile[3].equals("COST")){
						c.addEntry(cn.indexOf(zeile[0]), Double.parseDouble(zeile[4]));
					} else{
						m.addEntry(rn.indexOf(zeile[3]), cn.indexOf(zeile[0]), Double.parseDouble(zeile[4]));
					}
				} 
				
			}else
			if( rhs){//rechte seite einlesen
				String tempZeile = line.trim().replaceAll(" +", " ");
				String[] zeile = tempZeile.split(" ");
				b.addEntry(rn.indexOf(zeile[1]), Double.parseDouble(zeile[2]));
				if(zeile.length > 3){
					b.addEntry(rn.indexOf(zeile[3]), Double.parseDouble(zeile[4]));
				}
			}
		}
		in.close();

	}
	
	public static void main (String[] arg){
		try {
			Input in = new Input("src/InputData/bsp.mps");
			System.out.println("Ausgabe: ");
			System.out.println(in.getM().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
