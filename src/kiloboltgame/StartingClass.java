package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import kiloboltgame.framework.animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private static Robot Qbot;
	private Image image, currentSprite, character, character2, character3,
			characterDown, characterJumped, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;
	private animation anim, hanim;
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	public static Image tileOcean;
	public static Image tileDirt;
	private static Heliboy hb1;
	private static Heliboy hb2;
	private ArrayList<tile> tiles = new ArrayList<tile>();
	public static Image tilegrassbot;
	public static Image tilegrasstop;
	public static Image tilegrassleft;
	public static Image tilegrassright;
	public static int score;
	private Font font = new Font(null, Font.BOLD, 30);

	enum GameState {
		Alive, Dead
	}

	GameState state = GameState.Alive;

	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot ALPHA");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
		}
		// Image Setups
		character = getImage(base, "raw/character.png");
		character2 = getImage(base, "raw/character2.png");
		character3 = getImage(base, "raw/character3.png");

		characterDown = getImage(base, "raw/down.png");
		characterJumped = getImage(base, "raw/jumped.png");

		heliboy = getImage(base, "raw/heliboy.png");
		heliboy2 = getImage(base, "raw/heliboy2.png");
		heliboy3 = getImage(base, "raw/heliboy3.png");
		heliboy4 = getImage(base, "raw/heliboy4.png");
		heliboy5 = getImage(base, "raw/heliboy5.png");

		background = getImage(base, "raw/background.png");

		tileDirt = getImage(base, "raw/tiledirt.png");
		tilegrassbot = getImage(base, "raw/tilegrassbot.png");
		tilegrasstop = getImage(base, "raw/tilegrasstop.png");
		tilegrassright = getImage(base, "raw/tilegrassright.png");
		tilegrassleft = getImage(base, "raw/tilegrassleft.png");

		anim = new animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		hanim = new animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		currentSprite = anim.getImage();
	}

	public static Background getBg1() {
		return bg1;
	}

	public static void setBg1(Background bg1) {
		StartingClass.bg1 = bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static void setBg2(Background bg2) {
		StartingClass.bg2 = bg2;
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		Qbot = new Robot();
		try {
			loadTiles("raw/map1.txt");
		} catch (IOException e) {

		}
		hb1 = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadTiles(String string) throws IOException {
		int width = 0;
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(string));
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(line.length(), width);
			}
		}
		for (int j = 0; j < 12; j++) {
			String line = lines.get(j);
			{
				for (int i = 0; i < width; i++) {
					if (i < line.length()) {
						char ch = line.charAt(i);
						tile tile = new tile(i, j,
								Character.getNumericValue(ch));
						tiles.add(tile);
					}
				}
			}
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run() {
		if (state == GameState.Alive) {
			while (true) {
				bg1.update();
				bg2.update();
				hb1.update();
				hb2.update();
				Qbot.update();
				if (Qbot.isJumped()) {
					currentSprite = characterJumped;
				} else if (!Qbot.isJumped() && !Qbot.isDucked()) {
					currentSprite = anim.getImage();
				}
				ArrayList<Projectile> projectiles = Qbot.getProjectiles();
				for (int i = projectiles.size() - 1; i >= 0; i--) {
					Projectile p = (Projectile) projectiles.get(i);
					if (p.isVisible() == true) {
						p.update();
					} else {
						projectiles.remove(i);
					}
				}
				for (tile a : tiles) {
					a.update();
				}
				anim.update(10);
				hanim.update(50);
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (Qbot.getCenterY() > 500) {
					state = GameState.Dead;
				}
			}
		}
	}

	@Override
	public void update(Graphics arg0) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);
		arg0.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {
		if (state == GameState.Alive) {
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		for (tile a : tiles) {
			g.drawImage(a.getImage(), a.getX(), a.getY(), this);
		}
		ArrayList<Projectile> projectiles = Qbot.getProjectiles();
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		if(hb1.health>0)
		g.drawImage(hanim.getImage(), hb1.getCenterX() - 48,
				hb1.getCenterY() - 48, this);
		if(hb2.health>0)
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
				hb2.getCenterY() - 48, this);
		g.drawImage(currentSprite, Qbot.getCenterX() - 61,
				Qbot.getCenterY() - 63, this);
		// g.drawRect((int) Robot.rectU.getX(), (int) Robot.rectU.getY(),
		// (int) Robot.rectU.getWidth(), (int) Robot.rectU.getHeight());
		// g.drawRect((int) Robot.rectD.getX(), (int) Robot.rectD.getY(),
		// (int) Robot.rectD.getWidth(), (int) Robot.rectD.getHeight());
		// g.drawRect((int)Robot.rectU.getX() - 26, (int)Robot.rectU.getY()+32,
		// 26, 20);
		// g.drawRect((int)Robot.rectU.getX() + 68, (int)Robot.rectU.getY()+32,
		// 26, 20);
		// g.drawRect(Qbot.getCenterX() - 110, Qbot.getCenterY() - 110, 180,
		// 180);
		// g.setColor(Color.blue);
		// g.drawRect(Qbot.getCenterX() - 50, Qbot.getCenterY() + 20, 50, 15);
		// g.drawRect(Qbot.getCenterX(), Qbot.getCenterY() + 20, 50, 15);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(score), 740, 30);
		} else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);


		}
	}

	public static Heliboy getHb1() {
		return hb1;
	}

	public static Heliboy getHb2() {
		return hb2;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("move up");
			Qbot.jump();
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("move down");
			currentSprite = characterDown;
			if (Qbot.isJumped() == false) {
				Qbot.setDucked(true);
				Qbot.setSpeedX(0);
			}
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("move right");
			Qbot.moveRight();
			Qbot.setMovingRight(true);
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("move Left");
			Qbot.moveLeft();
			Qbot.setMovingLeft(true);
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("Jump");
			if (!Qbot.isDucked() && !Qbot.isJumped()) {
				Qbot.shoot();
				Qbot.setReadyToFire(false);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("stop move up");
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("stop move down");
			currentSprite = anim.getImage();
			Qbot.setDucked(false);
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("stop move right");
			Qbot.stopRight();
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("stop move Left");
			Qbot.stopLeft();
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("stop Jump");
			Qbot.setReadyToFire(true);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static Robot getQbot() {
		return Qbot;
	}

}
