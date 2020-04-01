package Task3;

import java.util.ArrayList;
import java.util.Random;

public class Witch extends Thread {
	private int number;
	private Coven covens[];
	private int[] availableIngredients = new int[10];
	private PotionTransfer potionQueue;
	private ArrayList<PotionReceipt> potionList = new ArrayList<>();

	public Witch(int number, Coven[] covens, PotionTransfer potionQueue, ArrayList<PotionReceipt> potionList) {

		this.number = number;
		this.covens = covens;
		this.potionQueue = potionQueue;
		this.potionList = potionList;
	}

	public void run() {

		while (true) {

			// Getting the ingredient from the coven
			int[] newIngredient = new int[10];
			newIngredient = getIngredientFromCoven();

			for (int i = 0; i < 10; i++) {
				availableIngredients[i] += newIngredient[i];
			}

			for (int i = 0; i < newIngredient.length; i++) {
				if (newIngredient[i] != 0) {
					System.out.println("Witch " + number + " received ingredient " + i);
				}

			}
			makePotion();

			// Sleeping between 10 and 30 milliseconds
			Random rand = new Random();
			long milis = rand.nextInt(30) + 10;
			try {
				Thread.sleep(milis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int[] getIngredientFromCoven() {

		// Choosing a random coven from the existing ones
		Random rand = new Random();
		int coven = rand.nextInt(GrandSorcererCircle.nrCovens) + 0;

		// Requesting an ingredient from the chosen coven
		return covens[coven].getIngredient();
	}

	public void makePotion() {

		for (PotionReceipt potion : potionList) {
			if (potion.checkReceipt(availableIngredients)) {
				System.out.println("Potion " + potion.getNumber() + " of witch " + number + " was created");
				potionQueue.givePotion(potion.getNumber());
				try {
					Thread.sleep(potion.getTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
