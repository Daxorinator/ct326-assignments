package main;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A runnable class which produces random Transaction objects and puts them in the Bank's transactionQueue.
 * A TransactionProcessor object must be instantiated with a Bank object to put transactions into, and a name to identify the object.
 *
 * While running, generates a random Transaction with a value between -10,000 Euro and 10,000 Euro, for a random Account attached to the Bank object.
 * Exits if the Thread is interrupted, which is usually only caused by calling shutdown() or shutdownNow().
 *
 * Between generating Transactions, the Thread sleeps for a random amount of time between 0 and 1 second.
 *
 * Once interrupted, the RandomTransactionGenerator inserts a "poison-pill". A Transaction object with an Account Number of 0, which is not valid.
 * TransactionProcessors will pick this up and interpret this to mean the Queue has been closed and no more transactions will be submitted, causing them to shutdown also.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-11-13
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
public class RandomTransactionGenerator implements Runnable {
	private final String name;
	private final Bank bank;

	/**
	 * Instantiates a new RandomTransactionGenerator
	 * @param bank The bank to insert new randomized Transactions into
	 * @param name The name of the RandomTransactionGenerator
	 */
	public RandomTransactionGenerator(Bank bank, String name) {
		this.name = name;
		this.bank = bank;
	}

	@Override
	public void run() {
		System.out.printf("Thread %s is starting\n", name);
		// If the thread gets interrupted then exit - interruptions in this case only come from shutting down the thread
		while (!Thread.currentThread().isInterrupted()) {
			// Use ThreadLocalRandom to generate a new integer between 0 and the number of Accounts there are (exclusive)
			int accIndex = ThreadLocalRandom.current().nextInt(0, bank.getAccountNumbers().size());
			// Turn that random index into an actual account number
			int accNum = bank.getAccountNumbers().get(accIndex);
			// Generate a random amount between -10,000 and 10,000
			float amount = ThreadLocalRandom.current().nextFloat(-10000.00f, 10000.00f);
			// Make a float with the random account number and amount
			Transaction transaction = new Transaction(accNum, amount);
			// Submit it for processing
			bank.submitTransaction(transaction);
			try {
				// Sleep for a random amount of time between 0 and 1000 milliseconds
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				// If the thread was interrupted e.g. due to shutdown, bail.
				System.err.printf("Thread %s was interrupted!\n", name);
				break;
			}
		}
		// Before exiting, insert a poison-pill Transaction with account number of 0
		// which is invalid, and causes the TransactionProcessors to exit as this is considered "end-of-stream"
		bank.submitTransaction(new Transaction(0, 0));
		System.out.printf("Thread %s exiting\n", name);
	}
}
