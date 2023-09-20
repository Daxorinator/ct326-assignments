package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;
import java.time.Month;

public class Main {
	/**
	 * This main() function serves as an example for how to
	 * make use of NctBooking and NctTestCentre. It also
	 * provides a sample NctBookingSlotWebservice which returns
	 * a fixed time, to demonstrate how the NctTestCentre interacts with
	 * the NctBookingSlotWebservice interface.
	 * @param args No arguments are needed
	 */
	public static void main(String[] args) {
		NctTestCentre testCentre = new NctTestCentre("Galway", "Doughiska");

		NctBookingSlotWebservice bookingService = new NctBookingSlotWebservice() {
			@Override
			public LocalDateTime makeBookingDateTime(NctTestCentre testCentre) {
				return LocalDateTime.of(2024, Month.JANUARY, 12, 12, 30);
			}
		};

		testCentre.setNctWebService(bookingService);

		NctBooking booking = new NctBooking("12LH1316", testCentre);

		System.out.println(booking);
	}
}
