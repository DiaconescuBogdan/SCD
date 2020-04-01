package Task3;

import java.util.Random;

public class Zombie extends Thread {
	private int number;
	private Coven covens[];

	public Zombie(int number, Coven[] covens) {

		this.number = number;
		this.covens = covens;
	}

	public void run() {

		while (true) {
			Random rand = new Random();
			int covenNumber = rand.nextInt(covens.length);
			int demonKillNumber = rand.nextInt(6) + 5;

			if (covens[covenNumber].nrExistingDemons() >= 10) {
				System.out.println("A fost atacat cuibul " + covenNumber);
				for (int i = 0; i < demonKillNumber; i++) {
					covens[covenNumber].killDemon();

				}
			}
			int millis = rand.nextInt(501) + 500;
			try {

				Thread.sleep(millis);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}
