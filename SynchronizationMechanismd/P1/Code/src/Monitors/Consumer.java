package Monitors;

public class Consumer extends Thread {

	private int nrConsumers; // numarul de consumatori
	private int number; // elementul consumat
	private int Max;
	private int Min;
	private int counter = 0; // counter pentru afisarea numarului de elemente consumate
	private Market buffer; // bufferul din care se "consuma" elemente

	// Constructor
	public Consumer(int nrConsumers, int number, int Min, int Max, Market buffer) {

		this.nrConsumers = nrConsumers;
		this.number = number;
		this.Min = Min;
		this.Max = Max;
		this.buffer = buffer;
	}

	// Getter
	public int getCounter() {
		return counter;
	}

	public void run() {

		long startTime = System.nanoTime();
		int elementValue = Min;
		int element;

		while ((elementValue % nrConsumers) != number) { // determina primul numar consumat
			elementValue++;
		}
		while (elementValue <= Max) {
			element = buffer.consume();
			counter++;
			System.out.println("Consumer " + number + " consumed " + element);
			elementValue = elementValue + nrConsumers;
		}

		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("Time execution for " + number + " Producer: " + elapsedTime / 1000000);
	}
}
