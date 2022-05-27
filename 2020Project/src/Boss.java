import java.awt.Point;

public class Boss {

	public int RightLeft = 0;
	Point boss_position;

	public Boss(int x, int y) {
		boss_position = new Point(x, y);
	}

	public void move() {
		int speed = 14;
		if (boss_position.y < 100) {
			boss_position.y += speed + 5;
		} else {
			if (boss_position.x < 180 && RightLeft == 0) {
				boss_position.x += speed;
				if (boss_position.x > 165) {
					RightLeft = 1;// change the direction
				}
			} else if (boss_position.x > 0 && RightLeft == 1) {
				boss_position.x -= speed;
				if (boss_position.x < 15) {
					RightLeft = 0;// change the direction
				}
			}

		}
	}
}
