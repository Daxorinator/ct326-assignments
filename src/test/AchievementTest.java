import main.Achievement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AchievementTest {
    Achievement testAchievement1;
    Achievement testAchievement2;

    @BeforeAll
    void setup() {
        testAchievement1 = new Achievement("Test Achievement", "Test Achievement Description", LocalDate.parse("2023/10/17"));
        testAchievement2 = new Achievement("Test Achievement", "Test Achievement Description");
    }

    @Test
    void testGetAchievementName_ExpectTrue() {
        assertEquals(testAchievement1.getAchievementName(), "Test Achievement");
    }

    @Test
    void testGetDescription_ExpectTrue() {
        assertEquals(testAchievement1.getDescription(), "Test Achievement Description");
    }

    @Test
    void testGetDateOfAward_ExpectTrue() {
        assertEquals(testAchievement1.getDateOfAward(), LocalDate.parse("2023/10/17"));
    }

    @Test
    void testSetDateOfAward_ExpectTrue() {
        LocalDate newDate = LocalDate.parse("01/01/1970");
        testAchievement1.setDateOfAward(newDate);

        assertEquals(testAchievement1.getDateOfAward(), newDate);
    }

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
