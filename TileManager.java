package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		loadMap("/maps/world01.txt");
	}

	public void getTileImage() {
		setup(0, "grass", false);
		setup(1, "wall", true);
		setup(2, "water", true);
		setup(3, "earth", false);
		setup(4, "sand", false);
		setup(5, "tree", true);
		setup(6, "floor01", false);

	}

	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int row = 0;
			int col = 0;
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();

				while (col < gp.maxWorldCol) {

					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					System.out.print(num + " ");
					col++;
				}
				if (col == gp.maxWorldCol) {
					System.out.println("");
					col = 0;
					row++;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
			tile[index].image = uTool.scaledImage(tile[index].image, gp.trueTileSize, gp.trueTileSize);
			tile[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			/*
			 * first we check tile's worldX (worldCol * tileSize) if [0][0] tile, then 0 *
			 * 40 and 0 * 48
			 */

			int worldX = worldCol * gp.trueTileSize;
			int worldY = worldRow * gp.trueTileSize;

			/*
			 * now we need to know WHERE on the screen to draw it
			 */
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if (worldX + gp.trueTileSize > gp.player.worldX - gp.player.screenX
					&& worldX - gp.trueTileSize < gp.player.worldX + gp.player.screenX
					&& worldY + gp.trueTileSize > gp.player.worldY - gp.player.screenY
					&& worldY - gp.trueTileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			/*
			 * This if statement above looks to see what is visible on the screen. if the
			 * tile is in the boundary, it can be drawn.
			 */
			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;

				worldRow++;

			}
		}
	}
}
