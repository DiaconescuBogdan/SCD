package MatrixMultiplication;

import java.util.concurrent.locks.ReentrantLock;

public class Multiplier implements Runnable {

	private int number;
	private int range;
	private int size;
	private int A[][];
	private int B[][];
	private static int counter = 0;
	private static ReentrantLock counterLock = new ReentrantLock();

	public Multiplier(int number, int range, int A[][], int B[][], int size) {
		this.number = number;
		this.A = A;
		this.B = B;
		this.size = size;
		this.range = range;
	}

	public void run() {

		// Inmultirea matricilor pe intervalul specificat

		for (int i = 0; i < size; ++i) {

			for (int j = range * number; j < range * (number + 1); ++j) {

				for (int k = 0; k < size; ++k) {

					MainClass.C[i][j] += A[i][k] + B[k][j];
				}
			}
		}

		// Threadul si-a incheiat treaba

		try {
			counterLock.lock();
			counter++;
		} finally {
			counterLock.unlock();
		}

	}

	public static int finished() {

		int threadsFinished = 0;

		// Blocheaza counterLock si obtine valoarea acestuia

		try {
			counterLock.lock();
			threadsFinished = counter;
		} finally {
			counterLock.unlock();
		}

		return threadsFinished;
	}
}
