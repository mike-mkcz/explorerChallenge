package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikec on 27/01/15.
 */
public class CoordinateInfo
{
	private final Coordinate location;
	private final Map<Direction, Coordinate> neighbours;
	private int numVisits;
	private int lastVisit;
	private Direction nearestNeigboorToDoor;
	private boolean deadEnd; //or leadsToDeadEnd

	public CoordinateInfo(Coordinate location)
	{
		this.location = location;
		this.neighbours = new HashMap<>(4);
		addNeigbour(Direction.NORTH);
		addNeigbour(Direction.EAST);
		addNeigbour(Direction.SOUTH);
		addNeigbour(Direction.WEST);
		this.numVisits = 0;
		this.lastVisit = 0;
		this.deadEnd = false;
		this.nearestNeigboorToDoor = null;
	}

	private void addNeigbour(final Direction direction)
	{
		neighbours.put(direction, CoordinateUtils.getDirectionCoordinates(location, direction));
	}

	public Coordinate getLocation()
	{
		return location;
	}

	public Coordinate getNeighbour(final Direction direction)
	{
		return neighbours.get(direction);
	}

	public Map<Direction, Coordinate> getNeighbours()
	{
		return neighbours;
	}

	public void computeNearestNeighbourToDoor(final Coordinate doorLocation)
	{
		if (doorLocation == null)
		{
			nearestNeigboorToDoor = null;
		}
		else
		{
			//fancy maths go here
		}
	}

	public Direction getNearestNeigboorToDoor()
	{
		return nearestNeigboorToDoor;
	}

	public void incrementNumVisits()
	{
		this.numVisits++;
	}

	public int getNumVisits()
	{
		return numVisits;
	}

	public boolean isVisited()
	{
		return numVisits > 0;
	}

	public void markAsWall()
	{
		numVisits = Integer.MAX_VALUE;
	}

	public boolean isWall()
	{
		return numVisits == Integer.MAX_VALUE;
	}

	public int getLastVisit()
	{
		return lastVisit;
	}

	public void setLastVisit(final int lastVisit)
	{
		this.lastVisit = lastVisit;
	}

	public void markAsDeadEnd()
	{
		this.deadEnd = true;
	}

	public boolean isDeadEnd()
	{
		return deadEnd;
	}
}
