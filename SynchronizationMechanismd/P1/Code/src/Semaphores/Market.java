package Semaphores;

import java.util.concurrent.Semaphore;

public class Market {

	private final int N = 5;
	private int oldest = 0;
	private int newest = 0;
	private volatile int count = 0;
	private int buffer[] = new int[N];

	// Crearea semafoarelor
	private Semaphore producerSemaphore = new Semaphore(1);
	private Semaphore consumerSemaphore = new Semaphore(0);
	private Semaphore notFull = new Semaphore(1);
	private Semaphore notEmpty = new Semaphore(0);

	// functie pentru a adauga elemente in buffer
	public void produce(int value) {

		try {
			// cererea permisunilor de la produceSemaphore si notFull pentru adaugarea
			// elementului in buffer
			producerSemaphore.acquire();
			notFull.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// s-a primit permisiunea si incepe "producerea"
		buffer[newest] = value;
		newest = (newest + 1) % N;
		count++;

		if (count < N) { // verifica daca buffer -ul este plin
			notFull.release(); // daca nu este plin, incrementeaza semaforul
		}

		if (count != 0) { // verifica daca buffer -ul este gol
			notEmpty.release(); // daca nu este gol, incrementeaza semaforul
		}
		// incrementarea semafoarelor
		producerSemaphore.release();
		consumerSemaphore.release();
		notEmpty.release();

	}

	// functie pentru a extrage elemente din buffer
	public int consume() {

		try {
			// cererea permisunilor de la consumeSemaphore si notEmpty pentru extragerea
			// elementului din buffer
			consumerSemaphore.acquire();
			notEmpty.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// s-a primit permisiunea si incepe extragerea elementului
		int temp;
		temp = buffer[oldest];
		oldest = (oldest + 1) % N;
		count--;

		if (count != 0) { // verifica daca bufferul este gol
			notEmpty.release(); // daca nu este gol, incrementare semaforul
		}

		if (count < N) { // verifica daca bufferul este plin
			notFull.release(); // daca nu este plin, incrementeaza semaforul
		}
		consumerSemaphore.release();

		return temp; // returneaza valoarea extrasa
	}
}
