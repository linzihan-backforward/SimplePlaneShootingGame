package com.lin.parctice.planegame;

import java.util.Random;

public class FlyingAwards extends FlyingObjects implements Awards {
	private int xSpeed=1;
	private int ySpeed=2;
	private int awardType;
	public FlyingAwards() {
		this.image=PlaneShootingGame.award;
		height=image.getHeight();
		width=image.getWidth();
		y=-height;
		Random rand =new Random();
		x=rand.nextInt(PlaneShootingGame.WIDTH-width);
	}
	@Override
	public int getAwardType() {
		return awardType;
	}

	@Override
	public boolean OutOfBoder() {
		return y>PlaneShootingGame.HEIGHT;
	}

	@Override
	public void step() {
		x+=xSpeed;
		y+=ySpeed;
		if(x>PlaneShootingGame.WIDTH-width){
			xSpeed=-1;
		}
		if(x<0){
			xSpeed=1;
		}
	}

}
