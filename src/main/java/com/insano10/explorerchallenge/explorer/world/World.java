package com.insano10.explorerchallenge.explorer.world;

import com.insano10.explorerchallenge.explorer.Utils;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.insano10.explorerchallenge.explorer.Utils.ORDERED_DIRECTIONS;

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
	
	private CoordinateConstraints minCoord, maxCoord;
	
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
		minCoord = new CoordinateConstraints(Integer.MAX_VALUE, Integer.MAX_VALUE);
		maxCoord = new CoordinateConstraints(Integer.MIN_VALUE, Integer.MIN_VALUE);
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
		updateLocationConstraints(location);
		return knowledgebase.computeIfAbsent(location, cLocation -> new CoordinateInfo(cLocation));
	}
	
	private void updateLocationConstraints(Coordinate location)
	{
		minCoord.compareAndSetX(location.getX(), true);
		minCoord.compareAndSetY(location.getY(), true);
		maxCoord.compareAndSetX(location.getX(), false);
		maxCoord.compareAndSetY(location.getY(), false);
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
	
	private boolean constrained(final int min, final int x, final int max)
	{
		return min <= x && x <= max;
	}
	
	private boolean constrained(final Coordinate coordinate)
	{
		return constrained(minCoord.getX(), coordinate.getX(), maxCoord.getX()) && constrained(minCoord.getY(), coordinate.getY(), maxCoord.getY());
	}
	
	private boolean onEdge(final int min, final int x, final int max)
	{
		return x == min || x == max;
	}
	
	private boolean onEdge(final Coordinate coordinate)
	{
		return onEdge(minCoord.getX(), coordinate.getX(), maxCoord.getX()) || onEdge(minCoord.getY(), coordinate.getY(), maxCoord.getY());
		
	}
	
	public void guessAboutTheWorld(final Coordinate coordinate, final Set<Coordinate> visited)
	{
		if (visited.contains(coordinate))
		{
			return;
		}
		if (constrained(coordinate))
		{
			boolean guess = false;
			if (!knowledgebase.containsKey(coordinate) || !knowledgebase.get(coordinate).isVisited())
			{
				guess = true;
			}
			CoordinateInfo coordinateInfo = worldInstance().computeIfAbsent(coordinate);
			if (guess)
			{
				coordinateInfo.setGuess(guess);
				int numWalls = 0;
				int numDeadEnds = 0;
				List<Direction> activeNeighbours = new ArrayList<>();
				for (Direction direction : ORDERED_DIRECTIONS)
				{
					Coordinate neighbour = Utils.getCoordsFromDirection(direction, coordinate);
					CoordinateInfo neighbourInfo = knowledgebase.get(neighbour);
					if (neighbourInfo != null)
					{
						if (neighbourInfo.isWall())
						{
							numWalls++;
						}
						else
						{
							activeNeighbours.add(direction);
							if (neighbourInfo.isDeadEnd())
							{
								numDeadEnds++;
							}
						}
					}
				}
				coordinateInfo.setActiveNeighbours(activeNeighbours);
				if (numWalls > 1)
				{
					if (numWalls == 2 && onEdge(coordinate))
					{
						coordinateInfo.markAsWall();
					}
					else if (numWalls > 2)
					{
						coordinateInfo.markAsWall();
					}
					else if (numWalls + numDeadEnds > 2)
					{
						coordinateInfo.markAsDeadEnd();
					}
				}
				else if (numDeadEnds > 2)
				{
					coordinateInfo.markAsDeadEnd();
				}
			}
			visited.add(coordinate);
			for (Direction direction : ORDERED_DIRECTIONS)
			{
				Coordinate neighbour = Utils.getCoordsFromDirection(direction, coordinate);
				guessAboutTheWorld(neighbour, visited);
			}
		}
	}
	
	public void printWorldMap()
	{
		for (int y = maxCoord.getY(); y >= minCoord.getY(); y--)
		{
			for (int x = minCoord.getX(); x <= maxCoord.getX(); x++)
			{
				Coordinate coordinate = Coordinate.create(x, y);
				if (knowledgebase.containsKey(coordinate))
				{
					CoordinateInfo info = knowledgebase.get(coordinate);
					if (info.isWall())
					{
						System.out.print("*");
					}
					else if (info.isDeadEnd())
					{
						System.out.print("-");
					}
					else if (info.isVisited())
					{
						System.out.print("+");
					}
					else
					{
						System.out.print(" ");
					}
				}
				else
				{
					System.out.print("?");
				}
			}
			System.out.println();
		}
	}
}
