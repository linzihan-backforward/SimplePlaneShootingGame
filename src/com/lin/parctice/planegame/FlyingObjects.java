package com.lin.parctice.planegame;

import java.awt.image.BufferedImage;

public abstract class FlyingObjects {
	protected int x;
	protected int y;
	protected int height;
	protected int width;
	protected BufferedImage image;
	public int getX(){
		return x;
	}
	public void setX(int x){
		this.x=x;
	}
	public int getY(){
		return y;
	}
	public void setY(int y){
		this.y=y;
	}
	public int getHeight(){
		return height;
	}
	public void setHeight(int height){
		this.height=height;
	}
	public int getWidth(){
		return width;
	}
	public void setWeight(int width){
		this.width=width;
	}
	public BufferedImage GetImage(){
		return image;
	}
	public void setImage(BufferedImage image){
		this.image=image;
	}
	public abstract boolean OutOfBoder();//�ж��Ƿ�����Ϸ������
	
	
	public abstract void step();//��������ǰ��һ��
	public boolean IsGetHit(Bullet bullet){
		return ((bullet.x>this.x)&&(bullet.x<this.x+width))&&((bullet.y>this.y)&&(bullet.y<this.y+height));
	}
	public boolean isPickByPlane(MyPlane mp){
		int otherLeftX=mp.x-width/2;
		int otherRightX=mp.x+width/2+mp.width;
		int otherTopY=mp.y-height/2;
		int otherBottomY=mp.y+height/2+mp.height;
		int thisX=x+width/2;//����ҷɻ������ĵ���Ϊ�Ƚ�����
		int thisY=y+height/2;
		return thisX>otherLeftX&&thisX<otherRightX&&thisY>otherTopY&&thisY<otherBottomY;
		//���ĵ�����������������
	}
}
