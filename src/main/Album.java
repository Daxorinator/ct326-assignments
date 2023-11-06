package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Album {
	private final ImageIcon cover;
	private final String title;
	private final List<String> tracks;
	private final String artist;

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
