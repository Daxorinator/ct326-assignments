import java.util.ArrayList;

/**
 * An interface with a single method `print()` which takes a list of expenses
 * as a parameter and prints the contained expenses to the console with a particular formatting.
 * The formatting is chosen by the specific implementation of ExpensePrinter.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-04
 * @see https://github.com/Daxorinator/ct326-assignments
 */
public interface ExpensePrinter {

	/**
	 * @param expenseList An ArrayList of Expenses which will printed to the console
	 */
	void print(ArrayList<Expense> expenseList);
}
