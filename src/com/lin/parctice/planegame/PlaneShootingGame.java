package com.lin.parctice.planegame;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;


public class PlaneShootingGame extends JPanel {
	public static int WIDTH;
	public static int HEIGHT;
	public int state;
	public static final int START=1;
	public static final int RUNNING=2;
	public static final int PAUSING=3;
	public static final int GAMEOVER=4;
	public int score;
	FlyingObjects[] flyings={};
	Enermy[] enermies={};
	MyPlane PlayerPlane= new MyPlane();
	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
	}
	public void paitMyPlane(Graphics g){
		g.drawImage(PlayerPlane.image, PlayerPlane.getX(), PlayerPlane.getY(), null);
	}
	public class MyMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			switch (state) {//鼠标点击开始
			case START:
				state=RUNNING;
				break;

			case GAMEOVER://鼠标点击回归初始状态
				flyings=new FlyingObjects[0];
				enermies= new Enermy[0];
				PlayerPlane= new MyPlane();
				score=0;
				state=START;
				break;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if(state==PAUSING){//鼠标移进画面则游戏继续
				state=RUNNING;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(state==RUNNING){//鼠标移出画面则游戏暂停
				state=PAUSING;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if(state==RUNNING){//用户飞机跟随鼠标移动。
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
					
				}
				repaint();
			}
		}, delay,frequency);
	}
	public static void main(String[] args) {
		

	}

}
