package Datenstrukturen;

import java.util.ArrayList;

public class Vector {

	private ArrayList<Tupel<Integer, Double>> vec;
	
	public Vector(){
		vec = new ArrayList<Tupel<Integer, Double>>();
	}
	
	public void addEntry(int index, double value){
		vec.add(new Tupel<Integer, Double>(index, value));
	}
}
