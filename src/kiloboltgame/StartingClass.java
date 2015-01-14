package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

import kiloboltgame.framework.animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private Robot Qbot;
	private Image image, currentSprite, character, character2, character3,
			characterDown, characterJumped, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;
	private animation anim, hanim;
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Heliboy hb1, hb2;

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
		hb1 = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		Qbot = new Robot();
		Thread thread = new Thread(this);
		thread.start();
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
			animate();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void animate() {
		   anim.update(10);
		   hanim.update(50);
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
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		ArrayList<Projectile> projectiles = Qbot.getProjectiles();
		for (int i = projectiles.size() - 1; i >= 0; i--) {
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		g.drawImage(hanim.getImage(), hb1.getCenterX() - 48, hb1.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
		g.drawImage(currentSprite, Qbot.getCenterX() - 61,
				Qbot.getCenterY() - 63, this);
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
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
