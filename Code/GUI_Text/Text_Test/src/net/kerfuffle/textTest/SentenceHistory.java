package net.kerfuffle.textTest;

import static net.kerfuffle.Utilities.GUI.DavisGUI.*;

import java.util.ArrayList;

import net.kerfuffle.Utilities.GUI.RGB;
import net.kerfuffle.Utilities.GUI.Text.Font;

public class SentenceHistory {

	private ArrayList<String> history = new ArrayList<String>();
	private Font font;
	
	public SentenceHistory(Font font)
	{
		this.font = font;
	}
	
	public void addSentence(String str)
	{
		history.add(str);
	}
	
	public void draw(int windowWidth)
	{
		color(new RGB(0,1,0));
		lineH(-windowWidth/2, 150, windowWidth);
		
		if (history.size()>0)
		{
			font.drawText(history.get(history.size()-1), -1300/2, 160);
		}
	}
	
}
