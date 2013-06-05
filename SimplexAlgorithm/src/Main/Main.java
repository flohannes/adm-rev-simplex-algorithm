package Main;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> test = new ArrayList<String>();
		test.add("x1");
		test.add("x12");
		test.add(1, "x13");
		System.out.println(test.indexOf("x1"));
		System.out.println(test);
	}

}
