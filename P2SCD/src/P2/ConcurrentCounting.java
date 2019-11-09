package P2;

public class ConcurrentCounting extends Thread {
	public static volatile int n = 0;

	public int getN() {
		return n;
	}

	public void run() {
		int temp;
		for (int iterator = 0; iterator < 10; iterator++) {
			temp = n;
			n = temp + 1;
		}

	}

	public static void main(String[] args) {

		ConcurrentCounting p = new ConcurrentCounting(); // crearea thread -ului p
		ConcurrentCounting q = new ConcurrentCounting(); // crearea thread -ului q
		p.start(); // incepe executia thread -urilor
		q.start();
		try {
			p.join(); // legarea firelor de executie
			q.join();

		} catch (InterruptedException e) { // tratarea execeptiei generate de intreruperea unui fir de executie
			e.printStackTrace();
		}
		System.out.println("n = " + n);
	}

}