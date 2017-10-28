package com.lin.parctice.planegame;

import java.util.Random;

public class EnermyPlane extends FlyingObjects implements Enermy {
	public int speed;
	public EnermyPlane() {
		image=PlaneShootingGame.enermy;
		height=image.getHeight();
		width=image.getWidth();
		Random rand = new Random();
		y=-height;
		x=rand.nextInt(PlaneShootingGame.WIDTH-width);
		speed=rand.nextInt(3)+2;
	}
	@Override
	public boolean OutOfBoder() {
		return y>PlaneShootingGame.HEIGHT;
	}

	@Override
	public void step() {
		y+=speed;
	}
	@Override
	public int getScore() {
		return 5;
	}

}
