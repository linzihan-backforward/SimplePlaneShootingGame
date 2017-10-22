package com.lin.parctice.planegame;

public class Bullet extends FlyingObjects {
	private int speed=3;
	public int GetSpeed(){
		return speed;
	}
	public void SetSpeed(int speed){
		this.speed=speed;
	}
	public Bullet(int x,int y) {
		this.x=x;
		this.y=y;
	}
	@Override
	public boolean OutOfBoder() {
		return y>(-height);
	}

	@Override
	public void step() {
		 y-=speed;
	}
}
