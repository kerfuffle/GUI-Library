package net.kerfuffle.Utilities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static net.kerfuffle.Utilities.IOUtil.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.*;


public class Text implements DavisObject{
	
	private String text = "banana";
	private int fontHeight = 12;
	private STBTTBakedChar.Buffer cdata;
	
	public Text(String text, int fontHeight)
	{
		this.text = text;
		this.fontHeight = fontHeight;
		cdata = init(BITMAP_W, BITMAP_H);
		DavisGUI.DavisObjects.add(this);
	}

	private STBTTBakedChar.Buffer init(int BITMAP_W, int BITMAP_H) {
		int texID = glGenTextures();
		STBTTBakedChar.Buffer cdata = STBTTBakedChar.malloc(96);

		try {
			ByteBuffer ttf = ioResourceToByteBuffer("res/Helvetica.ttf", 160 * 1024);

			ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
			stbtt_BakeFontBitmap(ttf, fontHeight, bitmap, BITMAP_W, BITMAP_H, 32, cdata);

			glBindTexture(GL_TEXTURE_2D, texID);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		//glClearColor(43f / 255f, 43f / 255f, 43f / 255f, 0f); // BG color
		//glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		return cdata;
	}

	private int BITMAP_W = 512;
	private int BITMAP_H = 512;
	public void drawText()
	{
		renderText(cdata, BITMAP_W, BITMAP_H);
	}
	
	@Override
	public void finish()
	{
		cdata.free();
	}

	private void renderText(STBTTBakedChar.Buffer cdata, int BITMAP_W, int BITMAP_H) {
		
		glEnable(GL_TEXTURE_2D);
		
		try ( MemoryStack stack = stackPush() ) {
			FloatBuffer x = stack.floats(0.0f);
			FloatBuffer y = stack.floats(0.0f);

			STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);

			glBegin(GL_QUADS);
			for ( int i = 0; i < text.length(); i++ ) {
				char c = text.charAt(i);
				if ( c == '\n' ) {
					y.put(0, y.get(0) - fontHeight);
					x.put(0, 0.0f);
					continue;
				} 
				else if ( c < 32 || 128 <= c )
				{
					
					continue;
				}
					
				
				//y.put(0, fontHeight);
				
				stbtt_GetBakedQuad(cdata, BITMAP_W, BITMAP_H, c - 32, x, y, q, true);

				
				
				glTexCoord2f(q.s0(), q.t1());
				glVertex2f(q.x0(), q.y0());
				
				glTexCoord2f(q.s1(), q.t1());
				glVertex2f(q.x1(), q.y0());
				
				glTexCoord2f(q.s1(), q.t0());
				glVertex2f(q.x1(), q.y1());
				
				glTexCoord2f(q.s0(), q.t0());
				glVertex2f(q.x0(), q.y1());

				
				
			}
			glEnd();
		}
		
		
		glDisable(GL_TEXTURE_2D);
		//glDisable(GL_BLEND);

	
	}

	
}
