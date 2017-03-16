package net.kerfuffle.Utilities;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBEasyFont.stb_easy_font_print;


public abstract class DavisGUI {

	public static ArrayList<DavisObject> DavisObjects = new ArrayList<DavisObject>();
	
	protected static long window;

	protected String windowName;
	protected int windowWidth, windowHeight;
	public static float aspectRatio;
	public static float scale = 1;
	
	
	
	public DavisGUI(String windowName, float windowWidth, float windowHeight)
	{
		this.windowName = windowName;
		this.windowWidth = (int) windowWidth;
		this.windowHeight = (int) windowHeight;
		
		aspectRatio = windowHeight/windowWidth;
		//System.out.print(aspectRatio);
	}
	
	public void run() {

		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

	
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		
		
		
		GLFWErrorCallback.createPrint(System.err).set();

		
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

	
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); 
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

	
		window = glfwCreateWindow(windowWidth, windowHeight, windowName, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); 
		});


		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); 
			IntBuffer pHeight = stack.mallocInt(1); 

			glfwGetWindowSize(window, pWidth, pHeight);

			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} 
		
		glfwMakeContextCurrent(window);

		glfwSwapInterval(1);

		glfwShowWindow(window);
		
		
		//glEnableClientState(GL_VERTEX_ARRAY);
	}

	private void loop()
	{
		GL.createCapabilities();
		
		
		
		glEnableClientState(GL_VERTEX_ARRAY);
		//glEnableClientState(GL11.GL_3D);
		//glMatrixMode(GL_PROJECTION);	
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glEnable(GL_DEPTH_TEST);
		glOrtho(-windowWidth/2, windowWidth/2, -windowHeight/2, windowHeight/2, 0, 100);
		
		childInit();
		
		while ( !glfwWindowShouldClose(window) ) {
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
			
			childLoop();
	
			glfwSwapBuffers(window); 
		}
		
		
		// Allow all DavisObjects to carry out things needed before close
		for (DavisObject d : DavisObjects)
		{
			d.finish();
		}
	}
	
	public abstract void childLoop();
	public abstract void childInit();
	
	
	
	
	
	
	
	
	
	
	/***************************** Global Functions *****************************************************************************************************/
	
	
	
	
	public static boolean keyDown(int key)
	{
		if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS)
		{
			return true;
		}
		return false;
	}
	
	
	public static void quad(float x, float y, float w, float h)
	{
//		w*=aspectRatio;
//		
//		x*=scale;
//		y*=scale;
//		w*=scale;
//		h*=scale;
		
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x+w, y);
		glVertex2f(x+w, y+h);
		glVertex2f(x, y+h);
		glEnd();
	}
	
	public static void setColor(RGB color)
	{
		glColor3f(color.R(), color.G(), color.B());
	}

	public static void quadTex(float x, float y, float w, float h)
	{
		glEnable(GL_TEXTURE_2D);
		
		glBegin(GL_QUADS);

		glTexCoord2f(0.0f, 1.0f);
		glVertex2f(x, y);
		
		glTexCoord2f(1.0f, 1.0f);
		glVertex2f(x+w, y);
		
		glTexCoord2f(1.0f, 0.0f);
		glVertex2f(x+w, y+h);
		
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y+h);
		
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
	
	
	
	

	
}
