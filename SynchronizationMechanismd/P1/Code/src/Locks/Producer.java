package Locks;

public class Producer extends Thread {

	private int nrProducers;// numarul de producatori
	private int number; // elementul produs
	private int Max;
	private int Min;
	private Market buffer; // buffer -ul unde sunt stocate elementele produse
	private int counter = 0;// variabila contor pentru contorizarea elementelor produse de fiecare
							// producator

	public Producer(int nrProducers, int number, int Min, int Max, Market buffer) {
		this.nrProducers = nrProducers;
		this.number = number;
		this.Min = Min;
		this.Max = Max;
		this.buffer = buffer;
	}

	public int getCounter() {
		return counter;
	}

	public void run() {

		long startTime = System.nanoTime();
		int elementValue = Min; // valoarea elementului produs

		while ((elementValue % nrProducers) != number) { // determina primul numar produs
			elementValue++;
		}

		while (elementValue <= Max) {

			buffer.produce(elementValue);
			counter++;
			System.out.println("Producer " + number + " produced " + elementValue);
			elementValue = elementValue + nrProducers;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("Time execution for " + number + " Producer: " + elapsedTime / 1000000);
	}
}
