package main;

import org.joda.money.Money;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *
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
			e.printStackTrace();
		}

		ExecutorService random_es = Executors.newSingleThreadExecutor();
		ExecutorService processor_es = Executors.newFixedThreadPool(2);

		random_es.submit(new RandomTransactionGenerator(bank, "GenThread1"));
		processor_es.submit(new TransactionProcessor(bank, "ProcThread1"));
		processor_es.submit(new TransactionProcessor(bank, "ProcThread2"));

		try {
			Thread.sleep(10000);
			random_es.shutdownNow();
			processor_es.shutdown();
			processor_es.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.err.println("Achievement Unlocked! - How did we get here...?");
		}

		for (int accNum : bank.getAccountNumbers()) {
			Account acc = bank.getAccount(accNum);
			System.out.println(acc);
		}

		// To-Do
		// Test if InsufficientFundsException is working in TransactionProcessor
		// JavaDoc + Comments
	}
}