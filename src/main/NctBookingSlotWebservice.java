package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;

public interface NctBookingSlotWebservice {
	LocalDateTime getBookingDateTime(NctTestCentre testCentre);
}
