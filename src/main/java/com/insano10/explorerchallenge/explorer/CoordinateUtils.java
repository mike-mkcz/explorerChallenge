package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mikec on 27/01/15.
 */
public class CoordinateUtils
{
	public static Coordinate getDirectionCoordinates(final Coordinate currentLocation, final Direction direction)
	{
		int x = currentLocation.getX();
		int y = currentLocation.getY();
		switch (direction)
		{
			case NORTH:
			{
				y++;
				break;
			}

			case EAST:
			{
				x++;
				break;
			}

			case SOUTH:
			{
				y--;
				break;
			}

			case WEST:
			{
				x--;
				break;
			}
		}
		return Coordinate.create(x, y);
	}

	public static boolean isDeadEnd(final Coordinate location, final Map<Coordinate, CoordinateInfo> known)
	{
		if (known.containsKey(location))
		{
			List<CoordinateInfo> nonWalls = new ArrayList<>();
			CoordinateInfo locationInfo = known.get(location);
			int numWalls = 0;
			for (Direction direction : Direction.values())
			{
				if (known.containsKey(locationInfo.getNeighbour(direction)))
				{
					CoordinateInfo neighbour = known.get(locationInfo.getNeighbour(direction));
					if (neighbour.isWall())
					{
						numWalls++;
					}
					else
					{
						nonWalls.add(neighbour);
					}
				}
			}
			if (numWalls < 2)
			{
				return false;
			}
			else if (numWalls > 2)
			{
				return true;
			}
			else
			{
				for (CoordinateInfo nonWallNeighbour : nonWalls)
				{
					if (nonWallNeighbour.isDeadEnd())
					{
						return true;
					}
				}
			}
		}
		return false; // should really  be 'huh?'
	}

	public static boolean inArray(final Direction direction, final Direction[] directions)
	{
		for (Direction dir : directions)
		{
			if (dir.equals(direction))
			{
				return true;
			}
		}
		return false;
	}
}
