package main;

import java.io.*;
import java.time.LocalDate;

public class Achievement{
	private String achievementName;
	private String description;
	private LocalDate dateOfAward;

	public Achievement(String achievementName, String description) {
		this.achievementName = achievementName;
		this.description = description;
		this.dateOfAward = LocalDate.now();
	}

	public Achievement(String achievementName, String description, LocalDate dateOfAward) {
		this.achievementName = achievementName;
		this.description = description;
		this.dateOfAward = dateOfAward;
	}

	public String getAchievementName() {
		return achievementName;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDateOfAward() {
		return dateOfAward;
	}

	public void setDateOfAward(LocalDate dateOfAward) {
		this.dateOfAward = dateOfAward;
	}
}
