package Semaphores;

public class Dining {

	public static void main(String args[]) {

		// Crarea mesei

		Table table = new Table();

		// Crearea filozofilor

		Philosopher plato = new Philosopher(4, 0, "first", table);
		Philosopher aristotle = new Philosopher(0, 1, "second", table);
		Philosopher descartes = new Philosopher(1, 2, "third", table);
		Philosopher confucius = new Philosopher(2, 3, "fourth", table);
		Philosopher socrates = new Philosopher(3, 4, "fifth", table);

		//Punerea in executie a thread -urilor

		plato.start();
		aristotle.start();
		descartes.start();
		confucius.start();
		socrates.start();

		try {
			plato.join();
			aristotle.join();
			descartes.join();
			confucius.join();
			socrates.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
