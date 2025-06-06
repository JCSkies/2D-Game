package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// These values are the pixels.

	int FPS = 60;

	final int tileSize = 16;

	final int scale = 3;

	public final int trueTileSize = tileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = trueTileSize * maxScreenCol;
	public final int screenHeight = trueTileSize * maxScreenRow;

	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = trueTileSize * maxWorldCol;
	public final int worldHeight = trueTileSize * maxWorldRow;
	/*
	 * this will scale the character/tile/whatever by whatever number you decide
	 * scale to be. In this case, 3 times bigger than the original
	 */

	/*
	 * This is how many "true tiles" are, widht and height
	 */

	int x = 100;
	int y = 100;
	int speed = 4;

	// SYSTEM
	public TileManager TileM = new TileManager(this);

	KeyHandler keyH = new KeyHandler();

	public Player player = new Player(this, keyH);

	Sound sound = new Sound();

	Sound music = new Sound();

	public UI ui = new UI(this);

	public CollisionChecker cChecker = new CollisionChecker(this);

	public SuperObject obj[] = new SuperObject[10];

	public AssetSetter aSetter = new AssetSetter(this);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		/*
		 * double buffering allow events to be predrawn, much faster than single
		 * buffering already does. This increases performance
		 * 
		 */
		this.addKeyListener(keyH);
		/*
		 * now the game will listen for key inputs and respond to certain keys pressed.
		 * w,a,s,d will move the character around
		 */
		this.setFocusable(true);

	}

	public void setupGame() {
		aSetter.setObject();

		playMusic(0);
	}

	Thread gameThread;

	public void startThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;
		while (gameThread != null) {
			/*
			 * System.out.println("This is printed"); UPDATE: update information such as
			 * character positions
			 */
			/*
			 * DRAW: draw the screen with the updated information If the FPS is 60, the
			 * updates and drawing both happen 60 times per second
			 */

			/*
			 * this 1 billion is equal to 1 second (1 billion nanoseconds = 1 second) 1
			 * second / 60 = 60 updates per second the nextDrawTime means draw happens at
			 * every draw interval (1/60 seconds)
			 */
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}

		/*
		 * once remainingTime gets to 0, sleep loop starts again
		 */

	}

	public void update() {
		player.update();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // extends graphics, better control with this class

		// DEBUG
		long drawStart = 0;
		drawStart = System.nanoTime();

		/*
		 * We need to draw tiles before player, so that the background does not cover
		 * the player
		 */

		// Tile render
		TileM.draw(g2);

		// Object render
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}

		// Player render
		player.draw(g2);

		// UI render (UI should always be rendered last, so it is on the highest layer

		ui.draw(g2);

		if (keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.WHITE);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);

		}
		g2.dispose();
	}

	// getter methods

	public int getTileSize() {
		return trueTileSize;
	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) {
		sound.setFile(i);
		sound.play();
	}
}
