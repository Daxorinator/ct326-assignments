package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;
import java.util.UUID;

public class NctBooking {
	private String vrn;
	private NctTestCentre testCentre;
	private LocalDateTime testTime;
	private String bookingId;

	public NctBooking(String plate, NctTestCentre testCentre, LocalDateTime testTime) {
		this.vrn = plate;
		this.testCentre = testCentre;
		this.testTime = testTime;
		this.bookingId = makeBookingId();
	}

	public NctBooking(String plate, NctTestCentre testCentre) {
		this.vrn = plate;
		this.testCentre = testCentre;
		this.bookingId = makeBookingId();
		this.testTime = testCentre.getBookingDateTime();
	}

	private String makeBookingId() {
		return UUID.randomUUID().toString();
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
