package MatrixMultiplication;

import java.util.Random;

public class MainClass {

	public static final int MATRIX_SIZE = 1024;
	public static int A[][] = new int[MATRIX_SIZE][MATRIX_SIZE];
	public static int B[][] = new int[MATRIX_SIZE][MATRIX_SIZE];
	public static volatile int C[][] = new int[MATRIX_SIZE][MATRIX_SIZE];

	public static void main(String[] args) {

		// Obtine numarul de procesoare virtuale disponibile

		int workingThreads = Runtime.getRuntime().availableProcessors();

		System.out.println("Threaduri disponibile : " + workingThreads);

		// Generarea random a matricelor

		Random rand = new Random();

		for (int i = 0; i < MATRIX_SIZE; ++i) {
			for (int j = 0; j < MATRIX_SIZE; ++j) {

				A[i][j] = rand.nextInt(50);
				B[i][j] = rand.nextInt(50);

			}
		}

		// Afisarea matricelor

		System.out.println("Matricea A : \n");

		for (int i = 0; i < MATRIX_SIZE; ++i) {
			for (int j = 0; j < MATRIX_SIZE; ++j) {

				System.out.print(A[i][j] + " ");

			}

			System.out.println();
		}

		System.out.println("\nMatricea B : \n");

		for (int i = 0; i < MATRIX_SIZE; ++i) {
			for (int j = 0; j < MATRIX_SIZE; ++j) {

				System.out.print(B[i][j] + " ");

			}

			System.out.println();
		}

		// Impartirea matricilor și atribuirea sarcinilor

		int range = MATRIX_SIZE / workingThreads;

		for (int i = 0; i < workingThreads; ++i) {
			(new MultiplicationExecutor()).execute(new Multiplier(i, range, A, B, MATRIX_SIZE));
		}

		while (Multiplier.finished() != workingThreads) {
			// Nu face nimic cat timp nu sunt terminate toate threadurile
		}

		// Afiseaza matricea rezultat

		System.out.println("\nMatricea C : \n");

		for (int i = 0; i < MATRIX_SIZE; ++i) {
			for (int j = 0; j < MATRIX_SIZE; ++j) {

				System.out.print(C[i][j] + " ");

			}

			System.out.println();
		}

	}

}
