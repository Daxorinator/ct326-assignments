package main;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalDate;
import java.util.*;

public class Player implements Serializable {
	private String id;
	private String username;
	private Country country;
	private LocalDate joinDate;
	private List<Achievement> achievements;

	public Player(String username, Country country) {
		this.id = makeId();
		this.username = username;
		this.country = country;
		this.joinDate = LocalDate.now();
		this.achievements = new ArrayList<>();
	}

	public Player(String id, String username, Country country, LocalDate joinDate) {
		this.id = id;
		this.username = username;
		this.joinDate = joinDate;
		this.country = country;
		this.achievements = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public static String makeId() {
		return UUID.randomUUID().toString().split("-")[0];
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void addAchievement(Achievement achievement) {
		this.achievements.add(achievement);
	}

	@Serial
	private void writeObject(ObjectOutputStream outputStream) throws IOException {
		outputStream.writeObject(this.id);
		outputStream.writeObject(this.username);
		outputStream.writeObject(this.country);
		outputStream.writeObject(this.joinDate);

		BufferedWriter writer = new BufferedWriter(new FileWriter("achievements.csv"));
		for (Achievement a : this.achievements) {
			writer.write(String.format("%s,%s,%s, %s\n", this.id, a.getAchievementName(), a.getDescription(), a.getDateOfAward()));
		}
	}

	@Serial
	private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		this.id = (String) inputStream.readObject();
		this.username = (String) inputStream.readObject();
		this.country = (Country) inputStream.readObject();
		this.joinDate = (LocalDate) inputStream.readObject();
		this.achievements = new ArrayList<>();

		Scanner scanner = new Scanner(new FileInputStream("achievements.csv"));
		while (scanner.hasNextLine()) {
			String[] row = scanner.nextLine().split(",");
			if (this.id.equals(row[0])) {
				this.achievements.add(new Achievement(row[1], row[2], LocalDate.parse(row[3])));
			}
		}
	}
}
