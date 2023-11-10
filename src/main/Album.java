package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Represents a music album. Serves as a data model for the application.
 * Holds a scaled ImageIcon with the album cover art, as well as the album title, song list, and artist.
 *
 * @author Seán Kelly (21421506) {@literal <s.kelly178@universityofgalway.ie>}
 * @version 0.2.0
 * @since 2023-11-6
 * @see https://github.com/Daxorinator/ct326-assignments/tree/assignment4
 */
public class Album {
	private final ImageIcon cover;
	private final String title;
	private final List<String> tracks;
	private final String artist;

	/**
	 * Creates a new Album object.
	 *
	 * @param artist The artist name as a String
	 * @param title The album title as a String
	 * @param cover The album cover art as a BufferedImage
	 * @param tracks The list of tracks on the album as a List\<String\>
	 */
	public Album(String artist, String title, BufferedImage cover, List<String> tracks) {
		this.title = title;
		this.artist = artist;
		this.cover = new ImageIcon(cover.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		this.tracks = tracks;
	}

	public ImageIcon getCover() {
		return cover;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getTracks() {
		return tracks;
	}

	public String getArtist() {
		return artist;
	}
}
