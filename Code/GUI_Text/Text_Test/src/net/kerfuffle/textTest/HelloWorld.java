package net.kerfuffle.textTest;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import net.kerfuffle.Utilities.Button;
import net.kerfuffle.Utilities.Camera2D;
import net.kerfuffle.Utilities.DavisGUI;
import net.kerfuffle.Utilities.Player;
import net.kerfuffle.Utilities.Polygon;
import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;
import net.kerfuffle.Utilities.Text;
import net.kerfuffle.Utilities.Triangle;
import net.kerfuffle.Utilities.DavisImage;
import net.kerfuffle.Utilities.KeyPressListener;
import net.kerfuffle.Utilities.OnClickListener;

import java.io.IOException;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBEasyFont.*;


public class HelloWorld extends DavisGUI{

	public static final int WIDTH = 1000, HEIGHT = 700;
	
	private Player p;
	private Button leftArrow, rightArrow;
	
	public HelloWorld()
	{
		super("Hey", WIDTH, HEIGHT);
	}
	
	public static void main(String args[]) throws IOException
	{
		new HelloWorld().run();
	}

	public void	childInit()
	{
		p = new Player(new Quad(0-25, 0-25, 50, 50, new RGB(0,1,0)), GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D, true);
		p.setZoomKeys(GLFW_KEY_O, GLFW_KEY_P);
		p.setZoomSpeed(1.01f);
		p.setTexture("res/A.png");
		
		
		Triangle leftTri = new Triangle(-400, -300, new RGB(1,0,1));
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
		
		Triangle rightTri = new Triangle(400, -300, new RGB(1,0,1));
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
		
	}
	
	public void childLoop() 
	{
		
		leftArrow.update();
		rightArrow.update();
		
		p.update();
	}
	
	
}