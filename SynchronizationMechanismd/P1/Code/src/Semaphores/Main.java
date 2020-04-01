package Semaphores;

public class Main {

	static final int NRPRODUCERS = 10;
	static final int NRCONSUMERS = 5;
	static final int MINN = 0;
	static final int MAXN = 76;
	static Producer producers[] = new Producer[NRPRODUCERS];
	static Consumer consumers[] = new Consumer[NRCONSUMERS];
	static Market buffer = new Market();

	public static void main(String[] args) {

		//Crearea producatorilor si startul executiei threadurilor

		for (int i = 0; i < NRPRODUCERS; ++i) {
			producers[i] = new Producer(NRPRODUCERS, i, MINN, MAXN, buffer);
			producers[i].start();
		}

		//Crearea consumatorilor si startul executiei threadurilor

		for (int i = 0; i < NRCONSUMERS; ++i) {
			consumers[i] = new Consumer(NRCONSUMERS, i, MINN, MAXN, buffer);
			consumers[i].start();
		}

		for (int i = 0; i < NRPRODUCERS; ++i) {
			try {
				producers[i].join();
			} catch (InterruptedException e) {
			}
		}

		for (int i = 0; i < NRCONSUMERS; ++i) {
			try {
				consumers[i].join();
			} catch (InterruptedException e) {
			}
		}

		//Afisarea numarului de elemente produse de fiecare producator

		for (int i = 0; i < NRPRODUCERS; ++i) {
			System.out.println("Producer " + i + " produced " + producers[i].getCounter() + " elements.");
		}

		//Afisarea numarului de elemente consumate de fiecare consumator

		for (int i = 0; i < NRCONSUMERS; ++i) {
			System.out.println("Consumer " + i + " consumed " + consumers[i].getCounter() + " elements.");
		}

	}
}
