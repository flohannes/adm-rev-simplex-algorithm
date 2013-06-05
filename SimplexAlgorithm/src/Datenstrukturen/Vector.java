package Datenstrukturen;

import java.util.ArrayList;

public class Vector {

	private ArrayList<Tupel<Integer, Double>> vec;
	
	private int size;
	
	public Vector(){
		vec = new ArrayList<Tupel<Integer, Double>>();
	}
	
	public Vector( int size){
		vec = new ArrayList<Tupel<Integer, Double>>();
		this.size = size;
	}
	
	public void addEntry(int index, double value){
		vec.add(new Tupel<Integer, Double>(index, value));
	}
	
	/**
	 * Laenge der ArrayList
	 * @return
	 */
	public int getLength(){
		return vec.size();
	}
	
	public Tupel get(int index){
		return vec.get(index);
	}

	@Override
	public String toString() {
		String out = "";
		for( Tupel<Integer, Double> t : vec){
			out += t.getEntry() + " ; ";
		}
		return "Vector: \n"+out;
	}

	/**
	 * Tatsaechliche Länge des Vektors, d.h. die 0-Eintraege mitgezaehlt
	 * @return
	 */
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}
