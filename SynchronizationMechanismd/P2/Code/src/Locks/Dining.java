package Locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dining {

	public static void main(String[] args) {

		// Crearea unui vector de zavoare pentru a accesa furculitele
		Lock forks[] = new ReentrantLock[5];

		for (int i = 0; i < 5; ++i) {
			forks[i] = new ReentrantLock();
		}

		// Crearea filozofilor

		Philosopher p1 = new Philosopher(forks[4], forks[0], "first");
		Philosopher p2 = new Philosopher(forks[0], forks[1], "second");
		Philosopher p3 = new Philosopher(forks[1], forks[2], "third");
		Philosopher p4 = new Philosopher(forks[2], forks[3], "fourth");
		Philosopher p5 = new Philosopher(forks[3], forks[4], "fifth");

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
