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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerTest {
    Player testPlayer1;
    Player testPlayer2;

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

    @Test
    void testGetId_ExpectTrue() {
        assertEquals(testPlayer2.getId(), "testId");
    }

    @Test
    void testMakeId_ExpectTrue() {
        String newId = Player.makeId();
        assertNotNull(newId);
    }

    @Test
    void testIdUniqueness_ExpectTrue() {
        assertNotEquals(Player.makeId(), Player.makeId());
    }

    @Test
    void testGetUsername_ExpectTrue() {
        assertEquals(testPlayer1.getUsername(), "CrashOverride");
        assertEquals(testPlayer2.getUsername(), "Summit1G");
    }

    @Test
    void testSetUsername_ExpectTrue() {
		Player tempPlayer = new Player("GM-Hikaru", Country.UNITED_STATES);
        String newUsername = "Hikaru";
        tempPlayer.setUsername(newUsername);
        assertEquals(tempPlayer.getUsername(), newUsername);
    }

    @Test
    void testGetCountry_ExpectTrue() {
        assertEquals(testPlayer1.getCountry(), Country.IRELAND);
        assertEquals(testPlayer2.getCountry(), Country.CANADA);
    }

    @Test
    void testSetCountry_ExpectTrue() {
        Country newCountry = Country.NEW_ZEALAND;
        testPlayer1.setCountry(newCountry);
        assertEquals(testPlayer1.getCountry(), newCountry);
    }

    @Test
    void testGetJoinDate_ExpectTrue() {
        assertEquals(testPlayer2.getJoinDate(), LocalDate.now());
    }

    @Test
    void testGetAchievements_ExpectTrue() {
        assertNotNull(testPlayer1.getAchievements());
        assertTrue(testPlayer1.getAchievements() instanceof List);
    }

    @Test
    void testAddAchievement_ExpectTrue() {
        Achievement newAchievement = new Achievement("Dev Achievement", "Did you cheat?");
        testPlayer2.addAchievement(newAchievement);

    }

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
			    readPlayers.add((Player) ois.readObject());
		    }

	    } catch (Exception e) {
		    fail("Failed to read players from players.ser: " + e.getMessage());
	    }

	    for (int i = 0; i < 5; i++) {
			if (!playerList.get(i).equals(readPlayers.get(i))) {
				fail("Serialized Player and Deserialized Player did not match!");
			}
	    }
    }

    @AfterAll
    void teardown() {
        testPlayer1 = null;
        testPlayer2 = null;
    }
}
