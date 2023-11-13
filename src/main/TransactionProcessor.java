package main;

import org.joda.money.Money;

import java.math.RoundingMode;

/**
 * A runnable class which processes Transaction objects in the Bank's transactionQueue.
 * A TransactionProcessor object must be instantiated with a Bank object to pull transactions from, and a name to identify the object.
 *
 * While running, pulls the next Transaction from the top of the Bank's transactionQueue.
 * The Transaction is checked for a poison-pill which if detected will cause the Runnable to finish and the Thread to exit.
 * Otherwise, the Transaction is sorted by value, if the Transaction value is negative then a withdrawal is initiated for the associated Account of the Transaction.
 * If the Transaction value is positive, then a deposit is initiated for the associated Account of the Transaction.
 *
 * If the Account does not have a sufficient balance to process a withdrawal, the withdrawal is aborted and an error is printed, then the Transaction is discarded.
 *
 * If the Thread takes more than 5 seconds to acquire a new Transaction to process, then the Thread exits.
 *
 * Between Transactions, the Thread sleeps for a random amount of time between 0 and 1 second.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-11-13
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
public class TransactionProcessor implements Runnable {
	private final Bank bank;
	private final String name;

	private int depositCount = 0;
	private int withdrawalCount = 0;

	/**
	 * Instantiates a new TransactionProcessor with an associated Bank object and a name
	 * @param bank The Bank object to pull Transactions from
	 * @param name The name of the Thread as a String
	 */
	public TransactionProcessor(Bank bank, String name) {
		this.bank = bank;
		this.name = name;
	}

	@Override
	public void run() {
		System.out.printf("Thread %s is starting\n", name);
		// If the thread gets interrupted then exit - interruptions in this case only come from shutting down the thread
		while (!Thread.currentThread().isInterrupted()) {
			try {
				// Pull the next transaction, if one is not found within 5 seconds, bail.
				Transaction transaction = bank.nextTransaction();

				// The transaction will be null if bank.nextTransaction() takes more than 5 seconds
				// There is no such account number as Account 0, if it is encountered, then all transactions have been processed. Bail.
				if (transaction == null || transaction.getAccountNumber() == 0) {
					break;
				}

				float transactionValue = transaction.getAmount();

				// Get the Account Number and associated Account
				int accountNum = transaction.getAccountNumber();
				Account acc = bank.getAccount(accountNum);

				// Negative values represent a withdrawal
				if (transactionValue < 0) {
					try {
						// Print a processing notification, make the withdrawal. Withdrawals must be for a positive amount, so multiply by -1 to convert to positive.
						System.out.printf("%s is processing %s\n", name, transaction);
						// Euro only has 2 decimal places, so a RoundingMode must be specified
						/* Turns out debugging multithreaded programs is hard. Unchecked exceptions (like the one thrown if you forget to round),
						   are discarded by the Thread, and cause the thread to bail without notifying the calling thread.
						   Even the debugger will happily neglect to show you the Exception unless you step very carefully... */
						acc.makeWithdrawal(Money.of(acc.currency, transactionValue * -1, RoundingMode.HALF_UP));
						withdrawalCount++;
					} catch (InsufficientFundsException e) {
						// If an account has insufficient funds for a withdrawal, discard the Transaction and print an error message.
						System.err.printf("Account No. %d has insufficient funds!\n", accountNum);
					}
				} else {
					// Otherwise the value must be greater than or equal to zero which is a deposit.
					System.out.printf("%s is processing %s\n", name, transaction);
					// Make the deposit - a RoundingMode is required here too
					acc.makeDeposit(Money.of(acc.currency, transactionValue, RoundingMode.HALF_UP));
					depositCount++;
				}
				// Sleep for a random amount of milliseconds between 0 and 1000
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				// If the thread was interrupted, e.g. because it was shutdown, print a message and break out of the loop
				System.err.printf("Thread %s was interrupted!\n", name);
				break;
			}
		}
		// Print the final statement, outlining how many Transactions were processed by the TransactionProcessor
		System.out.printf("Thread %s exiting - Processed %d deposits and %d withdrawals\n", name, depositCount, withdrawalCount);
	}
}
