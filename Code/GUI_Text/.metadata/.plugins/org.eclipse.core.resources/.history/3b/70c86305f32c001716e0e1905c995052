
package net.kerfuffle.textTest;

import net.kerfuffle.Utilities.GUI.Text.Font;

public class Letter {

	private char letter;
	private Font font;
	public boolean visible = true;
	public float x, y;
	
	public Letter(char letter, Font font, float x, float y)
	{
		this.letter = letter;
		this.font = font;
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
	
	
	public char getLetter()
	{
		return letter;
	}
	
}
