package net.kerfuffle.textTest;

import net.kerfuffle.Utilities.GUI.Text.Font;

public class LetterSet {

	public static final int LOWER = 0, UPPER = 1;
	
	public static char[] lower = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static char[] upper = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	private int set = LOWER;
	
	public Letter currentSet[] = new Letter[26];
	
	private Font font;
	private float y, leftBoundX, rightBoundX;
	private int amountVisible;
	private int currentLength = 26;
	private int visibleIndex[];
	
	public LetterSet(Font font, int amountVisible, float leftBoundX, float rightBoundX, float y)
	{
		this.font = font;
		this.amountVisible = amountVisible;
		this.leftBoundX = leftBoundX; 
		this.rightBoundX = rightBoundX;
		this.y=y;
		
		visibleIndex = new int[amountVisible];
		for (int i = 0; i < visibleIndex.length; i++)
		{
			visibleIndex[i] = i;
		}
		
		setSet(LOWER);
	}
	
	public void draw()
	{
		for (int i = 0; i < currentSet.length; i++)
		{
			for (int j = 0; j < visibleIndex.length; j++)
			{
				if (i == visibleIndex[j])
				{
					currentSet[i].draw();
				}
			}
		}
	}
	
	public void shiftLeft()
	{
		for (int i = 0; i < visibleIndex.length; i++)
		{
			if (visibleIndex[i] == currentLength-1)
			{
				visibleIndex[i] = 0;
				continue;
			}
			visibleIndex[i] += 1;
		}
		
		for (int i = 0; i < currentSet.length; i++)
		{
			currentSet[i].x -= spaceBetween();
		}
	}
	public void shiftRight()
	{
		for (int i = 0; i < visibleIndex.length; i++)
		{
			if (visibleIndex[i] == 0)
			{
				visibleIndex[i] = currentLength-1;
				continue;
			}
			visibleIndex[i] -= 1;
		}
		for (int i = 0; i < currentSet.length; i++)
		{
			currentSet[i].x += spaceBetween();
		}
	}
	
	private float spaceBetween()
	{
		float ret = (Math.abs(leftBoundX) + Math.abs(rightBoundX)) / amountVisible;
		return ret;
	}
	
	public void setSet(int set)
	{
		this.set=set;
		
		if (set == LOWER)
		{
			currentSet = new Letter[lower.length];
			for (int i = 0; i < lower.length; i++)
			{
				currentSet[i] = new Letter(lower[i], font, (leftBoundX+font.getWidth("g")) + (i * spaceBetween()), y);
			}
		}
		if (set == UPPER)
		{
			currentSet = new Letter[upper.length];
			for (int i = 0; i < upper.length; i++)
			{
				currentSet[i] = new Letter(upper[i], font, (leftBoundX+font.getWidth("G")) + (i * spaceBetween()), y);
			}
		}
	}

}
