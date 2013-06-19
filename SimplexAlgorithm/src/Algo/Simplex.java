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
	private Vector bQuer;
	
	public Simplex(LP lp){
		this.lp = lp;
		this.originalCostFunction = lp.getC().clone();
		this.basisInverse = new Matrix();
		this.basis = lp.getBasis();
		this.nichtbasis = lp.getNichtBasis();
		this.m = lp.getM();
		this.isPerfect = false;
		this.bQuer = lp.getB().clone();
	}
	
	
	public void calculateOptimum(){
//		this.phase1();
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
		Vector d = this.FTRAN(maxIndex);
		int indexChuzr = this.CHUZR(d);
		this.WRETA(maxIndex, indexChuzr, d);
	}
	
	private void phase2(){
		basisInverse.createI(basis.length);
		System.out.println(this.bQuer);
		for(int i = 0; i < this.basis.length; i++){
			System.out.println(this.basis[i]);
		}
		int counter = 1;
		while(true){
			this.BTRAN(originalCostFunction);
			int maxIndex = this.PRICE(originalCostFunction);
			if(maxIndex == -1)
				break;
			Vector d = this.FTRAN(maxIndex);
			int indexChuzr = this.CHUZR(d);
			this.WRETA(maxIndex, indexChuzr, d);
			System.out.println("Runde: "+counter);
			System.out.println(this.bQuer);
			for(int i = 0; i < this.basis.length; i++){
				System.out.println(this.basis[i]);
			}
			counter++;
		}
		
	}
	
	
	private void BTRAN(Vector cost){
//		Vector cB = new Vector();
		double[] cBi = new double[basis.length];
		for(int i = 0; i < basis.length; i++){
			cBi[i] = cost.get(basis[i]);
		}
		Vector cB = new Vector(cBi);
		schattenpreise = basisInverse.multiplyVectorMatrix(cB);
//		System.out.println(basisInverse.getColNum()+" , "+basisInverse.getRowNum());
	}
	
	private int PRICE( Vector cost){
		int MaxIndex =-1;
		double max=0;
		
		for( int i=0 ; i<nichtbasis.length ; i++){
			
			double redCost = cost.get(nichtbasis[i]) - m.multiplyVectorMatrixColumn(schattenpreise, nichtbasis[i]);
			if( redCost > max){
				MaxIndex = i;	
				max = redCost;
			}
		}
		if( max == 0)
			isPerfect = true;
		return MaxIndex;
	}
	
	public Vector FTRAN(int maxIndex){
		return basisInverse.multiplyMatrixMatrixColumn(m, nichtbasis[maxIndex]);
	}
	
	public int CHUZR(Vector d){
		Tupel<Integer,Double> lambda0 = this.lambda0(d);
		return lambda0.getNum();
	}
	
	private Tupel<Integer, Double> lambda0(Vector d){
		double minLambda=Double.POSITIVE_INFINITY;
		int index = -1;
		for(int i = 0; i < this.bQuer.getVec().length; i++){
			if(minLambda > this.bQuer.get(i) / d.get(i) && d.get(i) > 0){
				minLambda = this.bQuer.get(i) / d.get(i);
				index = i;
			}
		}
		return new Tupel<Integer, Double>(index, minLambda);
	}
	
	public void WRETA(int indexPrice, int indexChuzr, Vector d){
		double[] eta = new double[d.getVec().length];
		double eintragStelleChuzr = d.get(indexChuzr);
		for(int i = 0; i < d.getVec().length; i++){
			if(i == indexChuzr)
				eta[i] = 1 / eintragStelleChuzr;
			else
				eta[i] = - d.get(i) / eintragStelleChuzr;
		}
		basisInverse.multiplyEta(new Vector(eta), indexChuzr);
		bQuer = basisInverse.multiplyMatrixVektor(this.lp.getB());
		int basisTmp = basis[indexChuzr];
		basis[indexChuzr] = nichtbasis[indexPrice];
		nichtbasis[indexPrice] = basisTmp;
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
			
			System.out.println(simplex.bQuer);
			for(int i = 0; i < simplex.basis.length; i++){
				System.out.println(simplex.basis[i]);
			}
//			for()
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
