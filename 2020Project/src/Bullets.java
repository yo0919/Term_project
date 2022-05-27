import java.awt.Point;

public class Bullets {
	Point bullet_position;
	int Angle;

	public Bullets(int x, int y, int angle) {
		bullet_position = new Point(x, y);
		Angle = angle;
	}

	public void move() {
		bullet_position.x -= Math.sin(Math.toRadians(Angle)) * 7;
		bullet_position.y -= Math.cos(Math.toRadians(Angle)) * 7;
	}
}
