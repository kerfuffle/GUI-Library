package net.kerfuffle.Utilities.GUI;

import static org.lwjgl.opengl.GL11.*;
import static net.kerfuffle.Utilities.GUI.DavisGUI.*;
import static org.lwjgl.glfw.GLFW.*;

public class Camera2D {

	private int up, down, left, right, zIn = -1, zOut = -1;
	private float x, y, speed;
	
	private float focusZoomX, focusZoomY, zoomSpeed = 1.05f;
	private float startX, startY;
	
	
	public Camera2D(float x, float y, float speed, int up, int down, int left, int right)
	{
		this.x=x;
		this.y=y;
		//this.startX = x;
		//this.startY = y;
		this.up=up;
		this.down=down;
		this.right=right;
		this.left=left;
		this.speed = speed;
	}
	
	public void setSpeed(float speed)
	{
		this.speed=speed;
	}
	public void setZoomKeys(int zIn, int zOut)
	{
		this.zIn = zIn;
		this.zOut = zOut;
	}
	public void setFocusZoom(float x, float y)
	{
		focusZoomX = x;
		focusZoomY = y;
	}
	public void setZoomSpeed(float s)
	{
		zoomSpeed = s;
	}
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	
	private void checkKeys()
	{
		if (keyDown(up))
		{
			y+=speed;
			offsetScreen(0, -speed, 0);
		}
		if (keyDown(down))
		{
			y-=speed;
			offsetScreen(0, speed, 0);
		}
		if (keyDown(left))
		{
			x-=speed;
			offsetScreen(speed, 0, 0);
		}
		if (keyDown(right))
		{
			x+=speed;
			offsetScreen(-speed, 0, 0);
		}
		if (zOut != -1)
		{
			if(keyDown(zOut))
			{
				//glTranslatef(0,0,speed);
				glTranslatef(focusZoomX, focusZoomY, 0);
				glScalef(1/zoomSpeed, 1/zoomSpeed, 1);
				glTranslatef(-(focusZoomX), -(focusZoomY), 0);
			}
		}
		if (zIn != -1)
		{
			if(keyDown(zIn))						//-1 means not being used
			{
				//glTranslatef(0,0,-speed);
				//System.out.println(focusZoomX+","+focusZoomY);
				glTranslatef(focusZoomX, focusZoomY, 0);
				glScalef(zoomSpeed, zoomSpeed,1);
				glTranslatef(-(focusZoomX), -(focusZoomY), 0);
			}
		}
		
	}
	
	
	public void update()
	{
		checkKeys();
		
		
	}
	
	
}
