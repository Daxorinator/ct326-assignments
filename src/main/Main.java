package main;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		LibraryLoader loader = new LibraryLoader();
		MusicLibrary application = new MusicLibrary(loader);

		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
