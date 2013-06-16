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
	private Vector schattenpreise;
	
	public Simplex(LP lp){
		this.lp = lp;
		this.originalCostFunction = lp.getC();
		this.basisInverse = new Matrix();
		this.basis = lp.getBasis();
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
	
	private void PRICE(){
		
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
