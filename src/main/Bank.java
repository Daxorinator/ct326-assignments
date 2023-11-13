package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Bank {
	private Map<Integer, Account> bankAccounts = new HashMap<>();
	private LinkedBlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();

	// private Boolean isTransactionQueueAvailable = false;

	public void addAccount(Account acc) {
		bankAccounts.put(acc.getAccountNumber(), acc);
	}

	public Account getAccount(int id) {
		return bankAccounts.get(id);
	}

	public synchronized void submitTransaction(Transaction trans) {
		transactionQueue.add(trans);
	}

	public Transaction nextTransaction() throws InterruptedException {
		return transactionQueue.poll(5, TimeUnit.SECONDS);
	}

	public void printDetails(Account acc) {
		System.out.println(acc);
	}

	public List<Integer> getAccountNumbers() {
		return bankAccounts.keySet().stream().toList();
	}
}
