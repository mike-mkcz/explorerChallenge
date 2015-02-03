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
	public final static Direction[] ORDERED_DIRECTIONS = {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};

	public static boolean isDeadEnd(final Coordinate location, final Map<Coordinate, CoordinateInfo> known)
	{
		if (known.containsKey(location))
		{
			List<CoordinateInfo> nonWalls = new ArrayList<>();
			int numWalls = 0;
			for (Direction direction : CoordinateUtils.ORDERED_DIRECTIONS)
			{
				Coordinate neighbour = CoordinateUtils.getCoordsFromDirection(location, direction);
				if (known.containsKey(neighbour))
				{
					CoordinateInfo neighbourInfo = known.get(neighbour);
					if (neighbourInfo.isWall())
					{
						numWalls++;
					}
					else
					{
						nonWalls.add(neighbourInfo);
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

	public static Coordinate getCoordsFromDirection(final Coordinate currentLocation, final Direction direction)
	{
		int x = currentLocation.getX() + direction.getxOffset();
		int y = currentLocation.getY() + direction.getyOffset();
		return Coordinate.create(x, y);
	}

	public static boolean inArray(final Direction direction, final Direction[] directions)
	{
		if (direction != null)
		{
			for (Direction dir : directions)
			{
				if (dir.equals(direction))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isOpposite(final Direction fromDirection, final Direction toDirection)
	{
		return fromDirection.equals(Direction.EAST) && toDirection.equals(Direction.WEST) ||
		       fromDirection.equals(Direction.WEST) && toDirection.equals(Direction.EAST) ||
		       fromDirection.equals(Direction.NORTH) && toDirection.equals(Direction.SOUTH) ||
		       fromDirection.equals(Direction.SOUTH) && toDirection.equals(Direction.NORTH);
	}

	public static Direction getOpposite(final Direction fromDirection)
	{
		switch (fromDirection)
		{
			case EAST:
			{
				return Direction.WEST;
			}

			case SOUTH:
			{
				return Direction.NORTH;
			}

			case WEST:
			{
				return Direction.EAST;
			}

			case NORTH:
			{
				return Direction.SOUTH;
			}
		}
		return null;
	}

//	public static void backtrackDeadEnd(final Coordinate location, final Map<Coordinate, CoordinateInfo> known)
//	{
//		final CoordinateInfo currLocation = known.get(location);
//		if (currLocation == null || currLocation.isDeadEnd())
//		{
//			return;
//		}
//		if (currLocation.getActiveNeighbours() > 2)
//		{
//
//		}
//	}
}
