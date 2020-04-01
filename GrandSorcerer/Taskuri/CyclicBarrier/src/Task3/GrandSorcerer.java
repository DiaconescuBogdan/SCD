package Task3;

public class GrandSorcerer extends Thread {

	private PotionTransfer potionQueue;

	public GrandSorcerer(PotionTransfer potionQueue) {
		this.potionQueue = potionQueue;

	}

	public void run() {

		while (true) {

			int potion = potionQueue.receivePotion();
			System.out.println("Grand Sorcerer received potion " + potion);

		}
	}

}
