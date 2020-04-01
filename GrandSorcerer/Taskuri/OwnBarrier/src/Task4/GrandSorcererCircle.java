package Task4;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class GrandSorcererCircle {

	public static int nrCovens;
	private Coven covens[];
	private DemonSpawner spawners[];
	public static volatile int nrTotalDemons = 1;
	private static ReentrantLock demonsCounterLock = new ReentrantLock();
	private Witch witches[];
	private PotionTransfer potionQueue;
	public static volatile Semaphore demonRetireSemaphore = new Semaphore(0);
	private DemonRetirement demonRetire = new DemonRetirement();

	private ArrayList<PotionReceipt> recipes = new ArrayList<>();
	private Zombie zombies[];

	public GrandSorcererCircle(PotionTransfer potionQueue) {
		this.potionQueue = potionQueue;
	}

	public static ReentrantLock getElvesCounterLock() {
		return demonsCounterLock;
	}

	public void createCovens() {

		Random rand = new Random();
		nrCovens = rand.nextInt(4) + 2;
		int nrWitches = rand.nextInt(10) + 8;
		int nrZombies = rand.nextInt(31) + 20;

		covens = new Coven[nrCovens];
		spawners = new DemonSpawner[nrCovens];
		witches = new Witch[nrWitches];
		zombies = new Zombie[nrZombies];

		System.out.println("There were created " + nrCovens + " covens");
		System.out.println("There were created " + nrWitches + " witches");

		for (int i = 0; i < nrCovens; ++i) {

			int N = rand.nextInt(500) + 100;
			covens[i] = new Coven(30, i + 1);
			spawners[i] = new DemonSpawner(covens[i]);
			System.out.println("Coven " + (i + 1) + " has N = " + N);
		}
		for (int i = 0; i < 20; i++) {
			PotionReceipt potionRecip = new PotionReceipt(i);
			recipes.add(potionRecip);
		}

		for (int i = 0; i < nrWitches; ++i) {
			witches[i] = new Witch(i + 1, covens, potionQueue, recipes);
		}

		for (int i = 0; i < nrZombies; ++i) {
			zombies[i] = new Zombie(i, covens);
		}

		for (int i = 0; i < nrCovens; ++i) {
			spawners[i].start();
			covens[i].start();
		}

		for (int i = 0; i < nrWitches; ++i) {
			witches[i].start();
		}

		for (int i = 0; i < nrZombies; ++i) {
			zombies[i].start();
		}

		demonRetire.start();

	}

}
