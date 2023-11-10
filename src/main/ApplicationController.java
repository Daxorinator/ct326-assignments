package main;

import javax.swing.*;

/**
 * The ApplicationController is the orchestrator of the application.
 * It is responsible for the initialising the user interface, and loading the initial model data from disk.
 * It then initialises the primary view of the application, the MusicLibrary.
 * Once the application has been initialised, the ApplicationController becomes responsible for handling events throughout the application,
 * via the defined handler functions.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.2.0
 * @since 2023-11-6
 * @see https://github.com/Daxorinator/ct326-assignments/tree/assignment4
 */
public class ApplicationController {
	JFrame frame = new JFrame("My Music Library");
	LibraryLoader loader;
	MusicLibrary library;

	/**
	 * Creates a new ApplicationController object.
	 * Spawns a new window and initialises the MusicLibrary by
	 * first loading data from disk by instantiating the LibraryLoader class.
	 * The MusicLibrary is then displayed on screen.
	 */
	public ApplicationController() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 720);

		loader = new LibraryLoader(this);
		library = new MusicLibrary(this, loader);

		frame.setContentPane(library);

		frame.setVisible(true);
	}

	/**
	 * A handler for clicks on the Album buttons in the MusicLibrary view.
	 * Sets the primary window's content to a new TrackList view based on the album parameter.
	 * @param album The Album which should be used to populate the TrackList view
	 */
	public void albumButtonHandler(Album album) {
		frame.setContentPane(new TrackList(this, album));
		frame.revalidate();
		frame.repaint();
	};

	/**
	 * A handler for clicks on the Back button in the TrackList view.
	 * Sets the primary window's content to the MusicLibrary view.
	 */
	public void backButtonHandler() {
		frame.setContentPane(library);
		frame.revalidate();
		frame.repaint();
	};
}
