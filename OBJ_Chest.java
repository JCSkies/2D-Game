package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class OBJ_Chest extends SuperObject {
	public OBJ_Chest(GamePanel gp) {
		name = "chest";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			uTool.scaledImage(image, gp.trueTileSize, gp.trueTileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
