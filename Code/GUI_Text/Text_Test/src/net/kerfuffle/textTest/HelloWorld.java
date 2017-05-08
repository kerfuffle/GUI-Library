package net.kerfuffle.textTest;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import net.kerfuffle.Utilities.GUI.Button;
import net.kerfuffle.Utilities.GUI.Camera2D;
import net.kerfuffle.Utilities.GUI.DavisGUI;
import net.kerfuffle.Utilities.GUI.DavisImage;
import net.kerfuffle.Utilities.GUI.KeyPressListener;
import net.kerfuffle.Utilities.GUI.OnClickListener;
import net.kerfuffle.Utilities.GUI.Player;
import net.kerfuffle.Utilities.GUI.Polygon;
import net.kerfuffle.Utilities.GUI.Quad;
import net.kerfuffle.Utilities.GUI.RGB;
import net.kerfuffle.Utilities.GUI.Triangle;
import net.kerfuffle.Utilities.GUI.Text.Font;

import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Renderer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBEasyFont.*;
import static org.lwjgl.opengl.GL15.*;

public class HelloWorld extends DavisGUI{

	public static final int WIDTH = 1000, HEIGHT = 700;

	private Player p;
	private Button leftArrow, rightArrow;

	private Font letter_font, letter_select_font, word_font;
	private LetterSet letterSet;
	private WordSet wordSet;
	private Quad center;

	public HelloWorld()
	{
		//super("Hey", WIDTH, HEIGHT);
		super("Hey");
	}

	public static void main(String args[]) throws IOException
	{
		new HelloWorld().run();
	}

	public void	childInit()
	{
		center = new Quad(-50, -310, 100,100, new RGB(0,0,0));
		center.setOuterBorder(10, 10, new RGB(1,0,0));
		
		p = new Player(new Quad(0-0.5f, 0-0.5f, 1, 1, new RGB(0,1,0)), GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D, true);
		p.setZoomKeys(GLFW_KEY_O, GLFW_KEY_P);
		p.setZoomSpeed(1.01f);
		//p.setTexture("res/A.png");


		Triangle leftTri = new Triangle(-600, -300, new RGB(1,0,1));
		leftTri.setRelativeVertex(0, 0, 0);
		leftTri.setRelativeVertex(1, 0, 100);
		leftTri.setRelativeVertex(2, -50, 50);

		leftArrow = new Button(leftTri);
		leftArrow.setOnClickListener(new OnClickListener()
		{
			public void onClick() 
			{
				System.out.println("left triggered");
			}
		});
		leftArrow.setKeyPressListener(new KeyPressListener(GLFW.GLFW_KEY_LEFT){
			public void onPress()
			{
				leftArrow.setColor(new RGB((float)Math.random(), (float)Math.random(), (float)Math.random()));
			}
		});

		Triangle rightTri = new Triangle(600, -300, new RGB(1,0,1));
		rightTri.setRelativeVertex(0, 0, 0);
		rightTri.setRelativeVertex(1, 0, 100);
		rightTri.setRelativeVertex(2, 50, 50);

		rightArrow = new Button(rightTri);
		rightArrow.setOnClickListener(new OnClickListener()
		{
			public void onClick() 
			{
				System.out.println("right triggered");
			}
		});
		rightArrow.setKeyPressListener(new KeyPressListener(GLFW.GLFW_KEY_RIGHT){
			public void onPress()
			{
				rightArrow.setColor(new RGB((float)Math.random(), (float)Math.random(), (float)Math.random()));
			}
		});


		try {
			letter_font = new Font(new FileInputStream("res/Helvetica.ttf"), 72);
			letter_select_font = new Font(new FileInputStream("res/Helvetica.ttf"), 92);
			word_font = new Font(new FileInputStream("res/Helvetica.ttf"), 56);
		} catch (FontFormatException | IOException ex) {
			Logger.getLogger(Renderer.class.getName()).log(Level.CONFIG, null, ex);
			letter_font = new Font();
		}

		letterSet = new LetterSet(letter_font, letter_select_font, 9, -500, 500, -300);
		wordSet = new WordSet(word_font);
	
	}
	

	private boolean caps = false;

	public void childLoop() 
	{
		
		
		if (checkKey(GLFW.GLFW_KEY_LEFT))
		{
			letterSet.shiftLeft();
		}
		if (checkKey(GLFW.GLFW_KEY_RIGHT))
		{
			letterSet.shiftRight();
		}
		if (checkKey(GLFW.GLFW_KEY_CAPS_LOCK))
		{
			letterSet.setSet(LetterSet.UPPER);
		}
		if (checkKey(GLFW.GLFW_KEY_ENTER))
		{
			wordSet.addLetter(letterSet.getCurrentLetter());
		}
		if (checkKey(GLFW.GLFW_KEY_BACKSPACE))
		{
			wordSet.removeLastLetter();
		}
		if (checkKey(GLFW.GLFW_KEY_SPACE))
		{
			wordSet.addSpace();
		}
		
		
	
		letterSet.draw();
		wordSet.draw();

		leftArrow.update();
		rightArrow.update();
		center.draw();
		p.update();
	}


}