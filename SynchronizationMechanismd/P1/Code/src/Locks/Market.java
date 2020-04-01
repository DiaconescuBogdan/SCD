package Locks;

import java.util.concurrent.locks.ReentrantLock;

public class Market {

	private final int N = 5; // numarul de lemente din buffer
	private int oldest = 0; // indicele de pozitie pentru elementele extrase
	private int newest = 0; // indicele de pozitie pentru elementele introduse
	private volatile int count = 0; // variabila contor pentru numarul de elemente din buffer
	private int buffer[] = new int[N]; // buffer
	private ReentrantLock bufferLock = new ReentrantLock(); // crearea zavorului pentru buffer
	private boolean notFull = true; // variabila booleana pentru a verifica daca bufferul este plin
	private boolean notEmpty = false; // variabila booleana pentru a verifica daca bufferul este gol

	public void produce(int value) {

		bufferLock.lock(); // blocarea zavorului

		try {
			if (notFull) { // verifica daca bufferul este plin

				buffer[newest] = value; // adauga un element in buffer
				newest = (newest + 1) % N; // trece la urmatoarea pozitie din buffer
				count++; // incrementeaza numarul de obiecte din buffer

			}
			if (count == N) { // verifica daca numarul de elemente din buffer este egal cu N

				notFull = false; // in caz afirmativ, bufferul este plin
			}
			notEmpty = true; // in caz contrat, bufferul nu este gol

		} finally {

			bufferLock.unlock(); // deblocarea zavorului
		}

	}

	public int consume() {

		int value = -1;

		bufferLock.lock(); // blocarea zavorului
		try {
			if (notEmpty) { // verifica daca bufferul este gol
				value = buffer[oldest]; // extrage elementul din buffer
				oldest = (oldest + 1) % N; // trece la urmatoarea pozitie din buffer
				count--; // decrementeaza numarul de elemente din buffer

			}
			if (count == 0) { // verifica daca numarul de elemente din buffer este 0
				notEmpty = false; // in caz afirmativ, bufferul este gol
			}
			notFull = true; // in caz contrat, bufferul nu este plin
		} finally {

			bufferLock.unlock(); // deblocarea zavorului

		}
		return value; // returneaza valoarea extrasa din buffer
	}
}
