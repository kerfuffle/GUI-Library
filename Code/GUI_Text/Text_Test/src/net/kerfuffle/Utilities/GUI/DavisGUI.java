package net.kerfuffle.Utilities.GUI;

import static org.lwjgl.glfw.GLFW.*;
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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
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
import net.kerfuffle.Utilities.GUI.Text.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.stb.STBEasyFont.stb_easy_font_print;


public abstract class DavisGUI {

	public static ArrayList<DavisObject> DavisObjects = new ArrayList<DavisObject>();
	
	protected static long window;

	protected String windowName;
	protected static int windowWidth;
	protected static int windowHeight;
	public static float aspectRatio;
	public static float scale = 1;
	
	private boolean fullScreen = false;
	
	public static float originX = 0, originY = 0;
	
	
	public DavisGUI(String windowName, float windowWidth, float windowHeight)
	{
		this.windowName = windowName;
		this.windowWidth = (int) windowWidth;
		this.windowHeight = (int) windowHeight;
		
		aspectRatio = windowHeight/windowWidth;
		
		fullScreen = false;
		//System.out.print(aspectRatio);
	}
	
	public DavisGUI(String windowName)
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); 
		int width = gd.getDisplayMode().getWidth(); 
		int height = gd.getDisplayMode().getHeight();
		
		this.windowName = windowName;
		
		windowWidth = width;
		windowHeight = height;
		
		aspectRatio = windowHeight/windowWidth;
		
		fullScreen = true;
	}
	public void setScreenSize(int windowWidth, int windowHeight)
	{
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		aspectRatio = windowHeight/windowWidth;
		
		fullScreen = false;
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

		
		
		if (!fullScreen)
		{
			window = glfwCreateWindow(windowWidth, windowHeight, windowName, NULL, NULL);
		}
		else
		{
			window = glfwCreateWindow(windowWidth, windowHeight, windowName, glfwGetPrimaryMonitor(), NULL);
		}
		
		
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
		if (color != null)
		{
			glColor3f(color.R(), color.G(), color.B());
		}
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
	
	
	public static float[] getMousePos()
	{
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		
		float ret[] = new float[2];
		glfwGetCursorPos(window, x, y);
		
		ret[0] = (float) x.get(0);
		ret[1] = (float) y.get(0);
		
		ret[0] = (originX - windowWidth/2) + ret[0]; // scales with center of screen at 0,0
		ret[1] = (originY + windowHeight/2) - ret[1]; //scales and flips

		return ret;
	}
	
	public static boolean isCollide(Quad q1, Quad q2)
	{
		float area1 = q1.w*q1.h;
		float area2 = q2.w*q2.h;
		
		if (area1 <= area2)
		{
			//check if q2 is stepping inside of q1
			// basically check if all four points on q1 are in bounds of q2
			if (isIn(q1.x, q1.y, q2) || isIn(q1.x+q1.w, q1.y, q2) || isIn(q1.x+q1.w, q1.y+q1.h, q2) || isIn(q1.x, q1.y+q1.h, q2))
			{
				return true;
			}
		}
		else if (area1 > area2)
		{
			//check if q2 is stepping inside q1
			if (isIn(q2.x, q2.y, q1) || isIn(q2.x+q2.w, q2.y, q1) || isIn(q2.x+q2.w, q2.y+q2.h, q1) || isIn(q2.x, q2.y+q2.h, q1))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isIn(float mx, float my, Quad q)
	{
		if (mx > q.x && mx < q.x+q.w && my > q.y && my < q.y+q.h)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isClick(float x, float y, float w, float h)
	{
		if (isHover(x,y,w,h) && checkMouse(GLFW_MOUSE_BUTTON_LEFT))
		{
			return true;
		}
		return false;
	}
	
	public static boolean mouseStates[] = new boolean[2];
	public static boolean checkMouse(int i)
	{
		int state = glfwGetMouseButton(window, i);
		
		if ((state == GLFW.GLFW_PRESS) != mouseStates[i])
		{
			return mouseStates[i] = !mouseStates[i];
		}
		else
		{
			return false;
		}
	}
	
	public static boolean keyStates[] = new boolean[1000];
	public static boolean checkKey(int i)
	{
		int state = GLFW.glfwGetKey(window, i);
		
		if ((state == GLFW.GLFW_PRESS) != keyStates[i])
		{
			return keyStates[i] = !keyStates[i];
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isHover(float x, float y, float w, float h)
	{
		float mousePos[] = new float[2];
		mousePos = getMousePos();
		
		if (w > 0 && h > 0)
		{
			if (mousePos[0] > x && mousePos[0] < x+w && mousePos[1] > y && mousePos[1] < y+h)
			{
				return true;
			}
		}
		else if (w < 0 && h > 0)
		{
			if (mousePos[0] < x && mousePos[0] > x+w && mousePos[1] > y && mousePos[1] < y+h)
			{
				return true;
			}
		}
		else if (w > 0 && h < 0)
		{
			if (mousePos[0] > x && mousePos[0] < x+w && mousePos[1] < y && mousePos[1] > y+h)
			{
				return true;
			}
		}
		else if (w < 0 && h < 0)
		{
			if (mousePos[0] < x && mousePos[0] > x+w && mousePos[1] < y && mousePos[1] > y+h)
			{
				return true;
			}
		}
		
		
		
		return false;
	}
	
	public static void drawTextureRegion(Texture texture, float x, float y, float tx, float ty, float tw, float th, RGB c)
	{
		
		float s1 = tx / texture.getWidth();
        float t1 = ty / texture.getHeight();
        float s2 = (tx + tw) / texture.getWidth();
        float t2 = (ty + th) / texture.getHeight();
		
		color(c);
		quadTex(x, y, tw, th, s1, t1, s2, t2, texture);
		//quadTex(x,y,100,100,texture);
	}
	
	public static void quadTex(float x, float y, float w, float h, float t1x, float t1y, float t2x, float t2y, Texture tex)
	{
		//tex.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2f(t1x, t1y);
		glVertex2f(x, y);
		glTexCoord2f(t1x, t2y);
		glVertex2f(x, y + h);
		glTexCoord2f(t2x, t2y);	
		glVertex2f(x + w, y + h);
		glTexCoord2f(t2x, t1y); 
		glVertex2f(x + w, y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void quadTex(float x, float y, float w, float h, Texture tex)
	{
		tex.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2f(1, 1);		
		glVertex2f(x, y);
		glTexCoord2f(1, 0); 	
		glVertex2f(x, y + h);
		glTexCoord2f(0, 0);		
		glVertex2f(x + w, y + h);
		glTexCoord2f(0, 1);		
		glVertex2f(x + w, y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void offsetScreen(float x, float y, float z)
	{
		originX += -x;
		originY += -y;
		glTranslatef(x,y,z);
	}
	
	public static Quad getTriangleBounds(Triangle tri)
	{
		Vertex[] vs = tri.getRelatives();
		float oX = tri.getX();
		float oY = tri.getY();
		
		
		float x = oX+vs[0].x;
		float y = oY+vs[0].y;
		float w=0,h=0;
		if (Math.abs(vs[1].x) > Math.abs(vs[2].x))
		{
			w = -(vs[0].x - vs[1].x);
		}
		else if (Math.abs(vs[1].x) <= Math.abs(vs[2].x))
		{
			w = -(vs[0].x - vs[2].x);
		}
		
		if (Math.abs(vs[1].y) > Math.abs(vs[2].y))
		{
			
			h = -(vs[0].y - vs[1].y);
		}
		else if (Math.abs(vs[1].y) <= Math.abs(vs[2].y))
		{
			h = -(vs[0].y - vs[2].y);
		}
		
		Quad ret = new Quad(x,y,w,h,null);
		
		return ret;
	}
	
	public static void color(RGB c)
	{
		glColor3f(c.R(), c.G(), c.B());
	}
	
	public static void line(float x, float y, float x2, float y2)
	{
		glBegin(GL_LINES);
		glVertex2f(x,y);
		glVertex2f(x2, y2);
		glEnd();
	}
	
	public static void lineH(float x, float y, float length)
	{
		line(x, y, x+length, y);
	}
	public static void lineV(float x, float y, float length)
	{
		line(x,y,x,y+length);
	}
	
	public static void floatBufferExample()
	{
		int vertices = 3;

		int vertex_size = 3; // X, Y, Z,
		int color_size = 3; // R, G, B,

		FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices * vertex_size);
		vertex_data.put(new float[] { -100f, -100f, 0f, });
		vertex_data.put(new float[] { 100f, -100f, 0f, });
		vertex_data.put(new float[] { 100f, 100f, 0f, });
		vertex_data.flip();

		FloatBuffer color_data = BufferUtils.createFloatBuffer(vertices * color_size);
		color_data.put(new float[] { 100f, 100f, 0f, });
		color_data.put(new float[] { 100f, 100f, 0f, });
		color_data.put(new float[] { 100f, 100f, 0f, });
		color_data.flip();

		int vbo_vertex_handle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
		glBufferData(GL_ARRAY_BUFFER, vertex_data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		int vbo_color_handle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
		glBufferData(GL_ARRAY_BUFFER, color_data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
		glVertexPointer(vertex_size, GL_FLOAT, 0, 0l);

		glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
		glColorPointer(color_size, GL_FLOAT, 0, 0l);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);

		glDrawArrays(GL_TRIANGLES, 0, vertices);

		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);


	}
}
