package P1;

import java.util.ArrayList;
import java.util.Random;

public class MainClass2 {

	public static long startTime; // variabila statica deoarece nu se modifica pe parcursul programului

	public static void main(String[] args) {

		int n = generate(100, 500);
		System.out.println("n = " + n);

		int k = generate(100, 500);
		System.out.println("k = " + k);

		ArrayList<Integer> M = new ArrayList<Integer>();
		startTime = System.nanoTime();

		for (int iterator = 1; iterator <= n; iterator++) {
			if (iterator % (k + 1) != 0 || iterator == k + 1) { // adaugam in M numerele care nu sunt multiplii de k + 1
				// exceptand numarul k +1;
				M.add(iterator);
			}
		}
		PrimeSolution2[] findPrimes = new PrimeSolution2[k + 1];

		for (int j = 1; j <= k; j++) { // impartim multimea M in k submultimi
			ArrayList<Integer> Mj = new ArrayList<Integer>();
			for (int i : M) {
				if (i % (k + 1) == j) {
					// Adugam in multimea Mj numerele din multimea M care impartite la k + 1 au
					// restul j
					Mj.add(i);
				}
			}

			if (j == 1) {
				// Cazul particular k + 1
				Mj.add(k + 1);
			}

			findPrimes[j] = new PrimeSolution2(Mj, j); // Crearea unui nou thread

			findPrimes[j].start(); // executia thread -ului

		}
	}

	public static int generate(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
