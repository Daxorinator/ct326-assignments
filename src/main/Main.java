package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
	public static void main(String[] args) {
		MusicLibrary application = new MusicLibrary();

		LibraryLoader loader = new LibraryLoader();

		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
