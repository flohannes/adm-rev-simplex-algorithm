package Datenstrukturen;

import java.util.ArrayList;

public class Vector {

	private ArrayList<Tupel<Integer, Double>> vec;
	
	private int size;
	
	public Vector(){
		vec = new ArrayList<Tupel<Integer, Double>>();
	}
	
	public void negateBi(int i){
		this.vec.get(i).setEntry(-this.vec.get(i).getEntry());
	}

	public Vector( int size){
		vec = new ArrayList<Tupel<Integer, Double>>();
		this.size = size;
	}
	
	public void addEntry(int index, double value) throws IllegalArgumentException{
		
		if(vec.isEmpty()){
			vec.add(new Tupel<Integer, Double>(index, value));
		}
		else if(vec.get(vec.size()-1).getNum() < index){
			vec.add(new Tupel<Integer, Double>(index, value));
		}
		else{
			int i;
			for( i=0 ; i< vec.size() ; i++){
				if( vec.get(i).getNum() == index)
					throw new IllegalArgumentException("Element existiert bereits!!!");
				if( vec.get(i).getNum() > index)
					vec.add(index, new Tupel<Integer, Double>(index, value));
			}
		}
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
	
	public ArrayList<Tupel<Integer, Double>> getVec() {
		return vec;
	}
	
}
