package net.kerfuffle.Utilities.GUI;

import static net.kerfuffle.Utilities.GUI.DavisGUI.*;

import org.lwjgl.glfw.GLFW;

public class Scrollable {

	private float x, y, maxX, maxY, minX, minY;
	private float step;
	
	public Scrollable(float x, float y, float step, float maxX, float maxY)
	{
		this.x=x;
		this.y=y;
		this.step=step;
		this.maxX=maxX;
		this.minY=maxY;
		minX = x;
		maxY = y;
	}
	
	public void update()
	{
		if (keyDown(GLFW.GLFW_KEY_UP))
		{
			
				y-=step;
			
		}
		if (keyDown(GLFW.GLFW_KEY_DOWN))
		{
			
				y+=step;
			
		}
	}
	
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
}
