package com.lin.parctice.planegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlaneShootingGame extends JPanel {
	public static int WIDTH = 400;
	public static int HEIGHT = 654;
	public int state = START;
	private static final int START = 1;
	private static final int RUNNING = 2;
	private static final int PAUSING = 3;
	private static final int GAMEOVER = 4;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage background;
	public static BufferedImage player;
	public static BufferedImage bullet;
	public static BufferedImage award;
	public static BufferedImage enermy;
	private int loopTime = 0;
	private int BulletLoopTime = 0;
	static {
		try {
			player = ImageIO.read(new File(".\\Image\\hero0.png"));
			background = ImageIO.read(new File(".\\Image\\background.png"));
			start = ImageIO.read(new File(".\\Image\\start.png"));
			pause = ImageIO.read(new File(".\\Image\\pause.png"));
			gameover = ImageIO.read(new File(".\\Image\\gameover.png"));
			enermy = ImageIO.read(new File(".\\Image\\airplane.png"));
			award = ImageIO.read(new File(".\\Image\\bee.png"));
			bullet = ImageIO.read(new File(".\\Image\\bullet.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int score;
	ArrayList<FlyingObjects> flyings = new ArrayList<FlyingObjects>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	MyPlane PlayerPlane = new MyPlane();

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintMyPlane(g);
		paintBullet(g);
		paintFlyingObjects(g);
		paintScore(g);
		paintState(g);
	}

	public void paintMyPlane(Graphics g) {
		g.drawImage(PlayerPlane.image, PlayerPlane.getX(), PlayerPlane.getY(), null);
	}

	public void paintBullet(Graphics g) {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			g.drawImage(bullet.GetImage(), bullet.getX() - bullet.getHeight(), bullet.getY(), null);
		}
	}

	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.size(); i++) {
			FlyingObjects flying = flyings.get(i);
			g.drawImage(flying.GetImage(), flying.getX(), flying.getY(), null);
		}
	}

	public void paintScore(Graphics g) {
		int x = 10;
		int y = 25;
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);
		g.setColor(new Color(0xFF0000));
		g.setFont(font);
		g.drawString("SCORE:" + score, x, y);
		y += 20;
		g.drawString("LIFE:" + PlayerPlane.getLife(), x, y);
	}

	public void paintState(Graphics g) {
		switch (state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSING:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAMEOVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public class MyMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			switch (state) {// 鼠标点击开始
			case START:
				state = RUNNING;
				break;

			case GAMEOVER:// 鼠标点击回归初始状态
				flyings.clear();
				bullets.clear();
				PlayerPlane = new MyPlane();
				score = 0;
				state = START;
				loopTime = 0;
				BulletLoopTime = 0;
				break;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (state == PAUSING) {// 鼠标移进画面则游戏继续
				state = RUNNING;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (state == RUNNING) {// 鼠标移出画面则游戏暂停
				state = PAUSING;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (state == RUNNING) {// 用户飞机跟随鼠标移动。
				int x = e.getX();
				int y = e.getY();
				PlayerPlane.moveto(x, y);
			}
		}

	}

	public void startGame() {
		final int delay = 10;
		final int frequency = 10;
		MouseAdapter mouseAdapter = new MyMouseAdapter();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (state == RUNNING) {
					enterFlying();
					takeAStep();
					shootABullet();
					isHit();
					getReward();
					checkOutofBorder();
					checkGameOver();
				}
				repaint();
			}
		}, delay, frequency);
	}

	public void enterFlying() {
		loopTime++;
		if (loopTime % 10 == 0) {
			FlyingObjects newobj = nextFlyingObj();
			flyings.add(newobj);
		}
	}

	public void takeAStep() {
		for (FlyingObjects obj : flyings) {
			obj.step();
		}
		for (Bullet bt : bullets) {
			bt.step();
		}
		PlayerPlane.step();
	}

	public void shootABullet() {
		BulletLoopTime++;
		if (BulletLoopTime % 25 == 0) {
			Bullet temp[] = PlayerPlane.fire();
			for (int i = 0; i < temp.length; i++)
				bullets.add(temp[i]);
		}
	}

	public void isHit() {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet  b=bullets.get(i);
			if(isHitByBullet(b)){
				bullets.remove(b);
				i--;
			}
		}
	}

	public void getReward() {

		for (int i = 0; i < flyings.size(); i++) {
			FlyingObjects obj = flyings.get(i);
			if (obj instanceof FlyingAwards) {
				Awards aw = (Awards) obj;
				if (obj.isPickByPlane(PlayerPlane)) {
					int type = aw.getAwardType();
					if (type == Awards.ADDLIFE) {
						PlayerPlane.addLife();
					} else if (type == Awards.DOUBLEFIRE) {
						PlayerPlane.SetDoubleFire();
					}
					flyings.remove(obj);
					i--;
				}
			}
		}
	}

	public void checkOutofBorder() {
		for (int i = 0; i < flyings.size(); i++) {
			if (flyings.get(i).OutOfBoder()) {
				flyings.remove(i);
				i--;
			}
		}
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).OutOfBoder()) {
				bullets.remove(i);
				i--;
			}
		}
	}

	public void checkGameOver() {
		if (isGameOver()) {
			state = GAMEOVER;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("打飞机小游戏");
		PlaneShootingGame game = new PlaneShootingGame();
		frame.add(game);
		frame.setSize(WIDTH, HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon(".\\Image\\icon.jpg").getImage());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.startGame();
	}

	public FlyingObjects nextFlyingObj() {
		Random rand = new Random();
		int temp = rand.nextInt(60);
		if (temp < 5) {
			return new FlyingAwards();
		} else
			return new EnermyPlane();
	}

	public boolean isGameOver() {
		for (int i = 0; i < flyings.size(); i++) {
			FlyingObjects ob = flyings.get(i);
			if (PlayerPlane.ishit(ob)) {
				PlayerPlane.decreaseLife();
				PlayerPlane.deDoubleFire();
				flyings.remove(ob);
				i--;
			}
		}
		return PlayerPlane.getLife() <= 0;
	}

	public boolean isHitByBullet(Bullet b) {
		int flag=0;
		for (int i = 0; i < flyings.size(); i++) {
			FlyingObjects obj = flyings.get(i);
			if (obj instanceof Enermy) {
				if (obj.IsGetHit(b)) {
					Enermy e = (Enermy) obj;
					score += e.getScore();
					flyings.remove(obj);
					i--;
					flag=1;
				}
			}
		}
		if(flag>0) return true;
		else return false;
	}
}
