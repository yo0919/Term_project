import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ShooterGame extends JFrame implements KeyListener, Runnable {

	private JPanel contentPane;
	private int panel_width = 360;
	private int panel_height = 565;
	private int userx = 165; // points of character
	private int usery = 480;
	private boolean KeyUp = false;
	private boolean KeyDown = false;
	private boolean KeyLeft = false;
	private boolean KeyRight = false;
	private boolean KeyShoot = false;
	private String Score = "0";
	private String Lives = "5"; // Life of user
	ArrayList Bullet_List = new ArrayList();
	ArrayList Enemy_Bullet_List = new ArrayList();
	ArrayList Boss_Bullet_List = new ArrayList();
	ArrayList Explosion_List_Enemy = new ArrayList();
	ArrayList Explosion_List = new ArrayList();
	ArrayList Explosion_List_Boss = new ArrayList();
	ArrayList Star_List = new ArrayList();
	ArrayList Enemy_List = new ArrayList();
	ArrayList Background_Star_List = new ArrayList();
	ArrayList Boss_List = new ArrayList();
	Bullets bullets;
	EnemyBullets Ebullets;
	BossBullet Bossbullets; // Bullets
	Enemy ene;
	Enemy ene2;
	Boss bosss;
	Enemy_Explosion ex;
	Explosion ex2;
	Boss_Explosion ex3; // Explosion effect
	Background_star starr;
	Thread th;

	/**
	 * counters
	 */
	private int count = 1;
	private int count_enemy_move = 0;
	private int count_bullet_speed = 0;
	private int count_enemy_bullet = 0;
	private int count_explosion_enemy = 0;
	private int count_explosion_boss = 0;
	private int count_explosion = 0;
	private int count_star = 0;
	private int count_boss_move = 0;
	private int r_star = 0;
	private int Boss_Lives = 30;
	private int count_boss_bullet = 0;

	Graphics gs;
	Image user_Image;

	/**
	 * Images
	 */
	Toolkit user = Toolkit.getDefaultToolkit();
	Image user_img = user.getImage("Player.png");
	Toolkit bullet = Toolkit.getDefaultToolkit();
	Image bullet_img = bullet.getImage("bullets.png");
	Toolkit enemy = Toolkit.getDefaultToolkit();
	Image enemy_img = enemy.getImage("Enemy.png");
	Toolkit enemy_bullet = Toolkit.getDefaultToolkit();
	Image enemy_bullet_img = enemy_bullet.getImage("bullets_enemy.png");
	Toolkit explos = Toolkit.getDefaultToolkit();
	Image explosion_img = explos.getImage("explosion.png");
	Toolkit background = Toolkit.getDefaultToolkit();
	Image background_img = background.getImage("background.jpg");
	Toolkit star = Toolkit.getDefaultToolkit();
	Image star_img = star.getImage("background_star.png");
	Toolkit Boss = Toolkit.getDefaultToolkit();
	Image Boss_img = Boss.getImage("Boss.png");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShooterGame frame = new ShooterGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShooterGame() {
		setTitle("ShooterGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(360, 565);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null); // Locate GUI on the center of the screen
		addKeyListener(this);
		th = new Thread(this);
		th.start();
	}

	public void paint(Graphics g) {
		user_Image = createImage(panel_width, panel_height);
		gs = user_Image.getGraphics();
		update(g);
	}

	public void update(Graphics g) {
		Draw_Background();
		Draw_Background_star();
		Draw_Char();
		Draw_Bullet();
		Draw_Status();
		Draw_Enemy();
		Draw_Explosion_Enemy();
		Draw_Explosion_Boss();
		Draw_Explosion();
		Draw_Boss_Bullet();
		Draw_Enemy_Bullet();
		Draw_Boss();
		g.drawImage(user_Image, 0, 0, this);
	}

	/**
	 * Draw the background elements
	 */
	private void Draw_Background_star() {// Draw background star function
		for (int i = 0; i < Star_List.size(); ++i) {
			starr = (Background_star) Star_List.get(i);
			gs.drawImage(star_img, starr.star_position.x, starr.star_position.y, this);
			starr.move();
			if (starr.star_position.y < 0) { // if the star goes out of the screen, remove it
				Star_List.remove(i);
			}
		}
	}

	private void Draw_Background() {// Draw the background image
		gs.clearRect(0, 0, panel_width, panel_height);
		gs.drawImage(background_img, 0, 0, this);
		gs.drawImage(star_img, 40, 30, this);
		gs.drawImage(star_img, 200, 310, this);
		gs.drawImage(star_img, 150, 192, this);
		gs.drawImage(star_img, 20, 260, this);
		gs.drawImage(star_img, 240, 360, this);
		gs.drawImage(star_img, 220, 10, this);
		gs.drawImage(star_img, 41, 420, this);
		gs.drawImage(star_img, 11, 150, this);
		gs.drawImage(star_img, 210, 213, this);
		gs.drawImage(star_img, 150, 320, this);
		gs.drawImage(star_img, 172, 403, this);
		gs.drawImage(star_img, 130, 360, this);
		gs.drawImage(star_img, 130, 21, this);
		gs.drawImage(star_img, 90, 94, this);
		gs.drawImage(star_img, 140, 41, this);
	}

	private void Draw_Status() { // draw the score and lives on the top of the screen
		gs.setColor(Color.WHITE);
		gs.setFont(new Font("Default", Font.BOLD, 20));
		gs.drawString("SCORE: " + Score, 15, 70);
		gs.drawString("LIVES: " + Lives, 240, 70);
	}

	/**
	 * Draw the image of explosions
	 */
	private void Draw_Explosion() { // Explosion effect for character
		for (int i = 0; i < Explosion_List.size(); ++i) {
			ex2 = (Explosion) (Explosion_List.get(i));
			gs.drawImage(explosion_img, userx, usery, this);
		}
		if (count_explosion % 40 == 0) {
			Explosion_List.clear();
		}
	}

	private void Draw_Explosion_Boss() {// Explosion effect for boss
		for (int i = 0; i < Explosion_List_Boss.size(); ++i) {
			ex3 = (Boss_Explosion) (Explosion_List_Boss.get(i));
			gs.drawImage(explosion_img, ex3.Boss_explosion_position.x, ex3.Boss_explosion_position.y, this);
		}
		if (count_explosion_boss % 40 == 0) {
			Explosion_List_Boss.clear();
		}
	}

	private void Draw_Explosion_Enemy() {// Explosion effect for enemy
		for (int i = 0; i < Explosion_List_Enemy.size(); ++i) {
			ex = (Enemy_Explosion) (Explosion_List_Enemy.get(i));
			gs.drawImage(explosion_img, ex.enemy_explosion_position.x, ex.enemy_explosion_position.y, this);
		}
		if (count_explosion_enemy % 40 == 0) {
			Explosion_List_Enemy.clear();
		}
	}

	/**
	 * Draw the image of bullets
	 */
	private void Draw_Boss_Bullet() {
		for (int i = 0; i < Boss_Bullet_List.size(); ++i) {
			Bossbullets = (BossBullet) (Boss_Bullet_List.get(i));
			gs.drawImage(enemy_bullet_img, Bossbullets.boss_bullet_position.x, Bossbullets.boss_bullet_position.y, this);
			Bossbullets.move(); // movement of the bullet
			if (Bossbullets.boss_bullet_position.y < 0) {
				Boss_Bullet_List.remove(i);
			}
		}
	}

	private void Draw_Enemy_Bullet() {
		for (int i = 0; i < Enemy_Bullet_List.size(); ++i) {
			Ebullets = (EnemyBullets) (Enemy_Bullet_List.get(i));
			gs.drawImage(enemy_bullet_img, Ebullets.enemy_bullet_position.x, Ebullets.enemy_bullet_position.y, this);
			Ebullets.move();// movement of the bullet
			if (Ebullets.enemy_bullet_position.y < 0) {
				Enemy_Bullet_List.remove(i);
			}
		}
	}

	private void Draw_Bullet() {
		for (int i = 0; i < Bullet_List.size(); ++i) {
			bullets = (Bullets) (Bullet_List.get(i));
			gs.drawImage(bullet_img, bullets.bullet_position.x, bullets.bullet_position.y, this);
			bullets.move();// movement of the bullet
			if (bullets.bullet_position.y < 0) {
				Bullet_List.remove(i);
			}
		}
	}

	/**
	 * Draw enemies and the character
	 */
	private void Draw_Enemy() {
		for (int i = 0; i < Enemy_List.size(); ++i) {
			ene = (Enemy) (Enemy_List.get(i));
			gs.drawImage(enemy_img, ene.enemy_position.x, ene.enemy_position.y, this);
			if (count_enemy_move % 12 == 0) { // to make enemies move more smoothly
				ene.move();
			}
			if (ene.enemy_position.y < 0) {
				Enemy_List.remove(i);
			}
		}
	}

	private void Draw_Char() { // draw the flight
		gs.drawImage(user_img, userx, usery, this);
	}

	private void Draw_Boss() {
		for (int i = 0; i < Boss_List.size(); ++i) {
			bosss = (Boss) Boss_List.get(i);
			gs.drawImage(Boss_img, bosss.boss_position.x, bosss.boss_position.y, this);
			if (count_boss_move % 12 == 0) {
				bosss.move();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			KeyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = true;
			break;
		case KeyEvent.VK_UP:
			KeyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			KeyDown = true;
			break;
		case KeyEvent.VK_SPACE:
			KeyShoot = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			KeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = false;
			break;
		case KeyEvent.VK_UP:
			KeyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			KeyDown = false;
			break;
		case KeyEvent.VK_SPACE:
			KeyShoot = false;
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		try {
			while (Integer.parseInt(Lives) > 0) {
				keyProcess();
				bossProcess();
				hitBoss();
				BulletProcess();
				EnemyProcess();
				StarProcess();
				EnemyBulletProcess();
				BossBulletProcess();
				hitEnemy();
				hitbyEnemy();
				hitbyBoss();
				repaint();
				count++;
				count_enemy_move++;
				count_enemy_bullet++;
				count_boss_bullet++;
				count_bullet_speed++;
				count_explosion_enemy++;
				count_explosion_boss++;
				count_boss_move++;
				count_explosion++;
				count_star++;
				Thread.sleep(20);
			}
			JOptionPane.showMessageDialog(null, "Game Over!", "Message", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
		}
	}

	private void keyProcess() {// change the coordinate of the character
		if (userx < 20 && KeyLeft == true)
			return;
		if (userx > 300 && KeyRight == true)
			return;
		if (usery < 50 && KeyUp == true)
			return;
		if (usery > 500 && KeyDown == true)
			return;
		if (KeyLeft == true)
			userx -= 5;
		if (KeyRight == true)
			userx += 5;
		if (KeyUp == true)
			usery -= 5;
		if (KeyDown == true)
			usery += 5;
	}

	private void StarProcess() {// make moving stars
		if (count_star % 70 == 0) {
			r_star++;
			if (r_star % 2 == 0) {
				starr = new Background_star(10, 0);
				Star_List.add(starr);
				starr = new Background_star(100, 0);
				Star_List.add(starr);
				starr = new Background_star(190, 0);
				Star_List.add(starr);
				starr = new Background_star(280, 0);
				Star_List.add(starr);
			} else if (r_star % 2 == 1) {
				starr = new Background_star(55, 0);
				Star_List.add(starr);
				starr = new Background_star(145, 0);
				Star_List.add(starr);
				starr = new Background_star(235, 0);
				Star_List.add(starr);
			}
		}
	}

	private void EnemyProcess() {// make normal enemies
		if (count % 200 == 0) {
			ene = new Enemy(20, 30);
			Enemy_List.add(ene);
			ene = new Enemy(110, 30);
			Enemy_List.add(ene);
			ene = new Enemy(200, 30);
			Enemy_List.add(ene);
			ene = new Enemy(290, 30);
			Enemy_List.add(ene);
			if (Integer.parseInt(Score) > 4000) {
				ene = new Enemy(155, 0);
				Enemy_List.add(ene);
			}
		}
	}

	private void bossProcess() {// make boss enemies
		if (count % 500 == 0) {
			bosss = new Boss(90, 0);
			Boss_List.add(bosss);
		}
	}

	private void BossBulletProcess() {// make boss's bullets and put in the ArrayList
		if (count_boss_bullet % 50 == 0) {
			for (int i = 0; i < Boss_List.size(); i++) {
				Boss bosss = (Boss) Boss_List.get(i);
				Bossbullets = new BossBullet(bosss.boss_position.x + 70, bosss.boss_position.y + 50, 30);
				Boss_Bullet_List.add(Bossbullets);
				Bossbullets = new BossBullet(bosss.boss_position.x + 70, bosss.boss_position.y + 50, 0);
				Boss_Bullet_List.add(Bossbullets);
				Bossbullets = new BossBullet(bosss.boss_position.x + 70, bosss.boss_position.y + 50, 330);
				Boss_Bullet_List.add(Bossbullets);
			}
		}
	}

	private void EnemyBulletProcess() {// make enemy's bullets and put in the ArrayList
		if (count_enemy_bullet % 50 == 0) {
			for (int i = 0; i < Enemy_List.size(); i++) {
				Enemy en = (Enemy) Enemy_List.get(i);
				Ebullets = new EnemyBullets(en.enemy_position.x, en.enemy_position.y + 30);
				Enemy_Bullet_List.add(Ebullets);
			}
		}
	}

	private void BulletProcess() { // shoot the bullet
		if (KeyShoot) {
			int upgrade = 0;
			if (Integer.parseInt(Score) > 3000) {
				upgrade = 8;
			} else if (Integer.parseInt(Score) > 1000) {
				upgrade = 6;
			}
			bullets = new Bullets(userx, usery - 35, 0);
			if (count_bullet_speed % 10 - upgrade == 0) {
				Bullet_List.add(bullets);
			}
			if (Integer.parseInt(Score) > 2000) {
				bullets = new Bullets(userx, usery - 35, 20);
				if (count_bullet_speed % 10 - upgrade == 0) {
					Bullet_List.add(bullets);
				}
				bullets = new Bullets(userx, usery - 35, 340);
				if (count_bullet_speed % 10 - upgrade == 0) {
					Bullet_List.add(bullets);
				}
			}
		}
		for (int i = 0; i < Bullet_List.size(); i++) {
			if (bullets.bullet_position.y < 0) {// If the bullet is out of the screen
				Bullet_List.remove(i);
			}
		}
	}

	/**
	 * 
	 */
	public void hitEnemy() {// hit the enemy
		int scoreup = 100;
		int scoreNow = Integer.parseInt(Score);
		for (int i = 0; i < Bullet_List.size(); i++) {
			bullets = (Bullets) Bullet_List.get(i);
			for (int j = 0; j < Enemy_List.size(); j++) {
				ene2 = (Enemy) Enemy_List.get(j);
				if (IsIntersect(ene2.enemy_position.x, ene2.enemy_position.y, 20, 20, bullets.bullet_position.x, bullets.bullet_position.y,
						20, 20)) {
					int hitx = ene2.enemy_position.x;
					int hity = ene2.enemy_position.y;
					ex = new Enemy_Explosion(hitx, hity);
					Explosion_List_Enemy.add(ex);
					Enemy_List.remove(j);
					Bullet_List.remove(i);
					scoreNow += scoreup;
					Score = Integer.toString(scoreNow);
				}
			}
		}
	}

	public void hitBoss() {// hit the boss
		int scoreup = 1000;
		int scoreNow = Integer.parseInt(Score);
		for (int i = 0; i < Bullet_List.size(); i++) {
			bullets = (Bullets) Bullet_List.get(i);
			for (int j = 0; j < Boss_List.size(); j++) {
				bosss = (Boss) Boss_List.get(j);
				if (IsIntersect(bosss.boss_position.x, bosss.boss_position.y, 93, 50, bullets.bullet_position.x, bullets.bullet_position.y,
						20, 20)) {
					Boss_Lives -= 1;
					Bullet_List.remove(i);
					ex3 = new Boss_Explosion(bullets.bullet_position.x, bullets.bullet_position.y);
					Explosion_List_Boss.add(ex3);
					if (Boss_Lives <= 0) {
						scoreNow += scoreup;
						Score = Integer.toString(scoreNow);
						Boss_List.remove(j);
						Boss_Lives = 30;
					}
				}
			}
		}
	}

	public void hitbyEnemy() {// when the character is hit by the enemy
		int lifedown = 1;
		int lifeNow = Integer.parseInt(Lives);
		for (int i = 0; i < Enemy_Bullet_List.size(); i++) {
			Ebullets = (EnemyBullets) Enemy_Bullet_List.get(i);
			if (IsIntersect(userx, usery, 20, 20, Ebullets.enemy_bullet_position.x, Ebullets.enemy_bullet_position.y, 20, 20)) {
				ex2 = new Explosion(userx, usery);
				Explosion_List.add(ex2);
				Enemy_Bullet_List.remove(i);
				lifeNow -= lifedown;
				Lives = Integer.toString(lifeNow);
			}
		}
	}

	private void hitbyBoss() {// when the character is hit by the boss
		int lifedown = 1;
		int lifeNow = Integer.parseInt(Lives);
		for (int i = 0; i < Boss_Bullet_List.size(); i++) {
			Bossbullets = (BossBullet) Boss_Bullet_List.get(i);
			if (IsIntersect(userx, usery, 20, 20, Bossbullets.boss_bullet_position.x, Bossbullets.boss_bullet_position.y, 20, 20)) {
				ex2 = new Explosion(userx, usery);
				Explosion_List.add(ex2);
				Boss_Bullet_List.remove(i);
				lifeNow -= lifedown;
				Lives = Integer.toString(lifeNow);
			}
		}
	}

	public static boolean IsIntersect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		// check the crush
		if (x1 + w1 >= x2 && x1 <= x2 && y1 + h1 >= y2 && y1 <= y2 + h2) {
			return true;
		}
		return false;
	}

}
