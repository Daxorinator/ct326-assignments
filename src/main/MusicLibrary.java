package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MusicLibrary extends JFrame implements ActionListener {
	public MusicLibrary() {
		super("My Music Library");

		LibraryLoader loader = new LibraryLoader();

		Container contentPane = getContentPane();
		contentPane.setLayout( new GridLayout(loader.getAlbums().size()/2, 2) );

		for (Album album : loader.getAlbums()) {
			JButton albumButton = new JButton(album.getTitle(), album.getCover());
			albumButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			albumButton.setHorizontalTextPosition(SwingConstants.CENTER);
			albumButton.addActionListener(this);
			contentPane.add(albumButton);
		}

		setSize(1280, 720);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.print("Action Performed " + e.ACTION_PERFORMED);
		System.out.print(e.getActionCommand());
	}
}
