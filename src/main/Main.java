package main;

import java.io.*;

public class Main {
	public static void main(String[] args) {
		Player player = new Player("CrashOverride", Country.IRELAND);
		Achievement achievement = new Achievement("Dev Achievement", "You shouldn't have gotten this!");
		player.addAchievement(achievement);

		try {
			FileOutputStream fout = new FileOutputStream("test.txt");
			ObjectOutputStream oout = new ObjectOutputStream(fout);

			oout.writeObject(achievement);
			oout.writeObject(player);

			oout.flush();
			oout.close();
			fout.close();

			FileInputStream fin = new FileInputStream("test.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);

			Achievement loadedAchievement = (Achievement) oin.readObject();
			Player loadedPlayer = (Player) oin.readObject();

			/**
			 * To-Do List:
			 * OK - Manually Serialize each object by writing out all attributes, except the list of player achievements
			 * OK - Write the list of achievements in text format to a CSV file called achievements.csv
			 * OK - Each Achievement object in the list of awards should be written as a separate line in the CSV
			 * OK - Each line in the CSV should contain the attributes of the achievement object separated by a comma and some way to identify the player to which the achievement belongs
			 * OK - The player class should implement a readObject() which reads back in all of the above, the list of achievements should be read from the CSV (You can use a Scanner with , as the delimiter and \n as EOL)
			 * OK - Include appropriate exception handling
			 * OK - Include tests for everything
			 * * JavaDoc everything
			 * * Delete Main.java
			 */

			System.out.println("Loading complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
