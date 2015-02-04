package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by mikec on 27/01/15.
 */
public class Utils
{
	private static Random RANDOM = new Random(0);
	
	public final static List<Direction> ORDERED_DIRECTIONS = new ArrayList<>();

	static
	{
		ORDERED_DIRECTIONS.add(Direction.EAST);
		ORDERED_DIRECTIONS.add(Direction.SOUTH);
		ORDERED_DIRECTIONS.add(Direction.WEST);
		ORDERED_DIRECTIONS.add(Direction.NORTH);
	}
	
	public static List<Direction> orderDirections(final Direction[] directions)
	{
		return ORDERED_DIRECTIONS.stream().filter(direction -> inArray(direction, directions)).collect(Collectors.toList());
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

	public static Coordinate getCoordsFromDirection(final Direction direction, final Coordinate currentLocation)
	{
		int x = currentLocation.getX() + direction.getxOffset();
		int y = currentLocation.getY() + direction.getyOffset();
		return Coordinate.create(x, y);
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
	
	public static int getRandom(final int range)
	{
		return (int) (0 + (range * RANDOM.nextDouble()));
	}
}
