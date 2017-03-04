package net.kerfuffle.textTest;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import net.kerfuffle.Utilities.Camera2D;
import net.kerfuffle.Utilities.DavisGUI;
import net.kerfuffle.Utilities.Player;
import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBEasyFont.*;


public class HelloWorld extends DavisGUI{

	private Player p;// = new Player(new Quad(0f, 0f, 50f, 50f, new RGB(0,1,0)), true);
	
	public HelloWorld()
	{
		super("Hey", 600, 600);
		
	
		
		
	}
	
	public static void main(String args[])
	{
		new HelloWorld().run();
	}

	public void	childInit()
	{
		p = new Player(new Quad(0-25, 0-25, 50f, 50f, new RGB(0,1,0)), GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D, true);
		p.setZoomKeys(GLFW_KEY_O, GLFW_KEY_P);
	}
	
	public void childLoop() 
	{
		glColor3f(0, 1, 0);
	
		p.update();
		
		
		/*(glColor3f(1, 1, 0);
		glBegin(GL_QUADS);
		glVertex2f(50f, 50f);
		glVertex2f(50f+50f, 50f);
		glVertex2f(50f+50f, 50f+50f);
		glVertex2f(50f, 50f+50f);
		glEnd();*/
		
	}
	
	
}