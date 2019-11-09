package P1;

public class PrimeSolution1 extends Thread {
	private int start;
	private int end;

	public PrimeSolution1(int start, int end) { // Constructor
		this.start = start;
		this.end = end;
	}

	public static boolean isAPrimeNumber(int x) { // functie pentru determinarea unui nr. prim
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
		for (int iterator = start; iterator <= end; iterator++) {
			if (isAPrimeNumber(iterator)) {
				System.out.println(iterator);
			}
		}

		long endTime = System.nanoTime(); // salvam momentul de timp cand se incheie thread-ul
		long executionTime = (endTime - MainClass1.startTime) % 10000; // timpul de executie al thread -ului
		System.out.println("[" + start + " , " + end + "]: " + executionTime + " milisecunde");

	}
}
