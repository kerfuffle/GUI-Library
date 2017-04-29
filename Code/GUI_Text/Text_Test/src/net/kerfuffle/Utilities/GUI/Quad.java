package net.kerfuffle.Utilities.GUI;

import static net.kerfuffle.Utilities.GUI.DavisGUI.*;

public class Quad {

	public float x, y, w, h;
	private RGB color;
	private DavisImage di;
	private Quad border;

	public Quad(float x, float y, float w, float h, RGB color)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.color=color;
	}
	
	public void setOuterBorder(int width, int height, RGB color)
	{
		border = new Quad(x-width, y-width, w+(width*2), h+(height*2), color);
	}
	public void setInnerBorder(int width, int height, RGB color)
	{
		border = new Quad(x,y,w,h,color);
		this.x += width;
		this.y += height;
		this.w-=2*width;
		this.h-=2*height;
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
			
			if (border != null)
			{
				border.draw();
			}
			
		}
		else
		{
			color(new RGB(1,1,1));
			di.draw(x, y, w, h);
		}
	}
	
}
