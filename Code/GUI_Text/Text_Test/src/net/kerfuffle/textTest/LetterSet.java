package net.kerfuffle.textTest;

import java.util.Arrays;

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
	
	public LetterSet(Font font, int amountVisible, float leftBoundX, float rightBoundX, float y)
	{
		this.font = font;
		this.amountVisible = amountVisible;
		this.leftBoundX = leftBoundX; 
		this.rightBoundX = rightBoundX;
		this.y=y;
		
		setSet(LOWER);
	}
	
	public void draw()
	{
		for (int i = 0; i < amountVisible; i++)
		{
			currentSet[i].draw();
		}
	}
	
	
	public void shiftLeft()
	{
		Letter temp[] = Arrays.copyOf(currentSet, currentSet.length);
		
		for (int i = 0; i < currentSet.length; i++)
		{
			if (i == currentSet.length-1)
			{
				currentSet[i] = temp[0];
				currentSet[i].x = leftBoundX+font.getWidth("g") + ((currentSet.length-1) * spaceBetween());
				continue;
			}
			
			currentSet[i] = temp[i+1];
			currentSet[i].x = leftBoundX+font.getWidth("g") + ((i) * spaceBetween());
		}
	}
	public void shiftRight()
	{
		Letter temp[] = Arrays.copyOf(currentSet, currentSet.length);
		
		for (int i = 0; i < currentSet.length; i++)
		{
			if (i == 0)
			{
				currentSet[i] = temp[currentSet.length-1];
				currentSet[i].x = leftBoundX+font.getWidth("g") + ((0) * spaceBetween());
				continue;
			}
			
			currentSet[i] = temp[i-1];
			currentSet[i].x = leftBoundX+font.getWidth("g") + ((i) * spaceBetween());
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
			for (Letter l : currentSet)
			{
				l.toUpperCase();
			}
		}
	}

}
