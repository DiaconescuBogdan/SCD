package Task4;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Demon extends Thread {

	private int number;
	private int X;
	private int Y;
	private int ingredient = 1;
	private Coven coven;

	private int socialLevel = 0;
	private int hits = 0;
	public int hitsFlag = 0;
	private CyclicBarrier demonBarrier;

	public Demon(int number, int x, int y, Coven coven, CyclicBarrier demonBarrier) {
		this.number = number;
		this.X = x;
		this.Y = y;
		this.coven = coven;
		this.demonBarrier = demonBarrier;

	}

	public void increaseHits() {
		this.hits++;
		this.hitsFlag = 2;
		if (hits == 10) {
			hits = 0;
			socialLevel -= 100;

		}

	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void run() {
		while (true) {

			if (Math.abs(X - Y) <= 1) {

				System.out.println("Demon " + number + " is sleeping on position (" + X + "," + Y + ")");
				try {

					demonBarrier.await();

				} catch (InterruptedException | BrokenBarrierException e) {

					e.printStackTrace();
				}
			}

			coven.moveDemon(this);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (GrandSorcererCircle.demonRetireSemaphore.tryAcquire()) {
				coven.retireDemon(this);
				break;
			}
		}
	}

	public int getNumber() {
		return number;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public int getIngredient() {
		return ingredient;
	}

	public void changePosition(int newX, int newY) {

		X = newX;
		Y = newY;
	}

	public void stopWork() {

		Random rand = new Random();

		long milis = rand.nextInt(50) + 10;

		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void reportPosition() {

		System.out.println("Demon " + number + " is at (" + X + "," + Y + ") in coven " + coven.getNumber());
	}

	// function for increment demon's social level
	public void incrementSocialLevel() {

		if (coven.getCoven()[X][Y + 1]) {
			socialLevel += 100;
		} else if (coven.getCoven()[X][Y - 1]) {
			socialLevel += 100;
		} else if (coven.getCoven()[X + 1][Y]) {
			socialLevel += 100;
		} else if (coven.getCoven()[X - 1][Y]) {
			socialLevel += 100;
		}

		if (socialLevel >= 100 && ingredient < 10) {
			ingredient = (socialLevel / 100) + 1;
		}
	}

}
