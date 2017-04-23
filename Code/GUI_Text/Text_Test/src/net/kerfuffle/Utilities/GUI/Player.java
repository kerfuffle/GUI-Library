package net.kerfuffle.Utilities.GUI;

import static net.kerfuffle.Utilities.GUI.DavisGUI.*;
import static org.lwjgl.opengl.GL11.*;

public class Player {

	private Quad box;
	private float speed = 5;
	private boolean cameraFollow = false;
	private Camera2D camera;
	
	private int keyUp, keyDown, keyLeft, keyRight;
	
	public Player (Quad box, int up, int down, int left, int right, boolean cameraFollow)
	{
		this.box = box;
		this.cameraFollow = cameraFollow;
		
		keyUp = up;
		keyDown = down;
		keyRight = right;
		keyLeft = left;
		
		if (cameraFollow)
		{
			glTranslatef(-(box.x+(box.w/2)), -(box.y+(box.h/2)), 0);
			camera = new Camera2D(box.x+(box.w/2),box.y+(box.h/2), speed, keyUp, keyDown, keyLeft, keyRight);
		}
	}
	
	public void setTexture(String path)
	{
		box.setTexture(path);
	}
	
	public void setKeys(int up, int down, int left, int right)
	{
		keyUp = up;
		keyDown = down;
		keyRight = right;
		keyLeft = left;
	}
	public void setZoomKeys(int zIn, int zOut)
	{
		camera.setZoomKeys(zIn, zOut);
	}
	public void setZoomSpeed(float s)
	{
		camera.setZoomSpeed(s);
	}
	public void setSpeed(float speed)
	{
		this.speed = speed;
		camera.setSpeed(speed);
	}
	public void cameraFollow(boolean b)
	{
		cameraFollow = b;
	}
	
	public Quad getQuad()
	{
		return box;
	}
	
	
	private void checkMovement()
	{
		if (keyDown(keyUp))
		{
			box.y += speed;
		}
		if (keyDown(keyDown))
		{
			box.y -= speed;
		}
		if (keyDown(keyLeft))
		{
			box.x -= speed;
		}
		if (keyDown(keyRight))
		{
			box.x += speed;
		}
	}

	
	
	public void update()
	{
		
		checkMovement();
		
		//System.out.println("PLAYER: " + box.x + ", " + box.y);
		
		// MAKE SURE camera follow comes after checkMovement or zoom will be out of sync with translational movement
		float centerX = box.x+(box.w/2), centerY = box.y+(box.h/2);
		if (cameraFollow)
		{
			camera.setFocusZoom(centerX, centerY);
			camera.update();
		}
		
		box.draw();
		
		
		
	
	}
	
}
