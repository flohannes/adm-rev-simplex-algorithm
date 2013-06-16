package Algo;

import java.io.IOException;

import Datenstrukturen.LP;
import Datenstrukturen.Matrix;
import Datenstrukturen.Tupel;
import Datenstrukturen.Vector;
import Parser.Input;

public class Simplex {

	private LP lp;
	private Vector originalCostFunction;
	private Matrix basisInverse;
	private int[] basis;
	private int[] nichtbasis;
	private Vector schattenpreise;
	private Matrix m;
	private boolean isPerfect;
	
	public Simplex(LP lp){
		this.lp = lp;
		this.originalCostFunction = lp.getC();
		this.basisInverse = new Matrix();
		this.basis = lp.getBasis();
		this.nichtbasis = lp.getNichtBasis();
		this.m = lp.getM();
		this.isPerfect = false;
		
	}
	
	
	public void calculateOptimum(){
		this.phase1();
		this.phase2();
	}
	
	private void phase1(){
		double[] costP = new double[lp.getM().getColNum()];
		int indexOfKuenstlicheVar = lp.getIndexOfKuenstlicheVar();
		for(int b : basis){
			if(b>=indexOfKuenstlicheVar){
				costP[b] = -1 ;
			}
		}
		Vector costP1 = new Vector(costP);

		this.lp.setC(costP1);
		
		//Basisinverse erstellen
		basisInverse.createI(basis.length);
		
		this.BTRAN(costP1);
		int maxIndex = this.PRICE(costP1);
		this.FTRAN(maxIndex);
	}
	
	private void phase2(){
		
	}
	
	
	private void BTRAN(Vector cost){
//		Vector cB = new Vector();
		double[] cBi = new double[basis.length];
		for(int i = 0; i < basis.length; i++){
			cBi[i] = cost.get(basis[i]);
		}
		Vector cB = new Vector(cBi);
		schattenpreise = basisInverse.multiplyVectorMatrix(cB);
	}
	
	private int PRICE( Vector cost){
		int MaxIndex =-1;
		double max=0;
		
		for( int i=0 ; i<nichtbasis.length ; i++){
			double redCost = cost.get(nichtbasis[i]) - m.multiplyVectorMatrixColumn(schattenpreise, nichtbasis[i]);
			if( redCost > max)
				MaxIndex = i;	
		}
		if( max == 0)
			isPerfect = true;
		return MaxIndex;
	}
	
	public void FTRAN(int maxIndex){
		basisInverse.multiplyMatrixMatrixColumn(m, nichtbasis[maxIndex]);
	}
	
	public Vector getSchattenpreise() {
		return schattenpreise;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Input in = new Input();
			LP lin = in.readInput("src/InputData/Bsp_28.mps");
			Simplex simplex = new Simplex(lin);
			simplex.calculateOptimum();
			
			System.out.println(simplex.getSchattenpreise());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
