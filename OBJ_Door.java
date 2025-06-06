package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class OBJ_Door extends SuperObject {

	public OBJ_Door(GamePanel gp) {
		name = "door";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			uTool.scaledImage(image, gp.trueTileSize, gp.trueTileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
