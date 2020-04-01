package Semaphores;

public class Producer extends Thread {

	private int nrProducers;
	private int number;
	private int Max;
	private int Min;
	private Market buffer;
	private int counter = 0;

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
		int elementValue = Min;

		while ((elementValue % nrProducers) != number) {
			elementValue++;
		}

		while (elementValue <= Max) {
			buffer.produce(elementValue);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter++;
			System.out.println("Producer " + number + " produced " + elementValue);
			elementValue = elementValue + nrProducers;
		}
		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("Time execution for " + number + " Producer: " + elapsedTime/1000000);
	}
}
