package net.kerfuffle.textTest;

import net.kerfuffle.Utilities.GUI.RGB;
import net.kerfuffle.Utilities.GUI.Text.Font;

public class Letter {

	private char letter;
	private Font font;
	public float x,y;
	private boolean visible = true;
	
	public Letter(char letter, Font font, float x, float y)
	{
		this.letter = letter;
		this.font = font;
		this.x=x;
		this.y=y;
	}
	
	public void setCoords(float x, float y)
	{
		this.x=x;
		this.y=y;
	}
	
	public void draw()
	{
		if (visible)
		{
			font.drawText(String.valueOf(letter), x, y);
		}
	}
	
	
	public void setVisibility(boolean visible)
	{
		this.visible = visible;
	}
	public boolean isVisible()
	{
		return visible;
	}
}
