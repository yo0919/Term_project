import java.awt.Point;

public class EnemyBullets {
	Point enemy_bullet_position;

	public EnemyBullets(int x, int y) {
		enemy_bullet_position = new Point(x, y);
	}

	public void move() {
		enemy_bullet_position.y += 5;
	}
}
