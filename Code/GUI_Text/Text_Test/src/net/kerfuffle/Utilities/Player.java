package net.kerfuffle.Utilities;

import static net.kerfuffle.Utilities.DavisGUI.*;

public class Player {

	private Quad box;
	private float speed = 5;
	
	private int keyUp, keyDown, keyLeft, keyRight;
	
	public Player (Quad box)
	{
		this.box = box;
	}
	
	public void setKeys(int up, int down, int left, int right)
	{
		keyUp = up;
		keyDown = down;
		keyRight = right;
		keyLeft = left;
	}
	public void setSpeed(float speed)
	{
		this.speed = speed;
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
		box.draw();
	}
	
}
