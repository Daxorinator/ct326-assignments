package main;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * TrackList is a View in the application which displays the tracks of the selected Album.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.2.0
 * @since 2023-11-6
 * @see https://github.com/Daxorinator/ct326-assignments/tree/assignment4
 */
public class TrackList extends JPanel {
	// Parent is used to call back to the application controller for event handling
	public ApplicationController parent;

	/**
	 * Creates a new TrackList view.
	 * Fetches the tracks from the selected Album and processes them for display on the screen.
	 * The track list is a JTable contained in a JScrollPane so near-infinitely long track lists can be loaded.
	 *
	 * @param parent The JFrame parent where the view will be displayed
	 * @param album The album from which the tracks should be taken
	 */
	public TrackList(ApplicationController parent, Album album) {
		this.parent = parent;

		setLayout(new BorderLayout(10, 10));

		// Extract the tracks from the Album as a list of Strings
		List<String> tracks = album.getTracks();
		// Form an array of string arrays, to be consumed by the JTable
		String[][] tracksSplit = new String[tracks.size()][3];
		// Form an array of strings representing the column headings
		String[] columns = {"Track No.", "Track Name", "Track Length"};

		// Populate the array of String arrays by decomposing each String into its constituents (number, title, length)
		// Java could learn a thing or two from Python. enumerate() would make this much cleaner.
		for (int i = 0; i < tracks.size(); i++) {
			tracksSplit[i] = tracks.get(i).split(",");
		}

		// Create and populate the JTable based on the decomposed tracks list and the column headers
		JTable songList = new JTable(tracksSplit, columns);
		// Create a JScrollPane from the songList, necessary to display headers and allow scrolling.
		JScrollPane scrollPane = new JScrollPane(songList);
		add(scrollPane, BorderLayout.NORTH);

		// Create the backButton and register its callback function
		JButton backButton = new JButton("Back");
		backButton.addActionListener(
				e -> parent.backButtonHandler()
		);

		add(backButton, BorderLayout.SOUTH);
	}
}
