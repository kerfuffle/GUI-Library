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
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;

public abstract class DavisGUI {

	protected static long window;

	protected String windowName;
	protected int windowWidth, windowHeight;
	public static float aspectRatio;
	public static float scale = 0.01f;
	
	
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
		
		while ( !glfwWindowShouldClose(window) ) {
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
			
			childLoop();
	
			glfwSwapBuffers(window); 
		}
	}
	
	public abstract void childLoop();
	
	
	
	
	
	
	
	
	
	
	
	/***************************** Global Funtions *****************************************************************************************************/
	
	
	
	
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
		w*=aspectRatio;
		
		x*=scale;
		y*=scale;
		w*=scale;
		h*=scale;
		
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





	
}