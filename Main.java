package game;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		GamePanel gp = new GamePanel();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Game!");
		window.add(gp);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		/*
		 * Note: The pack, relative, and visible methods must be set in that order,
		 * otherwise the world will not update properly
		 */

		gp.setupGame();
		gp.startThread();

	}
}
