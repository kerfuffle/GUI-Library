package net.kerfuffle.Utilities;

import static net.kerfuffle.Utilities.DavisGUI.*;

public class Quad {

	public float x, y, w, h;
	private RGB color;
	private DavisImage di;
	

	public Quad(float x, float y, float w, float h, RGB color)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.color=color;
	}
	
	public void setTexture(String path)
	{
		di = new DavisImage(path);
	}

	public RGB getColor()
	{
		return color;
	}
	
	public void setColor(RGB color)
	{
		this.color=color;
	}
	
	public void draw()
	{
		if (di == null)
		{
			color(color);
			quad(x,y,w,h);
		}
		else
		{
			color(new RGB(1,1,1));
			di.draw(x, y, w, h);
		}
	}
	
}
