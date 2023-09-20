package online.override.ct326.assignment1.test;

import static org.junit.jupiter.api.Assertions.*;

import online.override.ct326.assignment1.main.InvalidDateTimeException;
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
		testTime = LocalDateTime.of(2024, Month.SEPTEMBER, 18, 0, 0);

		nctWebService = new NctBookingSlotWebservice() {
			@Override
			public LocalDateTime getBookingDateTime(NctTestCentre testCentre) {
				return testTime;
			}
		};

		testCentre = new NctTestCentre("Galway", "Doughiska");
		testCentre.setNctWebService(nctWebService);

		try {
			booking = new NctBooking(vrn, testCentre, testTime);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetBookingTestCentre_ExpectTrue() {
		NctTestCentre testedTestCentre = booking.getTestCentre();
		assertEquals(testedTestCentre, testCentre, "Returned TestCentre did not match input TestCentre");
	}

	@Test
	void testGetBookingVrn_ExpectTrue() {
		String testedVrn = booking.getVrn();
		assertEquals(testedVrn, vrn, "Returned Vrn did not match input Vrn");
	}

	@Test
	void testSetBookingVrn_ExpectTrue() {
		// Comment about data racing
		NctBooking testBooking = new NctBooking("12LH1316", testCentre);
		String newVrn = "131D12345";
		testBooking.setVrn(newVrn);
		assertEquals(newVrn, testBooking.getVrn(), "Returned Vrn did not match set Vrn");
	}

	@Test
	void testCreateBookingWithoutTime_ExpectTrue() {
		NctBooking testBooking = new NctBooking(vrn, testCentre);
		assertNotNull(testBooking, "Returned booking was NULL");
		assertEquals(testBooking.getTestTime(), testTime, "Returned LocalDateTime did not match NctWebService Stub LocalDateTime");
	}

	@Test
	void testCreateBookingInPast_ExpectFalse() {
		assertThrows(
			InvalidDateTimeException.class,
			() -> {
				new NctBooking(vrn, testCentre, LocalDateTime.of(
					1980, Month.JANUARY, 1, 0, 0
				));
			},
			"A booking was created with an invalid LocalDateTime in the past"
		);
	}

	@Test
	void testToString() {
		System.out.println(booking);
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
