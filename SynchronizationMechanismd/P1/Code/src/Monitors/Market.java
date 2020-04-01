package Monitors;

public class Market {

	private final int N = 5; // capacitatea buffer -ului
	private int oldest = 0; // variabila contor pentru elementele adugate de producator in buffer
	private int newest = 0; // variabila contor pentru elementele scoase de consumator din buffer
	private volatile int count = 0; // variabila contor pentru buffer
	private int buffer[] = new int[N]; // vectorul in care se stocheaza elementele

	public synchronized void produce(int value) {

		while (count == N) { // daca se umple buffer -ul se asteapta pana se face loc
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		buffer[newest] = value; // stocarea elementului in buffer
		newest = (newest + 1) % N; // trecerea la urmatoarea pozitie
		count++; // incrementarea contorului pentru numarul de elemente din buffer
		notifyAll(); // cand procesul isi termina executia anunta celelalte procese
	}

	public synchronized int consume() {
		int value; // variabila care stocheaza elementul scos din buffer
		while (count == 0) { // daca se umple buffer -ul se asteapta pana se face loc
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		value = buffer[oldest];
		oldest = (oldest + 1) % N;
		count--; // incrementarea contorului pentru numarul de elemente din buffer
		notifyAll(); // cand procesul isi termina executia anunta celelalte procese
		return value;
	}
}
