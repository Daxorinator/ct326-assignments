/**
 * The NctTestCentreTest test class is responsible for testing each aspect of
 * the NctTestCentre class. Notably, this test suite covers the following:
 * <ul>
 *     <li>That the name of an NctTestCentre can be queried</li>
 *     <li>That the address of an NctTestcentre can be queried</li>
 *     <li>That the NctBookingSlotWebservice correctly returns a valid LocalDateTime</li>
 * </ul>
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-09-15
 * @see https://github.com/Daxorinator/ct326-assignments
 */

package online.override.ct326.assignment1.test;

import static org.junit.jupiter.api.Assertions.*;

import online.override.ct326.assignment1.main.NctBookingSlotWebservice;
import online.override.ct326.assignment1.main.NctTestCentre;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.time.Month;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NctTestCentreTest {
	private String name;
	private String address;
	NctTestCentre testCentre;
	private LocalDateTime testDate;

	@BeforeAll
	public void setup() {
		name = "Galway Test Centre";
		address = "Merlin Commercial Park, Doughiska";
		testCentre = new NctTestCentre(name, address);
		testDate = LocalDateTime.of(2023, Month.SEPTEMBER, 18, 0, 0);
	}

	@Test
	public void testGetTestCentreName_ExpectTrue() {
		String testedName = testCentre.getTestCentreName();
		assertEquals(testedName, name, "Returned TestCentreName did not match input TestCentreName");
	}

	@Test
	void testGetTestCentreAddress_ExpectTrue() {
		String testedAddress = testCentre.getTestCentreAddress();
		assertEquals(testedAddress, address, "Returned TestCentreAddress did not match input TestCentreAddress");
	}

	@Test
	void testGetBookingDateTime_ExpectTrue() {
		NctBookingSlotWebservice testService = new NctBookingSlotWebservice() {
			@Override
			public LocalDateTime makeBookingDateTime(NctTestCentre testCentre) {
				return testDate;
			}
		};

		LocalDateTime testedDate = testService.makeBookingDateTime(testCentre);
		assertEquals(testedDate, testDate, "Returned LocalDateTime for testDate did not match input LocalDate time for testDate");
	}

	@AfterAll
	public void teardown() {
		name = null;
		address = null;
		testCentre = null;
		testDate = null;
	}
}
