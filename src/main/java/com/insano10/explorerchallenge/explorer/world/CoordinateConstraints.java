package com.insano10.explorerchallenge.explorer.world;

/**
 * Created by mikec on 10/02/15.
 */
public class CoordinateConstraints
{
	private int x;
	private int y;

	public CoordinateConstraints(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void compareAndSetX(int newX, boolean gt)
	{
		if ((gt && x > newX) || (!gt && x < newX))
		{
			x = newX;
		}
	}

	public void compareAndSetY(int newY, boolean gt)
	{
		if ((gt && y > newY) || (!gt && y < newY))
		{
			y = newY;
		}
	}
	
	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public String toString()
	{
		return "CoordinateConstraints{" +
		       "x=" + x +
		       ", y=" + y +
		       '}';
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		CoordinateConstraints that = (CoordinateConstraints) o;
		
		if (x != that.x)
		{
			return false;
		}
		if (y != that.y)
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		return result;
	}
}
