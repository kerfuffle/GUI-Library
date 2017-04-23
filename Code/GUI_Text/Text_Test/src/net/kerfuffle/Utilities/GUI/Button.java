package net.kerfuffle.Utilities.GUI;

import static net.kerfuffle.Utilities.GUI.DavisGUI.*;

public class Button {
	
	private Triangle triangle;
	private Quad quad;
	private OnClickListener onClickListener;
	private KeyPressListener keyPressListener;
	
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
	public void setColor(RGB color)
	{
		if (quad != null)
		{
			quad.setColor(color);
		}
		
		if (triangle != null)
		{
			triangle.setColor(color);
		}
	}
	public void setTexture(String path)
	{
		quad.setTexture(path);
	}
	
	public void setOnClickListener(OnClickListener ocl)
	{
		onClickListener = ocl;
	}
	public void setKeyPressListener(KeyPressListener kpl)
	{
		keyPressListener = kpl;
	}
	
	public void update()
	{
		if (triangle != null)
		{	
			Quad bounds = getTriangleBounds(triangle);
			
			if (isClick(bounds.x, bounds.y, bounds.w, bounds.h) && onClickListener != null)
			{
				onClickListener.onClick();
			}
			triangle.draw();
		}
		if (quad != null)
		{
			if (isClick(quad.x, quad.y, quad.w, quad.h) && onClickListener != null)
			{
				onClickListener.onClick();
			}
			quad.draw();
		}
		
		if (keyPressListener != null)
		{
			if (checkKey(keyPressListener.key))
			{
				keyPressListener.onPress();
			}
		}
	}
}
