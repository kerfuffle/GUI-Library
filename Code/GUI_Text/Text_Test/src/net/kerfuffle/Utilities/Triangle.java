package net.kerfuffle.Utilities;

import static org.lwjgl.opengl.GL11.*;
import static net.kerfuffle.Utilities.DavisGUI.*;

public class Triangle {

	private Vertex verticies[] = new Vertex[3];
	private Vertex relatives[] = new Vertex[3];
	
	private float x, y;
	private RGB color;
	
	public Triangle (float x, float y, RGB color)
	{
		this.x=x;
		this.y=y;
		this.color = color;
	}
	
	public void setRelativeVertex(int vertex, float x, float y)
	{
		verticies[vertex] = new Vertex(this.x + x, this.y + y);
		relatives[vertex] = new Vertex(x,y);
	}
	
	public Vertex[] getVerticies()
	{
		return verticies;
	}
	public Vertex[] getRelatives()
	{
		return relatives;
	}
	
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	
	private boolean verticiesNull()
	{
		for (Vertex v : verticies)
		{
			if (v==null)
			{
				return true;
			}
		}
		return false;
	}
	
	public void draw()
	{
		if (!verticiesNull())
		{
			setColor(color);
			glBegin(GL_TRIANGLES);
			for (Vertex v : verticies)
			{
				glVertex2f(v.x, v.y);
			}
			glEnd();
		}
	}
	
	
}
