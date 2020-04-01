package GrandSorcerer;

public class DemonRetirement extends Thread {

	public void run() {

		while (true) {

			// Releasing a permit for a demon to retire
			GrandSorcererCircle.demonRetireSemaphore.release();

			// Sleeping 50 milliseconds
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
