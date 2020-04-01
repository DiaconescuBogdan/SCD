package GrandSorcerer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Coven extends Thread {

	private int number;
	private int N;
	private boolean coven[][];
	private ArrayList<Demon> demons = new ArrayList<Demon>();
	private ReentrantLock covenLock = new ReentrantLock();
	private ReentrantLock demonsListLock = new ReentrantLock();
	private Semaphore witchSemaphore = new Semaphore(10);
	private ReentrantLock ingredientsLock = new ReentrantLock();

	private int[] ingredients = new int[10];
	private Semaphore zombieSemaphore = new Semaphore(10);

	public Coven(int N, int number) {

		this.coven = new boolean[N + 1][N + 1];
		this.number = number;
		this.N = N;

	}

	public boolean[][] getCoven() {
		return coven;
	}

	public void setCoven(boolean[][] coven) {
		this.coven = coven;
	}

	public int nrExistingDemons() {
		return demons.size();
	}

	public int getN() {
		return N;
	}

	public int getNumber() {
		return number;
	}

	public ReentrantLock getCovenLock() {
		return covenLock;
	}

	public boolean addDemon(Demon demon) {

		// Other threads can't access the demons' list while a demon is
		// being added to it
		// Reporting can't be done while a demon is spawned
		demonsListLock.lock();

		int X = demon.getX();
		int Y = demon.getY();

		if (coven[X][Y]) {

			demonsListLock.unlock();
			return false;

		} else {

			coven[X][Y] = true;
			demons.add(demon);
			demon.start();
			demon.reportPosition();
			demonsListLock.unlock();

			return true;
		}

	}

	private boolean canMoveLeft(int X, int Y, Demon demon) {

		if (Y - 1 > 0) {
			if (!coven[X][Y - 1]) {
				return true;
			}
		} else {
			demon.setHits(demon.getHits() + 1);
		}

		return false;
	}

	private boolean canMoveDown(int X, int Y, Demon demon) {

		if (X + 1 < N) {
			if (!coven[X + 1][Y]) {
				return true;
			}
		} else {
			demon.setHits(demon.getHits() + 1);
		}
		return false;
	}

	private boolean canMoveUp(int X, int Y, Demon demon) {

		if (X - 1 > 0) {
			if (!coven[X - 1][Y]) {
				return true;
			}
		} else {
			demon.setHits(demon.getHits() + 1);
		}
		return false;
	}

	private boolean canMoveRight(int X, int Y, Demon demon) {

		if (Y + 1 < N) {
			if (!coven[X][Y + 1]) {
				return true;
			}
		} else {
			demon.setHits(demon.getHits() + 1);
		}
		return false;
	}

	public void moveDemon(Demon demon) {

		// Getting demon info
		int X = demon.getX();
		int Y = demon.getY();
		int ingredient = demon.getIngredient();
		int demonNumber = demon.getNumber();

		Random rand = new Random();

		try {

			// Only a demon can move at a time in the coven
			covenLock.lock();

			if (canMoveRight(X, Y, demon)) {

				// Change position in matrix
				coven[X][Y] = false;
				coven[X][Y + 1] = true;

				if (demon.hitsFlag == 0) {

					createIngredient(demonNumber, demon.getIngredient());
				} else {
					demon.hitsFlag--;
				}

				// Change demon's current position
				demon.changePosition(X, Y + 1);
				demon.incrementSocialLevel();

				// Ask all demons for position
				askDemonsForPosition();

			} else if (canMoveUp(X, Y, demon)) {

				// Change position in matrix
				coven[X][Y] = false;
				coven[X - 1][Y] = true;

				createIngredient(demonNumber, demon.getIngredient());

				// Change demon's current position
				demon.changePosition(X - 1, Y);
				demon.incrementSocialLevel();
				// Ask all demons for position
				askDemonsForPosition();

			} else if (canMoveDown(X, Y, demon)) {

				// Change position in matrix
				coven[X][Y] = false;
				coven[X + 1][Y] = true;

				createIngredient(demonNumber, demon.getIngredient());

				// Change demon's current position
				demon.changePosition(X + 1, Y);
				demon.incrementSocialLevel();
				// Ask all demons for position
				askDemonsForPosition();

			} else if (canMoveLeft(X, Y, demon)) {

				// Change position in matrix
				coven[X][Y] = false;
				coven[X][Y - 1] = true;

				createIngredient(demonNumber, demon.getIngredient());

				// Change demon's current position
				demon.changePosition(X, Y - 1);
				demon.incrementSocialLevel();
				// Ask all demons for position
				askDemonsForPosition();

			} else {

				// Demon can't move (its's surrounded), it stops working
				demon.stopWork();
			}

		} finally {

			covenLock.unlock();

		}

	}

	public int[] getIngredient() {

		int[] aux = new int[10];
		try {

			// Acquire a witch permit
			try {
				witchSemaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Two witches can't read the gifts list at the same time
			ingredientsLock.lock();

			try {
				aux = ingredients.clone();
				for (int i = 0; i < 10; i++) {
					ingredients[i] = 0;
				}
			} catch (Exception exception) {
				// The ingredients list is empty

			}

		} finally {

			ingredientsLock.unlock();
			witchSemaphore.release();

		}

		return aux;
	}

	private void createIngredient(int demonNumber, int noIngredients) {

		try {
			Random rand = new Random();
			// Witches can't get ingredients while a demon creates an ingredient
			ingredientsLock.lock();

			for (int i = 0; i < noIngredients; i++) {
				int ingredient = rand.nextInt(9) + 1;
				ingredients[ingredient]++;
				System.out.println("Demon " + demonNumber + " created ingredient " + ingredient);
			}
		} finally {

			ingredientsLock.unlock();

		}

	}

	private void askDemonsForPosition() {

		try {

			// No new demon can be added while asking the current ones
			// Demons can't move while reporting their position
			// Witches can't get potions while coven is asking demons for position
			covenLock.lock();
			demonsListLock.lock();
			ingredientsLock.lock();

			for (Demon demon : demons) {
				demon.reportPosition();
			}

		} finally {

			demonsListLock.unlock();
			covenLock.unlock();
			ingredientsLock.unlock();

		}

	}

	public void run() {

		while (true) {

			try {
				// Try asking existing demons for their position
				// (the coven is also a working thread)
				askDemonsForPosition();

				// Sleeping for 3000 milliseconds
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void retireDemon(Demon demon) {

		try {

			// Modifying the demons' list and coven matrix
			demonsListLock.lock();
			covenLock.lock();

			demons.remove(demon);

			int X = demon.getX();
			int Y = demon.getY();

			coven[X][Y] = false;

			System.out.println("Demon " + demon.getNumber() + " retired from coven " + number);

		} finally {

			demonsListLock.unlock();
			covenLock.unlock();

		}
	}

	public void killDemon() {
		try {
			zombieSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Demon killDemon;
		Random rand = new Random();
		killDemon = demons.get(rand.nextInt(nrExistingDemons()));
		retireDemon(killDemon);

		try {
			ingredientsLock.lock();
			// Remove ingredients from coven
			for (int i = 0; i < 10; i++) {
				ingredients[i] = 0;
			}
		} finally {
			ingredientsLock.unlock();
			zombieSemaphore.release();
		}

	}

}
