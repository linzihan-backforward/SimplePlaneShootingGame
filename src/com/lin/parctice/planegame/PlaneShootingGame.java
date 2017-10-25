package com.lin.parctice.planegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class PlaneShootingGame extends JPanel {
	public static int WIDTH=400;
	public static int HEIGHT=654;
	public int state=START;
	private static final int START=1;
	private static final int RUNNING=2;
	private static final int PAUSING=3;
	private static final int GAMEOVER=4;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage background;
	public static BufferedImage player;
	static {
		try {
			player=ImageIO.read(new File(".\\Image\\hero0.png"));
			background=ImageIO.read(new File(".\\Image\\background.png"));
			start=ImageIO.read(new File(".\\Image\\start.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int score;
	ArrayList<FlyingObjects> flyings=new ArrayList<FlyingObjects>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	MyPlane PlayerPlane= new MyPlane();
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintMyPlane(g);
		paintBullet(g);
		paintFlyingObjects(g);
		paintScore(g);
		paintState(g);
	}
	public void paintMyPlane(Graphics g){
		g.drawImage(PlayerPlane.image, PlayerPlane.getX(), PlayerPlane.getY(), null);
	}
	public void paintBullet(Graphics g){
		for(int i=0;i<bullets.size();i++){
			Bullet bullet=bullets.get(i);
			g.drawImage(bullet.GetImage(), bullet.getX()-bullet.getHeight(), bullet.getY(), null);
		}
	}
	public void paintFlyingObjects(Graphics g){
		for(int i=0;i<flyings.size();i++){
			FlyingObjects flying= flyings.get(i);
			g.drawImage(flying.GetImage(), flying.getX(), flying.getY(), null);
		}
	}
	public void paintScore(Graphics g){
		int x=10;
		int y=25;
		Font font=new Font(Font.SANS_SERIF,Font.BOLD,22);
		g.setColor(new Color(0xFF0000));
		g.setFont(font);
		g.drawString("SCORE:"+score, x, y);
		y+=20;
		g.drawString("LIFE:"+PlayerPlane.getLife(), x, y);
	}
	public void paintState(Graphics g){
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
			switch (state) {//�������ʼ
			case START:
				state=RUNNING;
				break;

			case GAMEOVER://������ع��ʼ״̬
				flyings.clear();
				bullets.clear();
				PlayerPlane= new MyPlane();
				score=0;
				state=START;
				break;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if(state==PAUSING){//����ƽ���������Ϸ����
				state=RUNNING;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(state==RUNNING){//����Ƴ���������Ϸ��ͣ
				state=PAUSING;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if(state==RUNNING){//�û��ɻ���������ƶ���
				int x=e.getX();
				int y=e.getY();
				PlayerPlane.moveto(x,y);
			}
		}

	}
	public void startGame(){
		final int delay=10;
		final int frequency=10;
		MouseAdapter mouseAdapter=new MyMouseAdapter();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		Timer timer= new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(state==RUNNING){
					enterFlying();
					takeAStep();
					shootABullet();
					isHit();
					checkOutofBorder();
					checkGameOver();
				}
				repaint();
			}
		}, delay,frequency);
	}
	public void enterFlying(){
		
	}
	public void takeAStep(){
		
	}
	public void shootABullet(){
		
	}
	public void isHit(){
		
	}
	public void checkOutofBorder(){
		
	}
	public void checkGameOver(){
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("��ɻ�С��Ϸ");
		PlaneShootingGame game= new PlaneShootingGame();
		frame.add(game);
		frame.setSize(WIDTH, HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setIconImage(image);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.startGame();
	}
}
