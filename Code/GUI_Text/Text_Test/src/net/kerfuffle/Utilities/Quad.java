package net.kerfuffle.Utilities;

import static net.kerfuffle.Utilities.DavisGUI.*;

public class Quad {

	public float x, y, w, h;
	private RGB color;
	
	

	public Quad(float x, float y, float w, float h, RGB color)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.color=color;
	}

	public RGB getColor()
	{
		return color;
	}
	
	
	public void draw()
	{
		setColor(color);
		quad(x,y,w,h);
	}
	
}
