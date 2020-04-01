package Monitors;

public class Table {

	private boolean forks[] = new boolean[5]; // vector de tipul bool

	public synchronized void eat(int leftFork, int rightFork, String name) {

		// Asteapta ca ambele furculite sa fie puse pe masa
		while (forks[leftFork] || forks[rightFork]) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Ia furculitele
		forks[leftFork] = forks[rightFork] = true;

		// Incepe sa manance
		try {

			System.out.println(name + " eating");

			Thread.sleep(1000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		// Pune furculitele pe masa
		forks[leftFork] = forks[rightFork] = false;
		System.out.println(name + " is done eating and now is thinking");

		notify(); //

	}

	public synchronized void think(String name) {

		try {

			System.out.println(name + " thinking");
			Thread.sleep(1000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
