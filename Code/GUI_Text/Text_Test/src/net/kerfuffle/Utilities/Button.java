package net.kerfuffle.Utilities;

import static net.kerfuffle.Utilities.DavisGUI.*;

public class Button {

	private Triangle triangle;
	private Quad quad;
	
	public Button(Quad quad)
	{
		this.quad = quad;
	}
	public Button(Triangle triangle)
	{
		this.triangle = triangle;
	}
	
	
	public Triangle getTriangle()
	{
		return triangle;
	}
	public Quad getQuad()
	{
		return quad;
	}
	
	private float[] mousePos = new float[2];
	private void checkClick()
	{
		mousePos = getMousePos();
	}
	
	
	public void update()
	{
		if (triangle != null)
		{
			triangle.draw();
		}
		if (quad != null)
		{
			quad.draw();
		}
	}
}
