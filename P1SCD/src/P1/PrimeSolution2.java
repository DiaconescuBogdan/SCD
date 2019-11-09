package P1;

import java.util.ArrayList;

public class PrimeSolution2 extends Thread {
	private ArrayList<Integer> M;
	private int j; // il vom folosi pentru identificarea multimii unde se cauta numerele prime

	public PrimeSolution2(ArrayList<Integer> M, int j) { // Constructor
		this.M = M;
		this.j = j;
	}

	public static boolean isAPrimeNumber(int x) {
		if (x == 1) {
			return false;
		} else {
			for (int i = 2; i <= Math.sqrt(x); ++i) {
				if (x % i == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public void run() {
		for (int i : M) {
			if (isAPrimeNumber(i)) {
				System.out.println(i);
			}
		}

		long endTime = System.nanoTime();
		long executionTime = (endTime - MainClass2.startTime) % 10000; // timpul de executie al thread -ului
		System.out.println("Numerele prime din multimea M" + j + " : " + executionTime + " milisecunde");

	}

}
