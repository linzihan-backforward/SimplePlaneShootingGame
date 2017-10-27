package com.lin.parctice.planegame;

public class MyPlane extends FlyingObjects {
	private int isDoubleFire;
	private int Life;
	public MyPlane() {
		Life=3;
		isDoubleFire=0;
		this.image=PlaneShootingGame.player;
		height=image.getHeight();
		width=image.getWidth();
		x=150;
		y=400;
	}
	public boolean isDoubleFired(){
		return isDoubleFire>0;
	}
	public void SetDoubleFire(){
		isDoubleFire=1;
	}
	public void deDoubleFire(){
		isDoubleFire=0;
	}
	public void addLife(){
		Life++;
	}
	public void decreaseLife() {
		Life--;
	}
	public int getLife(){
		return Life;
	}
	public void moveto(int x,int y){
		this.x=x-this.width/2;
		this.y=y-this.height/2;
	}
	@Override
	public boolean OutOfBoder() {
		return false;
	}
	public Bullet[] fire(){
		int xLoc=width/4;
		int yLoc=20;
		if(isDoubleFired()){//双倍火力
			Bullet [] bullets= new Bullet[2];
			bullets[0]=new Bullet(x+xLoc, y-yLoc);
			bullets[1]=new Bullet(x+3*xLoc, y-yLoc);
			return bullets;
		}
		else{//单倍火力
			Bullet [] bullets=new Bullet[1];
			bullets[0]=new Bullet(x+2*xLoc, y-yLoc);
			return bullets;
		}
	}
	@Override
	public void step() {
		
	}
	public boolean ishit(FlyingObjects others){
		int otherLeftX=others.x-width/2;
		int otherRightX=others.x+width/2+others.width;
		int otherTopY=others.y-height/2;
		int otherBottomY=others.y+height/2+others.height;
		int thisX=x+width/2;//以玩家飞机的中心点作为比较依据
		int thisY=y+height/2;
		return thisX>otherLeftX&&thisX<otherRightX&&thisY>otherTopY&&thisY<otherBottomY;
		//中心点在其他飞行物里面
	}
}
