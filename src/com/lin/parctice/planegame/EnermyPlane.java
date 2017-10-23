package com.lin.parctice.planegame;

import java.util.Random;

public class EnermyPlane extends FlyingObjects implements Enermy {
	public int speed=3;
	public EnermyPlane() {
		height=image.getHeight();
		width=image.getWidth();
		Random rand = new Random();
		y=-height;
		x=rand.nextInt(PlaneShootingGame.WIDTH-width);
	}
	@Override
	public boolean OutOfBoder() {
		return y>PlaneShootingGame.HEIGHT;
	}

	@Override
	public void step() {
		y+=speed;
	}

}
