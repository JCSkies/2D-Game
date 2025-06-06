package game;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	/*
	 * let's check if the player is colliding with a solid tile or not
	 */
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		// left side of the rectangle
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		// right side of the rectangle
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		// top side of the box
		int entityBotWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		// based on these numbers, we can find their col and row numbers
		int entityLeftCol = entityLeftWorldX / gp.trueTileSize;
		int entityRightCol = entityRightWorldX / gp.trueTileSize;
		int entityTopRow = entityTopWorldY / gp.trueTileSize;
		int entityBotRow = entityBotWorldY / gp.trueTileSize;

		/*
		 * now in tiles, only need to check 2 tiles in each direction. example: if going
		 * up, then only check the left and right shoulders
		 */
		int tileNum1, tileNum2;
		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.trueTileSize;
			tileNum1 = gp.TileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.TileM.mapTileNum[entityRightCol][entityTopRow];
			// tileNum1 is the left shoulder, tileNum2 is the right shoulder
			if (gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBotRow = (entityBotWorldY + entity.speed) / gp.trueTileSize;
			tileNum1 = gp.TileM.mapTileNum[entityLeftCol][entityBotRow];
			tileNum2 = gp.TileM.mapTileNum[entityRightCol][entityBotRow];
			// tileNum1 is the left shoulder, tileNum2 is the right shoulder
			if (gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.trueTileSize;
			tileNum1 = gp.TileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.TileM.mapTileNum[entityLeftCol][entityBotRow];
			// tileNum1 is the left shoulder, tileNum2 is the right shoulder
			if (gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.trueTileSize;
			tileNum1 = gp.TileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.TileM.mapTileNum[entityRightCol][entityBotRow];
			// tileNum1 is the left shoulder, tileNum2 is the right shoulder
			if (gp.TileM.tile[tileNum1].collision == true || gp.TileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}

	public int checkObject(Entity entity, boolean player) {
		int index = 999;

		for (int i = 0; i < gp.obj.length; i++) {
			if (gp.obj[i] != null) {

				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

			}
		}

		return index;
	}

	/*
	 * we want to check if entity is player or not
	 */
	/*
	 * public int checkObject(Entity entity, boolean player) { int index = 999;
	 * 
	 * for (int i = 0; i < gp.obj.length; i++) { // checks all the items in the obj
	 * array
	 * 
	 * if (gp.obj[i] != null) { // get entity's solid area position
	 * entity.solidArea.x = entity.worldX + entity.solidArea.x; entity.solidArea.y =
	 * entity.worldY + entity.solidArea.y;
	 * 
	 * // get the object's solid area position gp.obj[i].solidArea.x =
	 * gp.obj[i].worldX + gp.obj[i].solidArea.x; gp.obj[i].solidArea.y =
	 * gp.obj[i].worldY + gp.obj[i].solidArea.y;
	 * 
	 * switch (entity.direction) { case "up": entity.solidArea.y -= entity.speed; if
	 * (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	 * System.out.println("down collision"); /* if (gp.obj[i].collision == true) {
	 * entity.collisionOn = true; } if (player == true) { index = i; }
	 * 
	 * // this makes sure only the player can pick up the objects } break; case
	 * "down": entity.solidArea.y += entity.speed; if
	 * (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	 * System.out.println("down collision!"); } break; case "left":
	 * entity.solidArea.x -= entity.speed; if
	 * (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	 * System.out.println("left collision!"); } break; case "right":
	 * entity.solidArea.x += entity.speed; if
	 * (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	 * System.out.println("right collision!"); } break; } entity.solidArea.x =
	 * entity.solidAreaDefaultX; entity.solidArea.y = entity.solidAreaDefaultY;
	 * gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX; gp.obj[i].solidArea.x =
	 * gp.obj[i].solidAreaDefaultY;
	 * 
	 * } } return index; }
	 */
}
