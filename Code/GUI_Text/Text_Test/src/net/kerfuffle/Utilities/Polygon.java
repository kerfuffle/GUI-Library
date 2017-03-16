package net.kerfuffle.Utilities;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Polygon {

	class Vertex
	{
		float x, y;
		
		public Vertex(float x, float y)
		{
			this.x=x;
			this.y=y;
		}
	}
	
	// Position should be relative based off of first vertex
	
	//TODO need to make it based off of triangles
	
	private float x, y;
	private ArrayList <Vertex> points = new ArrayList<Vertex>();
	
	public Polygon(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	public void addVertex(float x, float y)
	{
		points.add(new Vertex(x,y));
	}
	public void addRelativeVertex(float x, float y)
	{
		points.add(new Vertex(this.x + x, this.y + y));
	}
	public void setVertex(int vertex, float x, float y)
	{
		points.set(vertex, new Vertex(x,y));
	}
	
	public void draw()
	{
		glBegin(GL_POLYGON);
		for (Vertex v : points)
		{
			glVertex2f(x + v.x, y + v.y);
		}
		glEnd();
	}
	
}
