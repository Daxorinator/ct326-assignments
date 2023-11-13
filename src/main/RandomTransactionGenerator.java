package main;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTransactionGenerator implements Runnable {
	private final String name;
	private final Bank bank;

	public RandomTransactionGenerator(Bank bank, String name) {
		this.name = name;
		this.bank = bank;
	}

	@Override
	public void run() {
		System.out.printf("Thread %s is starting\n", name);
		while (!Thread.currentThread().isInterrupted()) {
			int accIndex = ThreadLocalRandom.current().nextInt(1, bank.getAccountNumbers().size());
			int accNum = bank.getAccountNumbers().get(accIndex);
			float amount = ThreadLocalRandom.current().nextFloat(-10000.00f, 10000.00f);
			Transaction transaction = new Transaction(accNum, amount);
			bank.submitTransaction(transaction);
			try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				System.err.printf("Thread %s was interrupted!\n", name);
				break;
			}
		}
		bank.submitTransaction(new Transaction(0, 0));
		System.out.printf("Thread %s exiting\n", name);
	}
}
