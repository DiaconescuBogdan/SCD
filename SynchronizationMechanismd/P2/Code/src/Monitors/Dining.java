package Monitors;

public class Dining {

	public static void main(String args[]) {

		// Crearea mesei

		Table table = new Table();

		// Crearea filozofilor

		Philosopher p1 = new Philosopher(4, 0, "first", table);
		Philosopher p2 = new Philosopher(0, 1, "second", table);
		Philosopher p3 = new Philosopher(1, 2, "third", table);
		Philosopher p4 = new Philosopher(2, 3, "fourth", table);
		Philosopher p5 = new Philosopher(3, 4, "fifth", table);

		// Punerea in executie a threadurilor

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();

		try {
			p1.join();
			p2.join();
			p3.join();
			p4.join();
			p5.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
