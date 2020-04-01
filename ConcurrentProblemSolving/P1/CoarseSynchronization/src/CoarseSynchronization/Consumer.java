package CoarseSynchronization;


public class Consumer extends Thread {


	private int number;
	private LockBasedQueue queue;


	public Consumer(LockBasedQueue queue, int number) {

		this.number = number;
		this.queue = queue;
	}

	public void run() {

		while(true) {

			int elementValue = 0;

			elementValue = queue.take();
			System.out.println("Consumatorul " + number + " a consumat elementul " + elementValue);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
