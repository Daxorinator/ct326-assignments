package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * LibraryLoader is a pseudo-controller / pseudo-model class, which is responsible for loading the music library from disk.
 * Once the library is loaded, it parses it and fetches the album cover art and track list.
 * This data is then used to create a new Album object and save it to the albums ArrayList.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.2.0
 * @since 2023-11-6
 * @see https://github.com/Daxorinator/ct326-assignments/tree/assignment4
 */
public class LibraryLoader {
	private final ArrayList<Album> albums = new ArrayList<>();

	/**
	 * Creates a new LibraryLoader object
	 * The LibraryLoader uses a Scanner to open the music library located at "resource/music_library.txt"
	 * The file is then parsed line by line, and the albumCover path is used to read in the album cover image.
	 * The album track list is then loaded from album tracks list path and is saved as an ArrayList\<String\>
	 * The newly created album objects based on those files are stored in the albums ArrayList\<Album\>
	 *
	 * @param parent The parent JFrame of the LibraryLoader - only used to display error messages
	 */
	public LibraryLoader(ApplicationController parent) {
		try {
			Scanner albumFile = new Scanner(new FileReader("resource/music_library.txt"));

			while (albumFile.hasNextLine()) {
				String[] albumString = albumFile.nextLine().strip().split(",");

				BufferedImage albumCover = ImageIO.read(new File("resource/" + albumString[2]));

				Scanner albumTrackList = new Scanner(new FileReader("resource/" + albumString[3]));
				ArrayList<String> albumTracks = new ArrayList<>();
				while (albumTrackList.hasNextLine()) {
					albumTracks.add(albumTrackList.nextLine());
				}
				albumTrackList.close();

				albums.add(new Album(albumString[0], albumString[1], albumCover, albumTracks));
			}
			albumFile.close();
		} catch (IOException e) {
			JPanel errorPanel = new JPanel();
			JButton exitButton = new JButton("Okay");
			exitButton.addActionListener(event -> System.exit(1));
			errorPanel.add(new JLabel("An error occured while loading your albums\nThe application will now exit."));
			errorPanel.add(exitButton);
			parent.frame.setContentPane(new JPanel());
		}

	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}
}
