package net.kerfuffle.textTest;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import net.kerfuffle.Utilities.GUI.Scrollable;
import net.kerfuffle.Utilities.GUI.Text.Font;
import static net.kerfuffle.Utilities.GUI.DavisGUI.*;

public class History {
	
	private class Sentence
	{
		private float x, y;
		private String str;
		
		public Sentence(String str, float x, float y)
		{
			this.str = str;
			this.x=x;
			this.y=y;
		}
		
		public void draw(Font font)
		{
			font.drawText(str, x, y);
		}
		public void bumpUp(int i)
		{
			y += i+20;
		}
	}
	
	//private StringBuilder history = new StringBuilder();
	private ArrayList<Sentence> history = new ArrayList<Sentence>();
	private Font font;
	private float windowWidth,windowHeight;
	private float originY;
	
	public History(Font font, float windowWidth, float windowHeight)
	{
		this.font = font;
		this.windowWidth=windowWidth;
		this.windowHeight=windowHeight;
		originY = (-windowHeight/2)+50;
	}
	
	public void draw()
	{
		if (keyDown(GLFW.GLFW_KEY_UP))
		{
			for (Sentence s : history)
			{
				//if (history.get(0).y + font.getHeight(history.get(0).str) > )
				//{
					s.y-=5;
				//}
			}
		}
		if (keyDown(GLFW.GLFW_KEY_DOWN))
		{
			for (Sentence s : history)
			{
				if (history.get(history.size()-1).y < originY)
				{
					s.y+=5;
				}
			}
		}
		
		for (Sentence s : history)
		{
			s.draw(font);
		}
	}
	
	public void addSentence(String str)
	{
		str = removeChar(str, '\n');
		str = addLines(str);
		
		float y = originY;
		if (!history.isEmpty())
		{
			y = history.get(history.size()-1).y;
		}
		
		for (Sentence ss : history)
		{
			ss.bumpUp(font.getHeight(str));
		}
		
		Sentence s = new Sentence(str, -windowWidth/2 + 10, y);
		history.add(s);
	}
	
	private String addLines(String str)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		
		for (int i = 0; i < sb.length(); i++)
		{
			String s = sb.toString().substring(0, i+1);
			
			if (font.getWidth(s) > windowWidth-20)
			{
				sb.insert(i, '\n');
			}
		}
		return sb.toString();
	}
	
	private String removeChar(String str, char c)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		for (int i = sb.length()-1; i > 0; i--)
		{
			if (sb.charAt(i) == c)
			{
				sb.deleteCharAt(i);
			}
		}
		return sb.toString();
	}
}
