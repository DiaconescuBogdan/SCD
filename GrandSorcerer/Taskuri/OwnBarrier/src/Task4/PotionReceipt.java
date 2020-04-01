package Task4;

import java.util.ArrayList;
import java.util.Random;

public class PotionReceipt {
	private int number;
	private ArrayList<Integer> necessaryIngredients = new ArrayList<>();
	private int time;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public PotionReceipt(int number) {
		this.number = number;
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			this.necessaryIngredients.add(rand.nextInt(10) + 1);
			System.out.println("Ingredient: " + necessaryIngredients.get(i));
		}

		this.time = rand.nextInt(30) + 1;
		System.out.println("Time: " + time);

	}

	public boolean checkReceipt(int[] availableIngredients) {

		for (Integer ingredient : necessaryIngredients) {
			if (availableIngredients[ingredient - 1] == 0) {
				return false;
			}

		}
		return true;
	}
}