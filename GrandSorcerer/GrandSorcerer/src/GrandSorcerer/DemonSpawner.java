package GrandSorcerer;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class DemonSpawner extends Thread {

	private Coven coven;

	public DemonSpawner(Coven coven) {
		this.coven = coven;
	}

	public void run() {

		while (true) {

			Random rand = new Random();
			long milis = rand.nextInt(1000) + 500;

			// Sleeping a random time between 500 and 1000 milliseconds
			try {
				Thread.sleep(milis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Spawning a demon
			spawnAnDemon();
		}
	}

	private void spawnAnDemon() {

		Random rand = new Random();

		// Getting the coven lock
		ReentrantLock covenLock = coven.getCovenLock();

		// Demons can't move while adding a new demon in the coven
		covenLock.lock();

		// Getting the coven matrix size
		int covenSize = coven.getN();

		if (coven.nrExistingDemons() != covenSize / 2) {

			// Randomizing a position for the demob
			int X = rand.nextInt(covenSize) + 0;
			int Y = rand.nextInt(covenSize) + 0;

			// Getting the demons counter lock
			ReentrantLock demonsCounterLock = GrandSorcererCircle.getElvesCounterLock();

			// No other thread can access the number of demons in the coven
			// since it's being modified now
			demonsCounterLock.lock();

			// Creating a new demon
			Demon demon = new Demon(GrandSorcererCircle.nrTotalDemons, X, Y, coven);

			// Try inserting the demon in the coven
			if (coven.addDemon(demon)) {

				GrandSorcererCircle.nrTotalDemons++;
				System.out.println("Demon " + demon.getNumber() + " was created in coven " + coven.getNumber());
			}

			// Unlock the demons counter
			demonsCounterLock.unlock();

		}
		// Unlock the coven
		covenLock.unlock();

	}

}
