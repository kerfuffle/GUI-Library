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
import net.kerfuffle.Utilities.Text;
import net.kerfuffle.Utilities.DavisGUI.PNG;
import net.kerfuffle.Utilities.DavisImage;

import java.io.IOException;
import java.nio.*;

import static net.kerfuffle.Utilities.DavisGUI.getPNG;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBEasyFont.*;


public class HelloWorld extends DavisGUI{

	public static final int WIDTH = 1000, HEIGHT = 700;
	
	private Player p;// = new Player(new Quad(0f, 0f, 50f, 50f, new RGB(0,1,0)), true);
	private Text t;
	private Quad base;
	
	public HelloWorld()
	{
		super("Hey", WIDTH, HEIGHT);
		
	
		
		
	}
	
	public static PNG pic;
	public static void main(String args[]) throws IOException
	{
		pic = getPNG("res/images.png");
		
		new HelloWorld().run();
	}

	
	DavisImage di;
	public void	childInit()
	{
		p = new Player(new Quad(0-25, 0-25, 50, 50, new RGB(0,1,0)), GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D, true);
		p.setZoomKeys(GLFW_KEY_O, GLFW_KEY_P);
		p.setZoomSpeed(1.01f);
		
		t = new Text("Herro There?", 72);
		
		base = new Quad(250, 250, 1, 1, new RGB(1,1,0));
		
		ByteBuffer charBuffer = BufferUtils.createByteBuffer(2 * 270);
		
		di = new DavisImage("res/images.png");
		
		//t = stb_easy_font_print(0,0,"hi",null,charBuffer);
		//glVertexPointer(2, GL_FLOAT, 16, charBuffer);
	}
	
	public void childLoop() 
	{
		glColor3f(0, 1, 0);
	
		p.update();
		
		glColor3f(1,1,1);
		t.drawText();
		
		base.draw();
		
		glColor3f(1,1,1);
		di.draw();
		
		glColor3f(1,1,1);
		quadTex(100, 100, 100, 100, pic);
		
		
		//glDrawArrays(GL_QUADS, 0, t*4);
		
		/*(glColor3f(1, 1, 0);
		glBegin(GL_QUADS);
		glVertex2f(50f, 50f);
		glVertex2f(50f+50f, 50f);
		glVertex2f(50f+50f, 50f+50f);
		glVertex2f(50f, 50f+50f);
		glEnd();*/
		
	}
	
	
}