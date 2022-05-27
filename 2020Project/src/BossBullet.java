import java.awt.Point;

public class BossBullet {
	Point boss_bullet_position;
	int Angle;

	public BossBullet(int x, int y, int angle) {
		boss_bullet_position = new Point(x, y);
		Angle = angle;
	}

	public void move() {
		boss_bullet_position.x += Math.sin(Math.toRadians(Angle)) * 6;
		boss_bullet_position.y += Math.cos(Math.toRadians(Angle)) * 6;
	}
}
