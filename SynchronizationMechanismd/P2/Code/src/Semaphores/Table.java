package Semaphores;

import java.util.concurrent.Semaphore;

public class Table {

	private Semaphore forks[] = new Semaphore[5]; // vector de semafoare

	// Constructor
	public Table() {

		for (int i = 0; i <= 4; ++i) {
			forks[i] = new Semaphore(1); // initializarea semafoarelor
		}
	}

	// Functia prin care un filozof mananca
	public void eat(int leftFork, int rightFork, String name) {

		try {
			// cererea permisunilor de la semafoare
			forks[leftFork].acquire();
			forks[rightFork].acquire();
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}

		// s-a primit permisiunea si filozoful incepe sa manance
		try {

			System.out.println(name + " eating");

			Thread.sleep(1000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println(name + " is done eating and now is thinking");

		// incrementarea semafoarelor
		forks[leftFork].release();
		forks[rightFork].release();

	}

	// Functia prin care un filozof se gandeste
	public void think(String name) {

		try {

			System.out.println(name + " thinking");
			Thread.sleep(1000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
