package Task4;

public class MainClass {

	public static void main(String[] args) {

		// Creating the potion transfer queue
		PotionTransfer potionQueue = new PotionTransfer();

		// Creating Grand Sorcerer
		GrandSorcerer grandSorcerer = new GrandSorcerer(potionQueue);

		// Creating Grand Sorcerer's Circle
		GrandSorcererCircle circle = new GrandSorcererCircle(potionQueue);

		// Starting coven creation
		circle.createCovens();

		// Grand Sorcerer starts receiving potions
		grandSorcerer.start();

	}

}
