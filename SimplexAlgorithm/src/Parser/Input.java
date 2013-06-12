package Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Datenstrukturen.LP;
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
	private boolean isMax;
	private ArrayList<Tupel<Integer, Double>> upperBound;
	private ArrayList<Tupel<Integer, Double>> lowerBound;
	
	
		
	public LP readInput( String path) throws IOException{
		setMax(true);
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
		boolean bounds = false;
		
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
			}else if(line.equals("OBJSENSE")){
				String max = in.readLine();
				if(max.equals("MAX")){
					setMax(true);
				} else if(max.equals("MIN")){
					setMax(false);
				}
			}
			else
			if( line.equals("COLUMNS")){
				rows = false;
				columns = true;
			}else
			if( line.equals("RHS")){
				columns = false;
				rhs = true;
			}else if(line.equals("BOUNDS")){
				rhs = false;
				bounds = true;
			}
			else if(line.equals("ENDATA")){
				break;
			}
			else if (rows){//Zeilen einlesen
				String tempZeile = line.trim().replaceAll(" +", " ");
				String[] zeile = tempZeile.split(" ");
				if( !zeile[0].equals("N")){//fuer die kosten keine matrix-zeile erstellen
					ec.add(new Tupel<String, String>(zeile[0], zeile[1]));
					rn.add(zeile[1]);
					m.addRow();
				}
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
			if(bounds){
				String tempZeile = line.trim().replaceAll(" +", " ");
				String[] zeile = tempZeile.split(" ");
				
				if(zeile[0].equals("UP")){
					upperBound.add(new Tupel<Integer, Double>(cn.indexOf(zeile[2]), Double.parseDouble(zeile[3])));
				} else if (zeile[0].equals("LO")){
					lowerBound.add(new Tupel<Integer, Double>(cn.indexOf(zeile[2]), Double.parseDouble(zeile[3])));
				}
			}
		}
		in.close();
		b.setSize(rn.size());
		c.setSize(cn.size());
		
		System.out.println("matrix: \n"+m);
		
		return new LP(m, ec, rn, c, b, bounds, upperBound, lowerBound);
	}
	
	public static void main (String[] arg){
		try {
			Input in = new Input();
			in.readInput("src/InputData/Bsp_28.mps");
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

	public boolean isMax() {
		return isMax;
	}

	public void setMax(boolean isMax) {
		this.isMax = isMax;
	}

}
