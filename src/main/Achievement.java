package main;

import java.time.LocalDate;

/**
 * Represents an Achievement.
 * Achievements must have a name, description, and dateOfAward, however the date can be empty if the Achievement hasn't been earned yet.
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-16
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
public class Achievement {
	private final String achievementName;
	private final String description;
	private LocalDate dateOfAward;

	/**
	 * Instantiates a new Achievement object.
	 * Assumes that the dateOfAward is the current date.
	 * @param achievementName The name of the new Achievement
	 * @param description The description of the new Achievement
	 */
	public Achievement(String achievementName, String description) {
		this.achievementName = achievementName;
		this.description = description;
		this.dateOfAward = LocalDate.now();
	}

	/**
	 * Instantiates a new Achievement object with a specific date.
	 * @param achievementName The name of the new Achievement
	 * @param description The description of the new Achievement
	 * @param dateOfAward The LocalDate when the achievement was earned
	 */
	public Achievement(String achievementName, String description, LocalDate dateOfAward) {
		this.achievementName = achievementName;
		this.description = description;
		this.dateOfAward = dateOfAward;
	}

	/**
	 * Get the name of an Achievement
	 * @return Returns the name of the Achievement
	 */
	public String getAchievementName() {
		return achievementName;
	}

	/**
	 * Get the description of an Achievement
	 * @return Returns the description of the Achievement
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the date an Achievement was earned
	 * @return Returns the date the achievement was earned
	 */
	public LocalDate getDateOfAward() {
		return dateOfAward;
	}

	/**
	 * Set the date an achievement was earned
	 * @param dateOfAward The date the achievement was earned
	 */
	public void setDateOfAward(LocalDate dateOfAward) {
		this.dateOfAward = dateOfAward;
	}

	/**
	 * Checks the equality of the Achievement with a given input Object.
	 * Equality is checked based on the achievementName, description, and dateOfAward fields.
	 * @param o The object to compare against
	 * @return Returns true if the Achievements have the same content, otherwise returns false
	 */
	@Override
	public boolean equals(Object o) {
		// Checks for equality by reference
		if (o == this) return true;
		// Checks if the object is an Achievement
		if (!(o instanceof Achievement)) return false;

		// Cast the object to an Achievement
		Achievement a = (Achievement) o;

		// Compare the achievementName, description and dateOfAward fields
		if ((this.achievementName.equals(a.getAchievementName()))
		 && (this.description.equals(a.getDescription()))
		 && (this.dateOfAward.equals(a.getDateOfAward()))
		) {
			return true;
		} else {
			return false;
		}
	}
}
