package main;

/**
 * The application entrypoint.
 * Spawns a new ApplicationController.
 *
 * This application follows the Model-View-Controller paradigm.
 * The ApplicationController serves as the controller and dictates how and when data is displayed.
 * The Views for the application are contained in the MusicLibrary and TrackList classes. These classes represent the current state of the model classes, on the screen.
 * The Models for the application are contained in the Album and LibraryLoader classes. These classes hold the application data and are modified by the controller.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.2.0
 * @since 2023-11-6
 * @see https://github.com/Daxorinator/ct326-assignments/tree/assignment4
 */
public class Main {
	public static void main(String[] args) {
		ApplicationController app = new ApplicationController();
	}
}
