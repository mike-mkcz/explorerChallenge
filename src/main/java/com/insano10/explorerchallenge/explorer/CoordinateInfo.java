package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

/**
 * Created by mikec on 27/01/15.
 */
public class CoordinateInfo
{
	private int numVisits;
	private int lastVisit;
	private Direction nearestNeighbourToDoor;
	private boolean deadEnd; //or leadsToDeadEnd

	public CoordinateInfo()
	{
		this.numVisits = 0;
		this.lastVisit = 0;
		this.deadEnd = false;
		this.nearestNeighbourToDoor = null;
	}

	public void addNearestNeighbourToDoor(final Direction nearestNeighbourToDoor)
	{
			this.nearestNeighbourToDoor = nearestNeighbourToDoor;
	}

	public Direction getNearestNeighbourToDoor()
	{
		return nearestNeighbourToDoor;
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
