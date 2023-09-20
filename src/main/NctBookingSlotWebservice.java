package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;

public interface NctBookingSlotWebservice {
	/**
	 * makeBookingDateTime() makes more sense as a title for this function.
	 * getBookingDateTime() implies this function is a getter,
	 * however in a practical implementation, it is a generator.
	 * @param testCentre Represents the NctTestCentre for which availability should be queried
	 * @return Returns a LocalDateTime representing an available time slot for an NctBooking to take place
	 */
	LocalDateTime makeBookingDateTime(NctTestCentre testCentre);
}
