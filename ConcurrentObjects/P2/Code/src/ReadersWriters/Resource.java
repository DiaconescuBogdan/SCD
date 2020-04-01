package ReadersWriters;

import java.util.concurrent.Semaphore;

public class Resource {

	private int readCount = 0; // number of processes reading resource
	private Semaphore resourceAccess = new Semaphore(1); // control acces to resource
	private Semaphore readCountAccess = new Semaphore(1); // control acces to reader count
	private Semaphore serviceQueue = new Semaphore(1); // control acces to the queue for read or write

	public void write(int writerNumber) {

		// Obtaining access to the resource
		try {
			serviceQueue.acquire(); // gain acces to service queue
			resourceAccess.acquire(); // gain acces to resource
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		serviceQueue.release(); // allow other process to acces service queue

		// Writing into the resource
		try {
			System.out.println("Writer " + writerNumber + " is accessing the resource");
			Thread.sleep(1000);
			System.out.println("Writer " + writerNumber + " stopped accessing the resource");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		resourceAccess.release(); // release exclusive acces to resource

	}

	public void read(int readerNumber) {

		// Obtaining access to the queue and the readCount variable
		try {
			serviceQueue.acquire(); // gain acces to service queue
			readCountAccess.acquire(); // gain acces to reader count
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// First reader obtains access to the resource
		// so that writers are blocked
		if (readCount == 0) {
			try {
				resourceAccess.acquire(); // prevent writer process to acces resources
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Reader starts reading

		readCount++; // increment reader counter

		serviceQueue.release(); // allow other process to acces service queue
		readCountAccess.release(); // allow other process to acces reader count

		// Reading the resource
		try {
			System.out.println("Reader " + readerNumber + " is accessing the resource");
			Thread.sleep(1000);
			System.out.println("Reader " + readerNumber + " stopped accessing the resource");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		// Obtaining access to modify readCount
		try {
			readCountAccess.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Reader stops reading
		readCount--; // decrement reader count

		// Releasing access if the current reader is the last reader

		if (readCount == 0) {
			resourceAccess.release(); // leave the control of resource, allow writer process
		}

		readCountAccess.release(); // allow other process to acces reader count

	}

}
