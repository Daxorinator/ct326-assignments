import org.joda.money.Money;

import java.time.LocalDateTime;

/**
 * Expense represents an employee expense claim.
 * Expenses must have a date, description, category, and an amount.
 * Dates are represented by LocalDateTime
 * Descriptions are represented by String
 * Categroy is represented by an enum Category containing various categories
 * Amount is a Money object representing the monetary value of the expense claim
 * Approved is a boolean representing the approval state of the expense claim.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-04
 * @see https://github.com/Daxorinator/ct326-assignments
 */
public class Expense {
	private LocalDateTime date;
	private String description;
	private Category category;
	private Money amount;
	private boolean approved = false;

	/**
	 * Creates an Expense object with a specific date
	 * @param date The LocalDateTime associated with the Expense
	 * @param description A plaintext description of the expense claim
	 * @param category The type of Expense being filed
	 * @param amount The monetary value of the claim as a Money object
	 */
	public Expense(LocalDateTime date, String description, Category category, Money amount) {
		this.date = date;
		this.description = description;
		this.category = category;
		this.amount = amount;
	}

	/**
	 * Creates an Expense object with the current date
	 * @param description The LocalDateTime associated with the Expense
	 * @param category The type of Expense being filed
	 * @param amount The monetary value of the claim as a Money object
	 */
	public Expense(String description, Category category, Money amount) {
		this.date = LocalDateTime.now();
		this.description = description;
		this.category = category;
		this.amount = amount;
	}

	/**
	 * An Enum representing the different types of Expense that can be filed
	 * Types are limited to Travel and Food, Supplies, Entertainment, Equipment and Other.
	 */
	public enum Category {
		TRAVEL_AND_FOOD,
		SUPPLIES,
		ENTERTAINMENT,
		EQUIPMENT,
		OTHER
	}

	/**
	 * @return Returns a String in the format: "YYYY-MM-DD: DESCRIPTION - CATEGORY - VALUE"
	 */
	@Override
	public String toString() {
		return String.format("%tF: %s - %s - %s",date,description,category,amount);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public Money getAmount() {
		return amount;
	}

	public boolean isApproved() {
		return approved;
	}

	public void approve() {
		approved = true;
	}
}
