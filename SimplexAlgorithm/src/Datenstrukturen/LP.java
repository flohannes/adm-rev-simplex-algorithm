package Datenstrukturen;

import java.util.ArrayList;

public class LP {

	private Matrix m;	
//	private ArrayList<Tupel<String, String>> ec;	//Is row equation or inequality? (eq?, name)
	private Vector c;	//objective function /cost
	private Vector b;	//RHS
	private boolean isMax;
	private ArrayList<Tupel<Integer, Double>> upperBound;
	private ArrayList<Tupel<Integer, Double>> lowerBound;
	private int[] basis;
	
	public LP(Matrix m,	ArrayList<Tupel<String, String>> ec, ArrayList<String> rn, Vector c, Vector b,
			boolean isMax, ArrayList<Tupel<Integer, Double>> upperBound, ArrayList<Tupel<Integer, Double>> lowerBound) {
		this.m = m;
//		this.ec = ec;
		this.c = c;
		this.b = b;
		this.isMax = isMax;
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.basis = new int[m.getRowNum()];
		for(Tupel<String, String> eq : ec){
			if(eq.getNum().equals("L")){
				m.addColumn();
				m.addEntry(rn.indexOf(eq.getEntry()), m.getColNum()-1, 1);
				for(Tupel<Integer, Double> bi : b.getVec()){
					if(bi.getNum() == rn.indexOf(eq.getEntry())){
						if(bi.getEntry() > 0){
							basis[rn.indexOf(eq.getEntry())] = m.getColNum()-1;
							break;
						}else if(bi.getEntry() < 0){
							m.negateRow(rn.indexOf(eq.getEntry()));
							b.negateBi(rn.indexOf(eq.getEntry()));
							break;
						}
					}
					else if(bi.getNum() > rn.indexOf(eq.getEntry())){
						basis[rn.indexOf(eq.getEntry())] = m.getColNum()-1;
						break;
					}
				}
			} else if(eq.getNum().equals("G")){
				m.addColumn();
				m.addEntry(rn.indexOf(eq.getEntry()), m.getColNum()-1, -1);
				for(Tupel<Integer, Double> bi : b.getVec()){
					if(bi.getNum() == rn.indexOf(eq.getEntry())){
						if(bi.getEntry() < 0){
							basis[rn.indexOf(eq.getEntry())] = m.getColNum()-1;
							break;
						}else if(bi.getEntry() > 0){
							break;
						}
					}
					else if(bi.getNum() > rn.indexOf(eq.getEntry())){
						basis[rn.indexOf(eq.getEntry())] = m.getColNum()-1;
						break;
					}
				}
			} else if(eq.getNum().equals("E")){
				for(Tupel<Integer, Double> bi : b.getVec()){
					if(bi.getNum() == rn.indexOf(eq.getEntry()) && bi.getEntry() < 0){
						m.negateRow(rn.indexOf(eq.getEntry()));
						b.negateBi(rn.indexOf(eq.getEntry()));
						break;
					} else if(bi.getNum() > rn.indexOf(eq.getEntry())){
						break;
					}
				}
			}
		}
		
		for(int i = 0; i < basis.length; i++){
			if(basis[i] == 0){
				m.addColumn();
				m.addEntry(i, m.getColNum()-1, 1);
				basis[i]= m.getColNum()-1;
			}
		}
		
	}
	

}
