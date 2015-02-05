package com.insano10.explorerchallenge.explorer.world;

import com.insano10.explorerchallenge.explorer.Utils;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikec on 2/3/15.
 */
public final class World
{
	private static World INSTANCE;

	public static World worldInstance()
	{
		if (World.INSTANCE == null)
		{
			World.INSTANCE = new World();
		}
		return World.INSTANCE;
	}
	
	private Map<Coordinate, CoordinateInfo> knowledgebase;
	private int time;
	private Coordinate doorLocation;
	
	private World()
	{
		knowledgebase = new HashMap<>();
		reset();
	}

	public void reset()
	{
		knowledgebase.clear();
		time = 0;
		doorLocation = null;
	}

	public Map<Coordinate, CoordinateInfo> getKnowledgebase()
	{
		return knowledgebase;
	}

	public CoordinateInfo computeRelativeIfAbsent(final Direction direction, final Coordinate fromLocation)
	{
		Coordinate relativeCoordinates = Utils.getCoordsFromDirection(direction, fromLocation);
		return computeIfAbsent(relativeCoordinates);
	}

	public CoordinateInfo computeIfAbsent(final Coordinate location)
	{
		return knowledgebase.computeIfAbsent(location, cLocation -> new CoordinateInfo(cLocation));
	}

	public void markDoorLocation(final Coordinate location)
	{
		doorLocation = location;
		computeIfAbsent(location).markAsDoor();
	}
	
	public boolean doorFound()
	{
		return doorLocation != null;
	}
	
	public void tick()
	{
		time++;
	}

	public int getTime()
	{
		return time;
	}
	
	public void updateDoorCost()
	{
		updateDoorCost(doorLocation);
	}
	
	private void updateDoorCost(final Coordinate location)
	{
		final CoordinateInfo currentLocation = computeIfAbsent(location);
		if (currentLocation != null && !currentLocation.isWall())
		{
			boolean anyChanged = false;
			int newStepsToDoor = currentLocation.getStepsToDoor() + 1;
			for (CoordinateNeighbour neighbour : currentLocation.getActiveNeighbours())
			{
				if (neighbour.getStepsToDoor() > newStepsToDoor)
				{
					neighbour.getInfo().setStepsToDoor(newStepsToDoor);
					anyChanged = true;
				}
			}
			if (anyChanged)
			{
				for (CoordinateNeighbour neighbour : currentLocation.getActiveNeighbours())
				{
					updateDoorCost(neighbour.getCoordinate());
				}
			}
		}
	}
	
	public void leadsToDeadEnd(final Coordinate location)
	{
		CoordinateInfo currLocation = computeIfAbsent(location);
		if (!currLocation.isDeadEnd() && currLocation.getActiveNeighbours().size() < 3)
		{
			for (CoordinateNeighbour neighbour : currLocation.getActiveNeighbours())
			{
				if (neighbour.getInfo().isDeadEnd())
				{
					currLocation.markAsDeadEnd();
					return;
				}
			}
		}
	}
}
