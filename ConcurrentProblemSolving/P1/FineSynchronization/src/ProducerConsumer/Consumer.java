package ProducerConsumer;

public class Consumer implements Runnable {

	private int number;
	private SharedQueue queue;

	public Consumer(SharedQueue queue, int number) {

		this.number = number;
		this.queue = queue;
	}

	public void run() {

		while(true) {

			int elementValue = queue.take();

			System.out.println("Consumatorul " + number + " a consumat elementul " + elementValue);


			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

