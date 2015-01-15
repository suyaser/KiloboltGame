package kiloboltgame;

import java.awt.Image;
import java.awt.Rectangle;

public class tile {

	private int x;
	private int y;
	private int type;
	private int speedX;
	private Image image;
	private Background bg;
	private Robot Qbot = StartingClass.getQbot();
	private Rectangle r;

	public tile(int x, int y, int type) {
		this.x = x * 40;
		this.y = y * 40;
		this.type = type;
		if (type == 5) {
			image = StartingClass.tileDirt;
		} else if (type == 8) {
			image = StartingClass.tilegrasstop;
		} else if (type == 4) {
			image = StartingClass.tilegrassleft;

		} else if (type == 6) {
			image = StartingClass.tilegrassright;

		} else if (type == 2) {
			image = StartingClass.tilegrassbot;
		} else {
			type = 0;
		}
		bg = StartingClass.getBg1();
		r = new Rectangle();
	}

	public void update() {
		speedX = bg.getSpeedX() * 5;
		x += speedX;
		r.setBounds(x, y, 40, 40);
		if (r.intersects(Robot.yellowRed) && type != 0) {
			checkVerticalCollision(Robot.rectU, Robot.rectD);
			checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft,
				Robot.footright);
		}
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot) {
		if (rtop.intersects(r)) {

		}

		if (rbot.intersects(r) && type == 8) {
			Qbot.setJumped(false);
			Qbot.setSpeedY(0);
			Qbot.setCenterY(y - 63);
		}
	}

	public void checkSideCollision(Rectangle rleft, Rectangle rright,
			Rectangle leftfoot, Rectangle rightfoot) {
		if (type == 4 || type == 2 || type == 5 || type == 6) {
			if (rleft.intersects(r)) {
				Qbot.setCenterX(x + 102);
				Qbot.setSpeedX(0);

			} else if (leftfoot.intersects(r)) {
				Qbot.setCenterX(x + 85);
				Qbot.setSpeedX(0);
			}

			if (rright.intersects(r)) {
				Qbot.setCenterX(x - 62);	
				Qbot.setSpeedX(0);
			}

			else if (rightfoot.intersects(r)) {
				Qbot.setCenterX(x - 45);
				Qbot.setSpeedX(0);
			}
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
