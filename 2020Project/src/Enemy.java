import java.awt.Point;

public class Enemy {

	Point enemy_position;

	public Enemy(int x, int y) {
		enemy_position = new Point(x, y);
	}

	public void move() {
		int r;
		int speed = 20;
		if (enemy_position.y < 150) {
			enemy_position.y += speed + 6;
		} else {
			if (enemy_position.x < 20) {// when enemies go out of the frame
				enemy_position.x += speed + 30;
			} else if (enemy_position.x > 340) {// when enemies go out of the frame
				enemy_position.x -= speed + 30;
			} else if (enemy_position.y > 400) {
				enemy_position.y -= speed + 30;
			} else {
				r = (int) (Math.random() * 7);
				if (r % 7 == 0) {
					enemy_position.x += speed;
					enemy_position.y += speed;
				} else if (r % 7 == 1) {
					enemy_position.x -= speed;
					enemy_position.y += speed;
				} else if (r % 7 == 2) {
					enemy_position.x += speed;
					enemy_position.y -= speed;
				} else if (r % 7 == 3) {
					enemy_position.x -= speed;
					enemy_position.y -= speed;
				} else if (r % 7 == 4) {
					enemy_position.x -= speed;
				} else if (r % 7 == 5) {
					enemy_position.x += speed;
				} else if (r % 7 == 6) {
					enemy_position.y -= speed;
				} else {
					enemy_position.y += speed;
				}
			}
		}
	}

}