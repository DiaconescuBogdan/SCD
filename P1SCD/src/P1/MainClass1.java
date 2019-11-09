package P1;

import java.util.Random;

public class MainClass1 {

	public static long startTime; // variabila statica deoarece nu se modifica pe parcursul programului

	public static void main(String[] args) {

		int n = generate(0, 100);
		System.out.println("n = " + n);

		int k = generate(0, 100);
		System.out.println("k = " + k);

		startTime = System.nanoTime(); // salvam momentul inceperii thread -ului

		int q = n / k; // catul impartirii lui n la k
		PrimeSolution1[] findPrimes = new PrimeSolution1[k]; // exista k fire de executie

		for (int interator = 0; interator < k; interator++) { // exista k intervale
			int start = interator * q + 1; // valoare de inceput a intervalului
			int end = q * (interator + 1); // valoarea de sfarsit a intervalului

			findPrimes[interator] = new PrimeSolution1(start, end); // Crearea unui nou thread

			findPrimes[interator].start(); // executia thread -ului
		}
	}

	public static int generate(int min, int max) { // returneaza un numar intreg cuprins intre min si max
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
