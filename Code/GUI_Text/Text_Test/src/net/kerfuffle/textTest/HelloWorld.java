package net.kerfuffle.textTest;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import net.kerfuffle.Utilities.Camera2D;
import net.kerfuffle.Utilities.DavisGUI;
import net.kerfuffle.Utilities.Player;
import net.kerfuffle.Utilities.Polygon;
import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;
import net.kerfuffle.Utilities.Text;
import net.kerfuffle.Utilities.DavisImage;

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
	private Polygon poly;
	
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
		
		/*poly = new Polygon(-200, -200);
		poly.addRelativeVertex(-50, -50);
		poly.addRelativeVertex(50,  50);
		poly.addRelativeVertex(100,  100);*/
	}
	
	public void childLoop() 
	{
		glColor3f(1,0,0);
		poly.draw();
		
		
		p.update();
	}
	
	
}