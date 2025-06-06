package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

	GamePanel gp;
	Font arial_40;
	Font arial_80;
	BufferedImage keyImage;
	int messageCounter = 0;

	public boolean messageOn = false;
	public String message = "";

	public boolean gameFinished = false;

	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");

	public UI(GamePanel gp) {
		this.gp = gp;

		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80 = new Font("Arial", Font.PLAIN, 80);
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;

	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {

		if (gameFinished == true) {

			g2.setFont(arial_40);
			g2.setColor(Color.WHITE);
			String text;
			int textLength;
			int x;
			int y;

			text = "You found the treasure chest!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 - (gp.trueTileSize * 3);
			g2.drawString(text, x, y);

			g2.setFont(arial_80);
			g2.setColor(Color.YELLOW);
			text = "Congratulations!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight - (gp.trueTileSize * 3);
			g2.drawString(text, x, y);

			gp.gameThread = null;

		} else {
			g2.setFont(arial_40);
			g2.setColor(Color.WHITE);
			g2.drawImage(keyImage, gp.trueTileSize / 2, gp.trueTileSize / 2 - 5, gp.trueTileSize, gp.trueTileSize,
					null);
			g2.drawString("x " + gp.player.hasKey, 75, 60);

			// TIME
			playTime += (double) 1 / 60;
			g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 35, 65);

			// MESSAGe
			if (messageOn == true) {

				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize * 2, gp.tileSize * 7);

				messageCounter++;
				if (messageCounter == 120) { // no need for while loop, draw is already called 60 times a second
					messageOn = false;
					messageCounter = 0;
				}
			}
		}

	}

}
