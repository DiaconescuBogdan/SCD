package CoarseSynchronization;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockBasedQueue {

	private volatile int head = 0;
	private volatile int tail = 0;
	private int[] elementsList;
	private Lock queueLock = new ReentrantLock();
	private Condition notFull = queueLock.newCondition();
	private Condition notEmpty = queueLock.newCondition();

	public LockBasedQueue(int maximumLength) {
		elementsList = new int[maximumLength];
	}

	public int take() {

		int elementValue = 0;

		queueLock.lock();
		try {
			while (tail == head) // Coada este goala
				try {
					notEmpty.await(); // Asteapta pana cand un element este introdus in coada
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			elementValue = elementsList[head % elementsList.length];
			head++;

			// Un element a fost extras, deci coada nu mai este plina
			notFull.signal();

		} finally {
			queueLock.unlock();
		}
		return elementValue;
	}

	public void put(int elementValue, int producerNumber) {

		queueLock.lock();

		try {
			while (tail - head == elementsList.length) // Coada este plina
				try {
					notFull.await(); // Asteapta pana cand un element este extras
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			elementsList[tail % elementsList.length] = elementValue;
			System.out.println("Producatorul " + producerNumber + " a produs " + elementValue + " pe pozitia "
					+ tail % elementsList.length);
			tail++;

			// Un element a fost adaugat, deci coada nu mai este goala
			notEmpty.signal();

		} finally {
			queueLock.unlock();
		}
	}

}
