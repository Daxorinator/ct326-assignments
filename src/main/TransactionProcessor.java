package main;

import org.joda.money.Money;

import java.math.RoundingMode;

public class TransactionProcessor implements Runnable {
	private final Bank bank;
	private final String name;

	private int depositCount = 0;
	private int withdrawalCount = 0;

	public TransactionProcessor(Bank bank, String name) {
		this.bank = bank;
		this.name = name;
	}

	@Override
	public void run() {
		System.out.printf("Thread %s is starting\n", name);
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Transaction transaction = bank.nextTransaction();

				if (transaction == null || transaction.getAccountNumber() == 0) {
					break;
				}

				float transactionValue = transaction.getAmount();

				int accountNum = transaction.getAccountNumber();
				Account acc = bank.getAccount(accountNum);

				if (transactionValue < 0) {
					try {
						System.out.printf("%s is processing %s\n", name, transaction);
						acc.makeWithdrawal(Money.of(acc.currency, transactionValue * -1, RoundingMode.HALF_UP));
						withdrawalCount++;
					} catch (InsufficientFundsException e) {
						System.err.printf("Account No. %d has insufficient funds!\n", accountNum);
					}
				} else {
					System.out.printf("%s is processing %s\n", name, transaction);
					acc.makeDeposit(Money.of(acc.currency, transactionValue, RoundingMode.HALF_UP));
					depositCount++;
				}
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				System.err.printf("Thread %s was interrupted!\n", name);
				break;
			}
		}
		System.out.printf("Thread %s exiting - Processed %d deposits and %d withdrawals\n", name, depositCount, withdrawalCount);
	}
}
