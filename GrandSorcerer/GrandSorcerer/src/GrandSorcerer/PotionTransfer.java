package GrandSorcerer;

public class PotionTransfer {
	private volatile int head = 0;
	private volatile int tail = 0;
	private int[] potions = new int[10];

	public synchronized int receivePotion() {

		int potion = 0;

		// Waiting until the buffer is no longer empty
		while (tail == head) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Getting the potion from the buffer
		potion = potions[head % potions.length];
		head++;

		// Notify that the buffer is not full
		notifyAll();

		return potion;
	}

	public synchronized void givePotion(int potion) {

		// Waiting until the buffer is no longer full
		while (tail - head == potions.length) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Adding the potion in the buffer
		potions[tail % potions.length] = potion;
		tail++;

		// Notify that the buffer is not empty
		notifyAll();

	}
}
