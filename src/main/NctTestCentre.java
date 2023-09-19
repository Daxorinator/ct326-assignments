package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;

public class NctTestCentre {
	private final String testCentreName;
	private final String testCentreAddress;
	private NctBookingSlotWebservice nctWebService;

	public NctTestCentre(String name, String address) {
		this.testCentreName = name;
		this.testCentreAddress = address;
	}

	public String getTestCentreName() {
		return testCentreName;
	}

	public String getTestCentreAddress() {
		return testCentreAddress;
	}

	public void setNctWebService(NctBookingSlotWebservice service) {
		this.nctWebService = service;
	}

	public LocalDateTime getBookingDateTime() {
		return nctWebService.getBookingDateTime(this);
	}
}
