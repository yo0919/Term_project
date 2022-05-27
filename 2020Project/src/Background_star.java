import java.awt.Point;

public class Background_star {

	Point star_position;

	public Background_star(int x, int y) {
		star_position = new Point(x, y);
	}

	public void move() {
		star_position.y += 3;
	}

}