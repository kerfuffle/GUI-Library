package net.kerfuffle.Utilities.GUI;

import java.awt.Image;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import static java.lang.Math.*;
import static net.kerfuffle.Utilities.GUI.DavisGUI.*;
import static net.kerfuffle.Utilities.GUI.GLFWUtil.*;
import static net.kerfuffle.Utilities.GUI.IOUtil.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.*;

public class DavisImage implements DavisObject{


	private final ByteBuffer image;

	private final int w;
	private final int h;
	private final int comp;

	private long window;

	private Callback debugProc;

	public DavisImage(String imagePath) {
		
		ByteBuffer imageBuffer;
		try {
			imageBuffer = ioResourceToByteBuffer(imagePath, 8 * 1024);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);

		if ( !stbi_info_from_memory(imageBuffer, w, h, comp) )
			throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());

//		System.out.println("Image width: " + w.get(0));
//		System.out.println("Image height: " + h.get(0));
//		System.out.println("Image components: " + comp.get(0));
//		System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer));

		// Decode the image
		image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
		if ( image == null )
			throw new RuntimeException("Failed to load image: " + stbi_failure_reason());

		this.w = w.get(0);
		this.h = h.get(0);
		this.comp = comp.get(0);
		
		DavisGUI.DavisObjects.add(this);
		
		init();
	}
	
	private void init()
	{
		int texID = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, texID);

		if ( comp == 3 ) {
			if ( (w & 3) != 0 )
				glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (w & 1));
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w, h, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
		} else {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	}
	
	public void draw(float x, float y, float w, float h)
	{
		init();
		
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


	public void finish()
	{
		stbi_image_free(image);
	}

}
