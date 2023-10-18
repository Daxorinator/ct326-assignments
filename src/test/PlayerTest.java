package test;

import main.Achievement;
import main.Country;
import main.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Houses unit tests for the Player class (built with JUnit)
 * Unit tests are ran with an initial setup ran before the entire test suite which instantiates test Players
 * After unit tests are ran, a teardown destroys the objects used in testing.
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-16
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerTest {
    Player testPlayer1;
    Player testPlayer2;

	/**
	 * Runs before each test suite run.
	 * Instantiates a pair of test players using both Player constructors
	 * Deletes any existing players.ser and achievements.csv files from previous runs, which may interfere with the tests.
	 */
    @BeforeAll
    void setup() {
        testPlayer1 = new Player("CrashOverride", Country.IRELAND);
        testPlayer2 = new Player("testId", "Summit1G", Country.CANADA, LocalDate.parse("2023-10-17"));

        File playerFile = new File("players.ser");
        File achievementFile = new File("achievements.csv");

        try {
            Files.deleteIfExists(playerFile.toPath());
            Files.deleteIfExists(achievementFile.toPath());
        } catch (Exception e) {
            fail("Setup failed! Encountered an I/O Error deleting players.ser or achievements.csv");
        }
    }

	/**
	 * Tests Player.getId() by checking if the fetched ID matches the expected value
	 */
    @Test
    void testGetId_ExpectTrue() {
        assertEquals(testPlayer2.getId(), "testId");
    }

	/**
	 * Tests Player.makeId() by generating a new ID and ensuring it is not null
	 */
    @Test
    void testMakeId_ExpectTrue() {
        String newId = Player.makeId();
        assertNotNull(newId);
    }

	/**
	 * Tests Player.makeId() generates unique IDs by comparing two freshly generated IDs
	 */
    @Test
    void testIdUniqueness_ExpectTrue() {
        assertNotEquals(Player.makeId(), Player.makeId());
    }

	/**
	 * Tests Player.getUsername() by checking if the fetched username matches the expected value
	 */
    @Test
    void testGetUsername_ExpectTrue() {
        assertEquals(testPlayer1.getUsername(), "CrashOverride");
        assertEquals(testPlayer2.getUsername(), "Summit1G");
    }

	/**
	 * Tests Player.setUsername() by constructing a temporary Player object,
	 * then changing its username to a new username, and asserting if the new username is fetched
	 */
    @Test
    void testSetUsername_ExpectTrue() {
		// A temp player is used because JUnit tests run in a random order unless explicitly stated otherwise
	    // This behaviour can cause tests like this to pass or fail randomly due to previous tests
	    // modifying testPlayer1 and testPlayer2
		Player tempPlayer = new Player("GM-Hikaru", Country.UNITED_STATES);
        String newUsername = "Hikaru";
        tempPlayer.setUsername(newUsername);
        assertEquals(tempPlayer.getUsername(), newUsername);
    }

	/**
	 * Tests Player.getCountry() by checking if the fetched Country matches the expected Country
	 */
    @Test
    void testGetCountry_ExpectTrue() {
        assertEquals(testPlayer1.getCountry(), Country.IRELAND);
        assertEquals(testPlayer2.getCountry(), Country.CANADA);
    }

	/**
	 * Tests Player.setCountry() by setting a new country for TestPlayer1 and asserting if the new country is fetched
	 */
    @Test
    void testSetCountry_ExpectTrue() {
        Country newCountry = Country.NEW_ZEALAND;
        testPlayer1.setCountry(newCountry);
        assertEquals(testPlayer1.getCountry(), newCountry);
    }

	/**
	 * Tests Player.getJoinDate() by checking if the fetched LocalDate matches the expected value
	 */
    @Test
    void testGetJoinDate_ExpectTrue() {
        assertEquals(testPlayer2.getJoinDate(), LocalDate.parse("2023-10-17"));
    }

	/**
	 * Tests Player.getAchievements() by checking if the fetched {@code List<Achievement>} is
	 * not null and is an instance of a class implementing List
	 */
    @Test
    void testGetAchievements_ExpectTrue() {
        assertNotNull(testPlayer1.getAchievements());
        assertTrue(testPlayer1.getAchievements() instanceof List);
    }

	/**
	 * Tests Player.addAchievement() by creating a test Achievement and adding it to a Player
	 * then fetching the {@code List<Achievement>} and checking it contains the test Achievement
	 */
    @Test
    void testAddAchievement_ExpectTrue() {
        Achievement newAchievement = new Achievement("Dev Achievement", "Did you cheat?");
        testPlayer2.addAchievement(newAchievement);
		assertTrue(testPlayer2.getAchievements().contains(newAchievement));
    }

	/**
	 * Tests Player.writeObject() and Player.readObject() by creating five test Players,
	 * adding them to an {@code ArrayList<Player>}, then creating five test Achievements,
	 * and adding each of the five achievements to each of the five players.
	 * <br>
	 * All five players are then serialized and written to "players.ser".
	 * All five players are then deserialized and reconstructed as new objects from the data in "players.ser".
	 * <br>
	 * The five original players are then compared with the five deserialized players.
	 * If any of the five original players do not have equality with their deserialized counterparts, the test fails.
	 */
    @Test
    void testSerializable() {
	    ArrayList<Player> playerList = new ArrayList<>();
	    playerList.add(new Player("Rubix", Country.IRELAND));
	    playerList.add(new Player("Druidoss", Country.POLAND));
	    playerList.add(new Player("Fritguy", Country.FIJI));
	    playerList.add(new Player("CrashOverride", Country.COSTA_RICA));
	    playerList.add(new Player("Daave", Country.UNITED_KINGDOM));

	    ArrayList<Achievement> achievementList = new ArrayList<>();
	    achievementList.add(new Achievement("How did we get here?", "Execute some unexpected code!"));
	    achievementList.add(new Achievement("Arbalistic!", "That's a pretty big crossbow..."));
	    achievementList.add(new Achievement("Baconator", "Sir this is a Wendy's."));
	    achievementList.add(new Achievement("Pixel Perfect", "Squeeze through the cracks"));
	    achievementList.add(new Achievement("Time Adventure", "Finish the campaign"));

	    for (Player p : playerList) {
		    for (Achievement a : achievementList) {
			    p.addAchievement(a);
		    }
	    }

	    try {
		    FileOutputStream fos = new FileOutputStream("players.ser");
		    ObjectOutputStream oos = new ObjectOutputStream(fos);

		    for (Player p : playerList) {
			    oos.writeObject(p);
		    }

			// Flush the stream and close it to prevent data loss and dangling pointers / file accessors
		    oos.flush();
		    oos.close();
	    } catch (Exception e) {
		    fail("Failed to save players to players.ser: " + e.getMessage());
	    }

	    ArrayList<Player> readPlayers = new ArrayList<>();
	    try {
		    FileInputStream fis = new FileInputStream("players.ser");
		    ObjectInputStream ois = new ObjectInputStream(fis);

		    for (int i = 0; i < 5; i++) {
				// Read in each of the five Players, casting the object data to the Player type
			    readPlayers.add((Player) ois.readObject());
		    }
	    } catch (Exception e) {
		    fail("Failed to read players from players.ser: " + e.getMessage());
	    }

		// Check that each of the five original players has equality with their deserialized counterparts
	    for (int i = 0; i < 5; i++) {
			assertEquals(playerList.get(i), readPlayers.get(i), "Serialized Player and Deserialized Player did not match!");
	    }
    }

    @AfterAll
    void teardown() {
        testPlayer1 = null;
        testPlayer2 = null;
    }
}
