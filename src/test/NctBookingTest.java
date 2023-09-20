/**
 * The NctBookingTest test class is responsible for testing each aspect of
 * the NctBooking class. Notably, this test suite covers the following:
 * <ul>
 *     <li>A booking has a registration number, test centre, and a valid time</li>
 *     <li>That the NctTestCentre of an NctBooking can be queried</li>
 *     <li>That the registration number of an NctBooking can be queried</li>
 *     <li>That it is possible to edit the registration number of an NctBooking</li>
 *     <li>That a booking instantiated without a date can fetch one automatically</li>
 *     <li>That it is not possible to create an NctBooking with a time in the past</li>
 *     <li>That the custom toString() method for the NctBooking returns a string</li>
 * </ul>
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-09-15
 * @see https://github.com/Daxorinator/ct326-assignments
 */

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

// Per-class lifecycle for tests, as the same NctBooking object is
// used repeatedly for a number of tests, except where stated otherwise.
@TestInstance (TestInstance.Lifecycle.PER_CLASS)
public class NctBookingTest {
	private String vrn;
	private LocalDateTime testTime;
	private NctBookingSlotWebservice nctWebService;
	private NctTestCentre testCentre;
	private NctBooking booking;

	/**
	 * setup() instantiates the above class members, which are used for test output checking,
	 * creating objects, and testing functionality such as LocalDateTime validity.
	 */
	@BeforeAll
	void setup() {
		vrn = "12LH1316";
		testTime = LocalDateTime.of(2024, Month.SEPTEMBER, 18, 0, 0);

		nctWebService = new NctBookingSlotWebservice() {
			@Override
			public LocalDateTime makeBookingDateTime(NctTestCentre testCentre) {
				return testTime;
			}
		};

		testCentre = new NctTestCentre("Galway", "Doughiska");
		testCentre.setNctWebService(nctWebService);

		// Adding this try-catch removes exception handling from
		// each individual test, by ensuring each test receives a
		// valid NctBooking to begin with.
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

	/**
	 * This test requires its own individual booking,
	 * as when the class member "booking" is used it causes data racing,
	 * due to all tests running in parallel by default.
	 * This would cause this specific test to pass or fail randomly.
	 */
	@Test
	void testSetBookingVrn_ExpectTrue() {
		NctBooking testBooking = new NctBooking("12LH1316", testCentre);
		String newVrn = "131D12345";
		testBooking.setVrn(newVrn);
		assertEquals(newVrn, testBooking.getVrn(), "Returned Vrn did not match set Vrn");
	}

	/**
	 * A null check is added here to ensure that the stub for generating test times
	 * is functional, or in the case of a full implementation, that the generator is
	 * correctly generating test times, as not having a time would be the only reason
	 * for this test to fail.
	 */
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
					// Doesn't get much more 'in the past' than Epoch.
					1970, Month.JANUARY, 1, 0, 0
				));
			},
			"A booking was created with an invalid LocalDateTime in the past"
		);
	}


	/**
	 * This mini test simply ensures the custom toString()
	 * is actually outputting a String.
	 */
	@Test
	void testToString() {
		System.out.println(booking);
    }

	/**
	 * Although no garbage collection is needed here,
	 * it's no harm to clean up after the tests.
	 */
	@AfterAll
	void teardown() {
		vrn = null;
		testTime = null;
		nctWebService = null;
		testCentre = null;
		booking = null;
	}
}
