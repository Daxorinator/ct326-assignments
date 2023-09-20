/**
 * The NctBooking class represents a booking for the NCT.
 * This class is responsible for instantiating NCT Bookings
 * with a valid vehicle registration number, a valid test centre,
 * and a valid test date and time. A test date and time can be fetched
 * automatically if none is specified. This class also includes a generator
 * for unique booking identifiers, as well as a custom toString() method to
 * display the booking details in a nicer format.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-09-15
 * @see https://github.com/Daxorinator/ct326-assignments
 */

package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;
import java.util.UUID;

public class NctBooking {
	private String vrn;
	private final NctTestCentre testCentre;
	private final LocalDateTime testTime;
	private final String bookingId;

	/**
	 * This method instantiates an NctBooking object with a specific LocalDateTime
	 * @param plate This is the vehicle registration plate as a String
	 * @param testCentre This is the NctTestCentre which should be associated with the NctBooking
	 * @param testTime This is the LocalDateTime of the booking, representing the date and time the booking will take place
	 * @throws InvalidDateTimeException This exception is thrown if the LocalDateTime testTime is invalid (e.g. in the past)
	 */
	public NctBooking(String plate, NctTestCentre testCentre, LocalDateTime testTime) throws InvalidDateTimeException {
		this.vrn = plate;
		this.testCentre = testCentre;
		this.bookingId = makeBookingId();

		// NCT bookings cannot be made for dates in the past,
		// so the earliest possible booking time should be this instant.
		// This allows for walk-in bookings.
		if (testTime.isBefore(LocalDateTime.now())) {
			throw new InvalidDateTimeException("Booking time cannot be in the past");
		} else {
			this.testTime = testTime;
		}
	}

	/**
	 * This method instantiates an NctBooking object without a LocalDateTime
	 * and instead fetches it from the NctBookingSlotWebservice for the associated NctTestCentre
	 * @param plate This is the vehicle registration plate as a String
	 * @param testCentre This is the NctTestCentre which should be associated with the NctBooking
	 */
	public NctBooking(String plate, NctTestCentre testCentre) {
		this.vrn = plate;
		this.testCentre = testCentre;
		this.bookingId = makeBookingId();
		this.testTime = testCentre.makeBookingDateTime();
	}

	/**
	 * This is a bit of a cursed toString() implementation,
	 * it returns a nicely formatted string showing the NctBooking details.
	 * The code here is about as readable as using plain string concatenation.
	 * It does the job nonetheless - a good candidate for refactoring.
	 * @return String Returns NctBooking information as a formatted String
	 */
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();

		// Very annoying that StringBuilder has no equivalent for C++ "std::endl",
		// Manually appending a newline is the only way, so about as
		// effective as string concatenation when it comes to string building.
		output.append("Booking ID Number: ").append(this.getBookingId()).append("\n");
		output.append("Registration Number: ").append(this.getVrn()).append("\n");
		output.append("Test Centre: ").append(this.getTestCentre().getTestCentreName()).append("\n");
		output.append("Test Centre Address: ").append(this.getTestCentre().getTestCentreAddress()).append("\n");
		output.append("Test Date & Time: ").append(this.getTestTime().toString().replace('T', ' ')).append("\n");

        return output.toString();
    }

	/**
	 * This function generates a UUID to be used as the Booking ID.
	 * It then converts it to a string, splits it on the "-" character,
	 * and takes the first portion (8 characters) to be used as a unique ID.
	 * UUIDs are nice - there's no built-in mechanism for short IDs in Java,
	 * Apache Commons has one but overkill for this use case.
	 * Easiest to just convert to a string and grab the first 8 chars.
	 * @return String Returns an 8 character unique ID
	 */
	private String makeBookingId() {
		return UUID.randomUUID().toString().split("-")[0];
	}

	/**
	 * @return Returns the unique ID of a booking
	 */
	public String getBookingId() {
		return bookingId;
	}

	/**
	 * @return Returns a LocalDateTime for when the NctBooking will take place
	 */
	public LocalDateTime getTestTime() {
		return testTime;
	}

	/**
	 * @return Returns the NctTestCentre associated with the NctBooking
	 */
	public NctTestCentre getTestCentre() {
		return testCentre;
	}

	/**
	 * @return Returns the Vehicle Registration Number of the NctBooking
	 */
	public String getVrn() {
		return vrn;
	}

	/**
	 * Used to replace the Vehicle Registration Number of a booking.
	 * @param newVrn The new VRN to be set for the NctBooking
	 */
	public void setVrn(String newVrn) {
		this.vrn = newVrn;
	}
}
