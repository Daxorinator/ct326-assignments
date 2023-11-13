package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Represents a Bank.
 * A bank has a Map which stores and maps account numbers to Account objects.
 * It is instantiated as a new HashMap when a Bank is constructed.
 * It also has a LinkedBlockingQueue of Transaction objects which is used by RandomTransactionGenerator and TransactionProcessor
 * to add new transactions and remove transactions for processing. Similarly, it is instantiated as an empty LinkedBlockingQueue when a Bank is constructed.
 *
 * Bank has no constructor as all of its class variables have default values.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-11-13
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
public class Bank {
	private Map<Integer, Account> bankAccounts = new HashMap<>();
	private LinkedBlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();

	/**
	 * Adds a new Account to the Bank's Account map.
	 * @param acc An Account to add to the Bank
	 */
	public void addAccount(Account acc) {
		bankAccounts.put(acc.getAccountNumber(), acc);
	}

	/**
	 * Searches a Bank for an Account with the specified account number
	 * @param id The account number to retrieve an Account object for
	 * @return Returns the Account associated with the account number specified in the ID parameter, or null if no such account number exists
	 */
	public Account getAccount(int id) {
		return bankAccounts.get(id);
	}

	/**
	 * Submits a Transaction object to the Bank's transactionQueue for processing by a TransactionProcessor
	 * @param trans The Transaction object to be submitted
	 */
	public synchronized void submitTransaction(Transaction trans) {
		transactionQueue.add(trans);
	}

	/**
	 * Fetches and removes the first transaction in the Bank's transactionQueue for processing
	 * @return Returns the Transaction object at the top of the transactionQueue or null if one is not available after 5 seconds
	 * @throws InterruptedException Thrown if the calling thread is interrupted while waiting for a transaction
	 */
	public Transaction nextTransaction() throws InterruptedException {
		return transactionQueue.poll(5, TimeUnit.SECONDS);
	}

	/**
	 * Prints the state of an Account object including Account Number and Balance
	 * @param acc The Account object to print the status of
	 */
	public void printDetails(Account acc) {
		System.out.println(acc);
	}

	/**
	 * Get the list of Account Numbers associated with a Bank object
	 * @return Returns a List\<Integer\> of Account numbers
	 */
	public List<Integer> getAccountNumbers() {
		// Fetch a Set<Integer> of all keys (account numbers),
		// convert it to a sequential Stream and then accumulate the stream into a List<Integer>
		return bankAccounts.keySet().stream().toList();
	}
}
