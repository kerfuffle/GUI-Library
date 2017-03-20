package net.kerfuffle.Utilities;

import static net.kerfuffle.Utilities.DavisGUI.*;

public class Button {
	
	private Triangle triangle;
	private Quad quad;
	private OnClickListener onClickListener;
	
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
	public void setTexture(String path)
	{
		quad.setTexture(path);
	}
	
	public void setOnClickListener(OnClickListener ocl)
	{
		onClickListener = ocl;
	}
	
	
	public void update()
	{
		if (triangle != null)
		{
			triangle.draw();
		}
		if (quad != null)
		{
			if (isClick(quad.x, quad.y, quad.w, quad.h))
			{
				onClickListener.onClick();
			}
			quad.draw();
		}
	}
}
