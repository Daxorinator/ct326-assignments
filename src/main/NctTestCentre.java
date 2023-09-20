/**
 * The NctTestCentre class represents an NCT Test Centre.
 * This class is responsible for instantiating valid NCT Test Centres,
 * with a test centre name and address. It also ensures that there is
 * an NctBookingSlotWebservice available for each NctTestCentre object,
 * so that valid booking dates and times may be fetched where one is not
 * specified in the NctBooking object at the time of instantiation.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.1.0
 * @since 2023-09-15
 * @see https://github.com/Daxorinator/ct326-assignments
 */

package online.override.ct326.assignment1.main;

import java.time.LocalDateTime;

public class NctTestCentre {

	/**
	 * 	Each NctTestCentre has an NctBookingSlotWebservice
	 * 	in this implementation, as a simple way to access the booking service
	 * 	via the NctBooking class for LocalDateTime generation.
	 * 	If it wasn't implemented as a stub, it could've been its own class
	 * 	and this wouldn't be necessary - the same NctBookingSlotWebservice could be
	 * 	used for all test centres, which would be more realistic for a web app.
	 */
	private final String testCentreName;
	private final String testCentreAddress;
	private NctBookingSlotWebservice nctWebService;

	/**
	 * This method instantiates an NctTestCentre with a specific name and address.
	 * @param name This is the name of the NctTestCentre as a String
	 * @param address This is the address of the NctTestCentre as a String
	 */
	public NctTestCentre(String name, String address) {
		this.testCentreName = name;
		this.testCentreAddress = address;
	}

	/**
	 * @return Returns the NctTestCentre name as a String
	 */
	public String getTestCentreName() {
		return testCentreName;
	}

	/**
	 * @return Returns the NctTestCentre address as a String
	 */
	public String getTestCentreAddress() {
		return testCentreAddress;
	}

	/**
	 * Used to set the NctBookingSlotWebservice of an NctTestCentre.
	 * @param service An NctBookingSlotWebservice to be associated with the NctTestCentre
	 */
	public void setNctWebService(NctBookingSlotWebservice service) {
		this.nctWebService = service;
	}

	/**
	 * makeBookingDateTime() makes more sense as a title for this function.
	 * getBookingDateTime() implies this function is a getter,
	 * however in a practical implementation, it is a generator.
	 * @return Returns a newly generated LocalDateTime representing the time an NctBooking will take place
	 */
	public LocalDateTime makeBookingDateTime() {
		return nctWebService.makeBookingDateTime(this);
	}
}
