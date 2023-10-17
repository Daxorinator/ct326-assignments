import main.Achievement;
import main.Country;
import main.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerTest {
    Player testPlayer1;
    Player testPlayer2;

    @BeforeAll
    void setup() {
        testPlayer1 = new Player("CrashOverride", Country.IRELAND);
        testPlayer2 = new Player("testId", "Summit1G", Country.CANADA, LocalDate.parse("2023/10/17"));
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
        String newUsername = "Hikaru";
        testPlayer2.setUsername(newUsername);
        assertEquals(testPlayer2.getUsername(), newUsername);
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
        assertEquals(testPlayer1.getAchievements().getClass(), List.class);
    }

    @Test
    void testAddAchievement_ExpectTrue() {
        Achievement newAchievement = new Achievement("Dev Achievement", "Did you cheat?");
        testPlayer2.addAchievement(newAchievement);

    }

    @Test
    void testSerializeable() {

    }

    @AfterAll
    void teardown() {
        testPlayer1 = null;
        testPlayer2 = null;
    }
}
