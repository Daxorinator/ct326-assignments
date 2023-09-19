package online.override.ct326.assignment1.test;

import static org.junit.jupiter.api.Assertions.*;

import online.override.ct326.assignment1.main.NctBooking;
import online.override.ct326.assignment1.main.NctBookingSlotWebservice;
import online.override.ct326.assignment1.main.NctTestCentre;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.time.Month;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
public class NctBookingTest {
	private String vrn;
	private LocalDateTime testTime;
	private NctBookingSlotWebservice nctWebService;
	private NctTestCentre testCentre;
	private NctBooking booking;

	@BeforeAll
	void setup() {
		vrn = "12LH1316";
		testTime = LocalDateTime.of(2023, Month.SEPTEMBER, 18, 0, 0);

		nctWebService = new NctBookingSlotWebservice() {
			@Override
			public LocalDateTime getBookingDateTime(NctTestCentre testCentre) {
				return testTime;
			}
		};

		testCentre = new NctTestCentre("Galway", "Doughiska");
		testCentre.setNctWebService(nctWebService);

		booking = new NctBooking(vrn, testCentre, testTime);
	}

	@Test
	void testGetBookingTestCentre_ExpectTrue() {
		NctTestCentre testedTestCentre = booking.getTestCentre();
		assertEquals(testedTestCentre, testCentre);
	}

	@Test
	void testGetBookingVrn_ExpectTrue() {
		String testedVrn = booking.getVrn();
		assertEquals(testedVrn, vrn);
	}

	@Test
	void testSetBookingVrn_ExpectTrue() {
		// Comment about data racing
		NctBooking testBooking = new NctBooking("12LH1316", testCentre);
		String newVrn = "131D12345";
		testBooking.setVrn(newVrn);
		assertEquals(newVrn, testBooking.getVrn());
	}

	@Test
	void testCreateBookingWithoutTime_ExpectTrue() {
		NctBooking testBooking = new NctBooking(vrn, testCentre);
		assertNotNull(testBooking);
		assertEquals(testBooking.getTestTime(), testTime);
	}

	@Test
	void testCreateBookingInPast_ExpectFalse() {
		fail("Not yet implemented");
	}

	@AfterAll
	void teardown() {
		vrn = null;
		testTime = null;
		nctWebService = null;
		testCentre = null;
		booking = null;
	}
}
