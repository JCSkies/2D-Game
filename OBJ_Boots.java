package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class OBJ_Boots extends SuperObject {
	public OBJ_Boots(GamePanel gp) {
		name = "boots";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			uTool.scaledImage(image, gp.trueTileSize, gp.trueTileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
