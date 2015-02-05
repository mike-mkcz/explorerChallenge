package com.insano10.explorerchallenge.explorer.world;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import static com.insano10.explorerchallenge.explorer.world.World.worldInstance;

/**
 * Created by mikec on 04/02/15.
 */
public class CoordinateNeighbour
{
	private final Direction direction;
	private final Coordinate coordinate;
	
	public CoordinateNeighbour(final Direction direction, Coordinate coordinate)
	{
		this.direction = direction;
		this.coordinate = coordinate;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public Coordinate getCoordinate()
	{
		return coordinate;
	}
	
	public CoordinateInfo getInfo()
	{
		return worldInstance().computeIfAbsent(coordinate);
	}
	
	public int getScore()
	{
		int score = getInfo().computeScore(worldInstance().getTime());
		System.out.println(toString() + " Score: " + score + " " + " Info: " + getInfo().toString());
		return score;
	}

	public int getStepsToDoor()
	{
		return getInfo().getStepsToDoor();
	}
	
	@Override
	public String toString()
	{
		return "CoordinateNeighbour{" +
		       "direction=" + direction +
		       ", coordinate=" + coordinate +
		       '}';
	}
}
