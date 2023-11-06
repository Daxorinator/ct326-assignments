package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryLoader {
	private final ArrayList<Album> albums = new ArrayList<>();

	public LibraryLoader() {
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
		} catch (FileNotFoundException e) {
			// To-Do: Rewrite this to throw a better error
			throw new RuntimeException(e);
		} catch (IOException e) {
			// To-Do: Rewrite this to throw a better error
			throw new RuntimeException(e);
		}

	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}
}
