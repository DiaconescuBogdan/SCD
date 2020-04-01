package ProducerConsumer;

public class Producer implements Runnable {


	private SharedQueue queue;
	private int number;

	public Producer(SharedQueue queue, int number) {

		this.queue = queue;
		this.number = number;
	}

	public void run() {

		int elementValue = number;
		while(true) {

			queue.put(elementValue);
			System.out.println("Producatorul "+number+" a produs elementul "+ elementValue);
			elementValue+= number;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}


