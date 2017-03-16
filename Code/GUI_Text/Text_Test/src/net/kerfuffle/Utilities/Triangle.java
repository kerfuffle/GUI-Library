package net.kerfuffle.Utilities;

import static org.lwjgl.opengl.GL11.*;

public class Triangle {

	private Vertex verticies[] = new Vertex[3];
	
	private float x, y;
	
	public Triangle (float x, float y)
	{
		this.x=x;
		this.y=y;
	}
	
	public void setRelativeVertex(int vertex, float x, float y)
	{
		verticies[vertex] = new Vertex(this.x + x, this.y + y);
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
			glBegin(GL_TRIANGLES);
			for (Vertex v : verticies)
			{
				
			}
		}
	}
	
	
	
	
}
