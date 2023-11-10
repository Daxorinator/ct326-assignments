package main;

import java.awt.*;
import javax.swing.*;

/**
 * MusicLibrary is a View in the application which displays the albums stored in resource/music_library.txt on the disk.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.2.0
 * @since 2023-11-6
 * @see https://github.com/Daxorinator/ct326-assignments/tree/assignment4
 */
public class MusicLibrary extends JPanel {
	// Parent is used to call back to the application controller for event handling
	public ApplicationController parent;

	/**
	 * Creates a new MusicLibrary view.
	 * Fetches all Albums stored in resource/music_library.txt from disk via a LibraryLoader.
	 * Albums are displayed with their cover art and name in a GridLayout.
	 *
	 * @param parent The JFrame parent where the view will be displayed
	 * @param loader The LibraryLoader which has loaded the Albums from disk
	 */
	public MusicLibrary(ApplicationController parent, LibraryLoader loader) {
		this.parent = parent;

		setLayout( new GridLayout(0, 3, 10, 10) );

		// Iterate over all loaded Albums and create buttons for them
		// The buttons contain the album title and scaled album cover
		for (Album album : loader.getAlbums()) {
			JButton albumButton = new JButton(album.getTitle(), album.getCover());
			albumButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			albumButton.setHorizontalTextPosition(SwingConstants.CENTER);
			albumButton.addActionListener(
					e -> parent.albumButtonHandler(album)
			);
			add(albumButton);
		}
	}
}
