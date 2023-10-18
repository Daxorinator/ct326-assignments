package test;

import main.Achievement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Houses unit tests for the Achievement class (built with JUnit)
 * Unit tests are ran with an initial setup ran before the entire test suite which instantiates test Achievements
 * After unit tests are ran, a teardown destroys the objects used in testing.
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-10-16
 * @see <a href="https://github.com/Daxorinator/ct326-assignments">https://github.com/Daxorinator/ct326-assignments</a>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AchievementTest {
    Achievement testAchievement1;
    Achievement testAchievement2;

    /**
     * Runs before each test suite run.
     * Instantiates a pair of test Achievements using both Achievement constructors.
     */
    @BeforeAll
    void setup() {
        testAchievement1 = new Achievement("Test Achievement", "Test Achievement Description", LocalDate.parse("2023-10-17"));
        testAchievement2 = new Achievement("Test Achievement", "Test Achievement Description");
    }

    /**
     * Tests Achievement.getAchievementName() by checking if the fetched name matches the expected value
     */
    @Test
    void testGetAchievementName_ExpectTrue() {
        assertEquals(testAchievement1.getAchievementName(), "Test Achievement");
    }

    /**
     * Tests Achievement.getDescription() by checking if the fetched description matches the expected value
     */
    @Test
    void testGetDescription_ExpectTrue() {
        assertEquals(testAchievement1.getDescription(), "Test Achievement Description");
    }

    /**
     * Tests Achievement.getDateOfAward() by checking if the fetched LocalDate matches the expected value
     */
    @Test
    void testGetDateOfAward_ExpectTrue() {
        assertEquals(testAchievement1.getDateOfAward(), LocalDate.parse("2023-10-17"));
    }

    /**
     * Tests Achievement.setDateOfAward() by creating a test date,
     * calling setDateOfAward() on testAchievement1 with the test date,
     * then checking if getDateOfAward() returns the set date.
     */
    @Test
    void testSetDateOfAward_ExpectTrue() {
        LocalDate newDate = LocalDate.parse("1970-01-01");
        testAchievement1.setDateOfAward(newDate);

        assertEquals(testAchievement1.getDateOfAward(), newDate);
    }

    /**
     * Tests if not specifying a date in the Player constructor, results in the current date being used
     */
    @Test
    void testNoDateInConstructor_ExpectTrue() {
        assertEquals(testAchievement2.getDateOfAward(), LocalDate.now());
    }

    @AfterAll
    void teardown() {
        testAchievement1 = null;
        testAchievement2 = null;
    }
}
