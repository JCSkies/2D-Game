package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class OBJ_Key extends SuperObject {

	public OBJ_Key(GamePanel gp) {
		name = "key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			uTool.scaledImage(image, gp.trueTileSize, gp.trueTileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
