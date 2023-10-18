package main;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a Player
 * Players must have an ID, a username, a Country, a joinDate, and a list of Achievements.
 * The list of Achievements is automatically instantiated as an empty {@code ArrayList<Achievement>} upon Player instantiation.
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-16
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
public class Player implements Serializable {
	private final String id;
	private String username;
	private Country country;
	private final LocalDate joinDate;
	private transient List<Achievement> achievements;

	/**
	 * Instantiates a new Player.
	 * Automatically generates a unique ID for the player.
	 * Assumes that the player joinDate is the current date.
	 * @param username The new Player's username
	 * @param country The new Player's country
	 */
	public Player(String username, Country country) {
		this.id = makeId();
		this.username = username;
		this.country = country;
		this.joinDate = LocalDate.now();
		this.achievements = new ArrayList<>();
	}

	/**
	 * Instantiates a new Player.
	 * Requires an ID be explicitly passed.
	 * Requires a joinDate be explicitly passed.
	 * @param id The new Player's ID
	 * @param username The new Player's Username
	 * @param country The new Player's Country
	 * @param joinDate The new Player's joinDate
	 */
	public Player(String id, String username, Country country, LocalDate joinDate) {
		this.id = id;
		this.username = username;
		this.joinDate = joinDate;
		this.country = country;
		this.achievements = new ArrayList<>();
	}

	/**
	 * Get the Player's ID.
	 * @return Returns the Player's ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Generate a new unique Player ID.
	 * Generated based on UUIDs.
	 * @return Returns a new unique Player ID
	 */
	public static String makeId() {
		return UUID.randomUUID().toString().split("-")[0];
	}

	/**
	 * Get the Player's Username
	 * @return Returns the Player's Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the Player's Username
	 * @param username The Player's new Username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the Player's Country
	 * @return Returns the Player's Country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Set the Player's Country
	 * @param country The Player's new Country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Get the Player's joinDate
	 * @return Returns the Player's joinDate
	 */
	public LocalDate getJoinDate() {
		return joinDate;
	}

	/**
	 * Get a List of the Player's Achievements
	 * @return Returns a list of the Player's Achievements
	 */
	public List<Achievement> getAchievements() {
		return achievements;
	}

	/**
	 * Award the Player a new Achievement
	 * The achievement is added to the Player's achievements List
	 * @param achievement The achievement to be awarded to the Player
	 */
	public void addAchievement(Achievement achievement) {
		this.achievements.add(achievement);
	}

	/**
	 * Serializes the Player for writing to an ObjectOutputStream.
	 * Uses the default serialization for the Player fields.
	 * Achievements are written to "achievements.csv" via BufferedWriter.
	 * Achievements are saved one per line, prepended with the Player ID, in the format:
	 * "Player.id,Achievement.achievementName,Achievement.description,Achievement.dateOfAward"
	 * @param outputStream The ObjectOutputStream where the serialized Player data should be written
	 * @throws IOException Throws an IOException if an error occurs writing to the ObjectOutputStream or if achievements.csv cannot be opened/written to
	 */
	@Serial
	private void writeObject(ObjectOutputStream outputStream) throws IOException {
		// Call the default serializer to serialize Player class variables
		outputStream.defaultWriteObject();

		// Open a new BufferedWriter via a FileWriter to achievement.csv
		BufferedWriter writer = new BufferedWriter(new FileWriter("achievements.csv", true));

		// For each Achievement the player has, write it to achievement.csv in Comma-Separated Value format.
		// Achievements are saved one per line, prepended with the Player ID, in the format:
	    // "Player.id,Achievement.achievementName,Achievement.description,Achievement.dateOfAward"
		for (Achievement a : this.achievements) {
			writer.write(String.format("%s,%s,%s,%s\n", this.id, a.getAchievementName(), a.getDescription(), a.getDateOfAward()));
		}

		// Flush the buffer to disk to prevent data loss
		writer.flush();
		// Close the BufferedWriter and FileWriter to prevent unnecessary file locking or dangling pointers
		writer.close();
	}

	/**
	 * Reads and Deserializes Player data from an ObjectInputStream, instantiating a new Player.
	 * Uses the default deserializer to deserialize the Player.
	 * The new Player is given an empty {@code ArrayList<Achievement>} as their achievements list.
	 * A Scanner opens achievements.csv via a FileInputStream and reads each line in the file.
	 * If the line contains the Player's ID, the achievement from that line is loaded and added to the achievements list.
	 * @param inputStream The ObjectInputStream where serialized Player data should be read from
	 * @throws IOException Throws an IOException if Player data cannot be read, or the Stream has no data, or achievements.csv could not be opened or read from
	 * @throws ClassNotFoundException Throws a ClassNotFoundException if there is no Class implemented for the object read from the Stream
	 */
	@Serial
	private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		// Call the default deserializer to deserialize the data to create the new Player
		inputStream.defaultReadObject();
		// Instantiate a blank ArrayList<Achievement> for the new Player
		this.achievements = new ArrayList<>();

		// Access achievements.csv using a Scanner via FileInputStream
		Scanner scanner = new Scanner(new FileInputStream("achievements.csv"));
		// Loop over all rows in achievements.csv
		while (scanner.hasNextLine()) {
			// Separate the comma-separated row into an array of Strings
			String[] row = scanner.nextLine().split(",");
			// Check if the Player ID at the start of the row matches the
			// deserialized Player's ID and if it does, add that achievement back to the deserialized Player
			if (this.id.equals(row[0])) {
				this.achievements.add(new Achievement(row[1], row[2], LocalDate.parse(row[3])));
			}
		}
	}

	/**
	 * Checks the equality of a Player with a given input Object.
	 * Equality is checked based on the Player ID, Username, Country, joinDate and Achievements fields.
	 * @param o The object to compare against
	 * @return Returns true if the Players have the same content, otherwise returns false
	 */
	@Override
	public boolean equals(Object o) {
		// Checks for equality by reference
		if (o == this) return true;
		// Checks if the object is a Player
		if (!(o instanceof Player)) return false;

		// Cast the object to a Player
		Player p = (Player) o;

		// Compare the ID, Username, Country, joinDate and Achievements fields
		if ((this.id.equals(p.getId()))
		 && (this.username.equals(p.getUsername()))
		 && (this.country == p.getCountry())
		 && (this.joinDate.equals(p.getJoinDate()))
		 && (this.achievements.equals(p.getAchievements()))
		) {
			return true;
		} else {
			return false;
		}
	}
}
