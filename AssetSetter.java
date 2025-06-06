package game;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldX = 37 * gp.trueTileSize;
		gp.obj[0].worldY = 34 * gp.trueTileSize;

		gp.obj[1] = new OBJ_Key(gp);
		gp.obj[1].worldX = 40 * gp.trueTileSize;
		gp.obj[1].worldY = 40 * gp.trueTileSize;

		gp.obj[2] = new OBJ_Door(gp);
		gp.obj[2].worldX = 18 * gp.trueTileSize;
		gp.obj[2].worldY = 31 * gp.trueTileSize;

		gp.obj[3] = new OBJ_Chest(gp);
		gp.obj[3].worldX = 19 * gp.trueTileSize;
		gp.obj[3].worldY = 13 * gp.trueTileSize;

		gp.obj[4] = new OBJ_Boots(gp);
		gp.obj[4].worldX = 9 * gp.trueTileSize;
		gp.obj[4].worldY = 31 * gp.trueTileSize;

		gp.obj[6] = new OBJ_Door(gp);
		gp.obj[6].worldX = 19 * gp.trueTileSize;
		gp.obj[6].worldY = 15 * gp.trueTileSize;

	}
}
