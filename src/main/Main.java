package main;

import org.joda.money.Money;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * The main class is responsible for declaring and instantiating a new Bank object, and adding accounts to it.
 * By default, this class declares and instantiates two TransactionProcessor threads and one RandomTransactionGenerator thread.
 * The TransactionProcessor threads are executed by a FixedThreadPool and the RandomTransactionGenerator is executed by a SingleThreadExecutor.
 * To test the TransactionProcessor, the RandomTransactionGenerator is allowed to run for 10 seconds before its threadpool is forcefully shutdown.
 * The TransactionProcessor threads are then allowed to exit gracefully, which gives them time to process any remaining transactions present on the Bank object.
 * Once the TransactionProcessor threadpool has shutdown, the states of each account in the Bank are printed.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-11-13
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
public class Main {
	public static void main(String[] args) {
		Bank bank = new Bank();
		try {
			Account acc1 = new Account(1, Money.parse("EUR 123.45"));
			Account acc2 = new Account(2, Money.parse("EUR 526.68"));
			Account acc3 = new Account(3, Money.parse("EUR 692367.11"));

			bank.addAccount(acc1);
			bank.addAccount(acc2);
			bank.addAccount(acc3);
		} catch (NegativeBalanceException e) {
			// This should never happen given the above values, so if it does, I'll want this anyway...
			e.printStackTrace();
		}

		// Create a pair of ExecutorServices, one for a single thread, and one for a pool of 2 threads
		ExecutorService random_es = Executors.newSingleThreadExecutor();
		ExecutorService processor_es = Executors.newFixedThreadPool(2);

		// Create and submit a single RandomTransactionGenerator runnable and 2 TransactionProcessor runnables
		// The names are used in the console output to indicate which thread processed a transaction
		random_es.submit(new RandomTransactionGenerator(bank, "GenThread1"));
		processor_es.submit(new TransactionProcessor(bank, "ProcThread1"));
		processor_es.submit(new TransactionProcessor(bank, "ProcThread2"));

		try {
			// Wait 10 seconds to allow some transactions to generate
			Thread.sleep(10000);
			// Forcefully shutdown the RandomTransactionGenerator,
			// and gracefully shutdown the TransactionProcessor thread pool
			// allowing it time to finish processing the remaining transactions
			random_es.shutdownNow();
			processor_es.shutdown();
			processor_es.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// if something interrupted the main thread you're kind of screwed anyway so...
			System.err.println("Achievement Unlocked! - How did we get here...?");
		}

		// Print out the state of the Account objects in the Bank object
		// Account has a toString() method which prints the account state
		for (int accNum : bank.getAccountNumbers()) {
			Account acc = bank.getAccount(accNum);
			System.out.println(acc);
		}
	}
}