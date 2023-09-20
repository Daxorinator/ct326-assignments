package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;
import java.util.UUID;

public class NctBooking {
	private String vrn;
	private NctTestCentre testCentre;
	private LocalDateTime testTime;
	private String bookingId;

	public NctBooking(String plate, NctTestCentre testCentre, LocalDateTime testTime) throws InvalidDateTimeException {
		this.vrn = plate;
		this.testCentre = testCentre;
		this.bookingId = makeBookingId();

		if (testTime.isBefore(LocalDateTime.now())) {
			throw new InvalidDateTimeException("Booking time cannot be in the past");
		} else {
			this.testTime = testTime;
		}
	}

	public NctBooking(String plate, NctTestCentre testCentre) {
		this.vrn = plate;
		this.testCentre = testCentre;
		this.bookingId = makeBookingId();
		this.testTime = testCentre.getBookingDateTime();
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();

		output.append("Booking ID Number: ").append(this.getBookingId()).append("\n");
		output.append("Registration Number: ").append(this.getVrn()).append("\n");
		output.append("Test Centre: ").append(this.getTestCentre().getTestCentreName()).append("\n");
		output.append("Test Centre Address: ").append(this.getTestCentre().getTestCentreAddress()).append("\n");
		output.append("Test Date & Time: ").append(this.getTestTime().toString().replace('T', ' ')).append("\n");

        return output.toString();
    }

	private String makeBookingId() {
		return UUID.randomUUID().toString().split("-")[0];
	}

	public String getBookingId() {
		return bookingId;
	}

	public LocalDateTime getTestTime() {
		return testTime;
	}

	public NctTestCentre getTestCentre() {
		return testCentre;
	}

	public String getVrn() {
		return vrn;
	}

	public void setVrn(String newVrn) {
		this.vrn = newVrn;
	}
}
