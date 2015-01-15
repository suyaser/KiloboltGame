package kiloboltgame;

import java.awt.Rectangle;

public class Projectile {

	private int x, y, speedX;
	private boolean visible;
	private Rectangle r;

	public Projectile(int startX, int startY) {
		x = startX;
		y = startY;
		speedX = 7;
		visible = true;
		r = new Rectangle(x, y, 10, 5);
	}

	public void update() {
		x += speedX;
		if (x > 800) {
			visible = false;
		}
		r.setBounds(x, y, 10, 5);
		if (r.intersects(StartingClass.getHb1().r)
				&& StartingClass.getHb1().health > 0) {
			visible = false;
			StartingClass.score += 10;
			StartingClass.getHb1().health -= 1;
			if(StartingClass.getHb1().health == 0)
				StartingClass.score += 5;
		}
		if (r.intersects(StartingClass.getHb2().r)
				&& StartingClass.getHb2().health > 0) {
			visible = false;
			StartingClass.score += 10;
			StartingClass.getHb2().health -= 1;
			if(StartingClass.getHb2().health == 0)
				StartingClass.score += 5;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
