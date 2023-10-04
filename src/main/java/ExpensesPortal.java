import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * ExpensesPortal maintains a list of expenses and provides methods for submitting new expenses.
 * It also contains printExpenses() which will print the system's recorded expenses to a given printer.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-04
 * @see https://github.com/Daxorinator/ct326-assignments
 */
public class ExpensesPortal {
	private ArrayList<Expense> expenseList = new ArrayList<>();

	/**
	 * @param expenseList An Arraylist of Expenses to be totalled
	 * @return Returns a Money object representing the total monetary value of the expenses in the system.
	 */
	public static Money totalExpenses(ArrayList<Expense> expenseList) {
		Money total = Money.parse("EUR 0.00");

		for (Expense e : expenseList) {
			if (e.getAmount().getCurrencyUnit() == CurrencyUnit.EUR) {
				total = total.plus(e.getAmount());
			} else {
				total = total.plus(e.getAmount().convertedTo(CurrencyUnit.EUR, new BigDecimal(1.00), RoundingMode.UNNECESSARY));
			}
		}

		return total;
	}

	/**
	 * Prints all expenses to a given printer
	 * @param printer The ExpensePrinter which will be used for printing
	 */
	public void printExpenses(ExpensePrinter printer) {
		printer.print(expenseList);
	}

	/**
	 * Adds an expense to the ExpensesPortal
	 * @param expense The expense object to be added
	 */
	public void addExpense(Expense expense) {
		expenseList.add(expense);
	}

	/**
	 * Used to get all expenses from the system
	 * @return Returns an ArrayList of Expenses contained in the system
	 */
	public ArrayList<Expense> getExpenseList() {
		return expenseList;
	}

	public static void main(String[] args) {
		ExpensesPortal expensePortal = new ExpensesPortal();

		Expense expense1 = new Expense("Flight to Glasgow", Expense.Category.TRAVEL_AND_FOOD, Money.parse("EUR 52.00"));
		Expense expense2 = new Expense("Dell 17-inch Monitor", Expense.Category.EQUIPMENT, Money.parse("EUR 100.00"));
		Expense expense3 = new Expense("Java for Dummies Second Edition", Expense.Category.OTHER, Money.parse("EUR 15.00"));
		Expense expense4 = new Expense("Oversized Teddy Bear", Expense.Category.SUPPLIES, Money.parse("EUR 25.00"));
		Expense expense5 = new Expense("Undersized 3D Printer", Expense.Category.EQUIPMENT, Money.parse("EUR 125.00"));
		Expense expense6 = new Expense("Coke Zero 24-Pack", Expense.Category.ENTERTAINMENT, Money.parse("EUR 10.00"));

		expensePortal.addExpense(expense1);
		expensePortal.addExpense(expense2);
		expensePortal.addExpense(expense3);
		expensePortal.addExpense(expense4);
		expensePortal.addExpense(expense5);
		expensePortal.addExpense(expense6);

		expense1.approve();
		expense2.approve();
		expense5.approve();

		expensePortal.printExpenses(
				expenseList -> {
					for (Expense expense : expensePortal.getExpenseList()) {
						System.out.println(expense);
					}
				}
		);

		expensePortal.printExpenses(
			new ExpensePrinter() {
				@Override
				public void print(ArrayList<Expense> expenseList) {
					System.out.println(String.format(
							"There are %d expenses in the system totalling to a value of %s.",
							expenseList.size(),
							ExpensesPortal.totalExpenses(expenseList)
					));
				}
			}
		);

		expensePortal.printExpenses(new PrinterByLabel());
	}
}
