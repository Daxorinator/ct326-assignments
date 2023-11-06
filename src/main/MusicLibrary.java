package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MusicLibrary extends JFrame implements ActionListener {
	public MusicLibrary(LibraryLoader loader) {
		super("My Music Library");

		Container contentPane = getContentPane();
		contentPane.setLayout( new GridLayout(loader.getAlbums().size()/2, 2) );

		for (Album album : loader.getAlbums()) {
			JButton albumButton = new JButton(album.getTitle(), album.getCover());
			albumButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			albumButton.setHorizontalTextPosition(SwingConstants.CENTER);
			albumButton.addActionListener(this);
			albumButton.setActionCommand(
					String.valueOf(loader.getAlbums().indexOf(album))
			);
			contentPane.add(albumButton);
		}

		setSize(1280, 720);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Possible solution:
//		Refactor this class to be a JPanel instead of a JFrame
//		Control JPanel being pained to JFrame in Main
//		Create a second JPanel class for TrackList
	}
}
