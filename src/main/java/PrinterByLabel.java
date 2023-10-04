import java.util.ArrayList;

/**
 * PrinterByLabel implements ExpensePrinter.
 * The expenses are printed according to their expense category.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-04
 * @see https://github.com/Daxorinator/ct326-assignments
 */
public class PrinterByLabel implements ExpensePrinter {

	/**
	 * Prints the passed expenseList, sorted by expense category.
	 * @param expenseList An ArrayList of Expenses which will printed to the console
	 */
	public void print(ArrayList<Expense> expenseList) {
		for (Expense.Category cat : Expense.Category.values()) {
			System.out.println(cat);
			for (Expense exp : expenseList) {
				if (exp.getCategory() == cat) {
					System.out.println(exp);
				}
			}
			System.out.println();
		}
	}
}
