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
		assertEquals(testedName, name);
	}

	@Test
	void testGetTestCentreAddress_ExpectTrue() {
		String testedAddress = testCentre.getTestCentreAddress();
		assertEquals(testedAddress, address);
	}

	@Test
	void testGetBookingDateTime_ExpectTrue() {
		NctBookingSlotWebservice testService = new NctBookingSlotWebservice() {
			@Override
			public LocalDateTime getBookingDateTime(NctTestCentre testCentre) {
				return testDate;
			}
		};

		LocalDateTime testedDate = testService.getBookingDateTime(testCentre);
		assertEquals(testedDate, testDate);
	}

	@AfterAll
	public void teardown() {
		name = null;
		address = null;
		testCentre = null;
		testDate = null;
	}
}
