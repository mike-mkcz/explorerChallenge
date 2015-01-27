package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikec on 27/01/15.
 */
public class TheSonOfDarcula implements Explorer
{
	private static final String NAME = "Darcula Jr.";

	private Key mazeKey;
	private Map<Coordinate, CoordinateInfo> whatIKnow;
	private long time;

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public void enterMaze(Coordinate startLocation)
	{
		mazeKey = new Key("I'm a celebrity, get me out of here!");
		if (whatIKnow == null)
		{
			whatIKnow = new HashMap<>();
		}
		whatIKnow.clear();
		time = 0;

	}

	@Override
	public Direction whichWayNow(final Coordinate fromLocation, final Direction[] availableDirections)
	{
		whatIKnow.putIfAbsent(fromLocation, new CoordinateInfo(fromLocation));
		Arrays.stream(Direction.values()).forEach(direction -> {
			Coordinate neighbour = whatIKnow.get(fromLocation).getNeighbour(direction);
			whatIKnow.putIfAbsent(neighbour, new CoordinateInfo(neighbour));
			if (!CoordinateUtils.inArray(direction, availableDirections))
			{
				whatIKnow.get(neighbour).markAsWall();
			}
		});
		if (availableDirections.length == 1)
		{
			whatIKnow.get(fromLocation).markAsDeadEnd();
		}
		else
		{
			if (CoordinateUtils.isDeadEnd(fromLocation, whatIKnow))
			{
				whatIKnow.get(fromLocation).isDeadEnd();
			}
		}
		int minVisited = Integer.MAX_VALUE - 1;
		Direction minVisitedDir = null;
		for (Direction direction : Direction.values())
		{
			Coordinate neighbourCoordinate = whatIKnow.get(fromLocation).getNeighbour(direction);
			CoordinateInfo neighbour = whatIKnow.get(neighbourCoordinate);
			if (!neighbour.isWall())
			{
				if (!neighbour.isVisited())
				{
					return direction;
				}
				else if (neighbour.getNumVisits() < minVisited)
				{
					minVisited = neighbour.getNumVisits();
					minVisitedDir = direction;
				}
			}
		}
		return minVisitedDir;
	}

	@Override
	public void move(Coordinate fromLocation, Coordinate toLocation)
	{
		whatIKnow.get(fromLocation).incrementNumVisits();
		whatIKnow.putIfAbsent(toLocation, new CoordinateInfo(toLocation));
		Arrays.stream(Direction.values()).forEach(direction -> {
			Coordinate neighbour = whatIKnow.get(fromLocation).getNeighbour(direction);
			whatIKnow.putIfAbsent(neighbour, new CoordinateInfo(neighbour));

		});
		if (CoordinateUtils.isDeadEnd(fromLocation, whatIKnow))
		{
			whatIKnow.get(fromLocation).isDeadEnd();
		}
		time++;
	}

	@Override
	public void keyFound(Key key, Coordinate location)
	{
		mazeKey = key;
	}

	@Override
	public Key getKey()
	{
		return mazeKey;
	}

	@Override
	public void exitReached(Coordinate location)
	{

	}

	@Override
	public void exitMaze()
	{

	}
}
