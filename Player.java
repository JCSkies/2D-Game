package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.KeyHandler;
import game.UtilityTool;

public class Player extends Entity {

	KeyHandler keyH;
	GamePanel gp;

	public final int screenX;
	public final int screenY;
	public int hasKey = 0;

	public Player(GamePanel gp, KeyHandler keyH) {
		this.keyH = keyH;
		this.gp = gp;

		screenX = gp.screenWidth / 2 - (gp.trueTileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.trueTileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		worldX = gp.trueTileSize * 25;
		worldY = gp.trueTileSize * 24;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");
	}

	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			image = uTool.scaledImage(image, gp.trueTileSize, gp.trueTileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void update() {

		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true) {

			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}

			collisionOn = false;
			gp.cChecker.checkTile(this);

			// check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			/*
			 * Check tile collision If collision is false, players can move
			 */
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}

		} else if (keyH.upPressed == false || keyH.downPressed == false || keyH.leftPressed == false
				|| keyH.rightPressed == false) {
			spriteNum = 1;
		}
	}

	public void pickUpObject(int i) {
		if (i != 999) {
			String objectName = gp.obj[i].name;

			switch (objectName) {
			case "key":
				hasKey++;
				gp.playSE(1);
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "door":
				if (hasKey > 0) {
					gp.playSE(4);
					gp.obj[i] = null;
					hasKey--;
					System.out.println("Key:" + hasKey);
				}
				break;
			case "boots":
				gp.playSE(2);
				speed = 5;
				gp.obj[i] = null;
				break;
			case "chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(3);
				gp.obj[i] = null;
				break;
			}
		}
	}

	public void draw(Graphics2D g2) {
		// g2.setColor(Color.white);
		// g2.fillRect(x, y, gp.trueTileSize, gp.trueTileSize);
		BufferedImage image = null;
		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
	}
	/*
	 * if you noticed, these are the default values set earlier in GamePanel
	 */

}
