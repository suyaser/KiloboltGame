package kiloboltgame;

import java.awt.Rectangle;

public class Enemy {

	private int speedX, centerX, centerY;
	private Background bg = StartingClass.getBg1();
	public Rectangle r;
	public int health = 5;
	private int movementSpeed;

	// Behavioral Methods
	public void update() {
		follow();
		centerX += speedX + movementSpeed;
		speedX = bg.getSpeedX() * 5;
		r = new Rectangle(centerX - 25, centerY - 25, 50, 60);
		if ((r.intersects(Robot.rectU) || r.intersects(Robot.rectD)
				|| r.intersects(Robot.rect3) || r.intersects(Robot.rect4))
				&& health > 0) {
			StartingClass.getQbot().setCenterX(
					StartingClass.getQbot().getCenterX() - 10);
			StartingClass.getQbot().setSpeedX(0);
			StartingClass.score -= 10;
		}

	}

	public void die() {

	}

	public void follow() {

		if (centerX < -95 || centerX > 810) {
			movementSpeed = 0;
		}

		else if (Math.abs(StartingClass.getQbot().getCenterX() - centerX) < 5) {
			movementSpeed = 0;
		}

		else {

			if (StartingClass.getQbot().getCenterX() >= centerX) {
				movementSpeed = 1;
			} else {
				movementSpeed = -1;
			}
		}

	}

	public void attack() {

	}

	public int getSpeedX() {
		return speedX;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public Background getBg() {
		return bg;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

}
