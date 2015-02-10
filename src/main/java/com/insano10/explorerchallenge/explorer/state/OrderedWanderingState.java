package com.insano10.explorerchallenge.explorer.state;

import com.insano10.explorerchallenge.explorer.world.CoordinateInfo;
import com.insano10.explorerchallenge.explorer.world.CoordinateNeighbour;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.List;

import static com.insano10.explorerchallenge.explorer.Utils.ORDERED_DIRECTIONS;

/**
 * Created by mikec on 2/3/15.
 */
public class OrderedWanderingState extends State
{
	@Override
	public Direction getDirection(final Direction lastDirection, final Coordinate location, final List<Direction> availableDirections)
	{
		CoordinateInfo locationInfo = updateLocationDetails(location, availableDirections);
		locationInfo.incrementNumVisits();
		
		final int maxScore = getMaxNeighbourScore(locationInfo);
		
		final List<CoordinateNeighbour> maxNeighbours = getMaxNeighbours(locationInfo, maxScore);
		
		if (maxNeighbours == null || maxNeighbours.isEmpty())
		{
			return null;
		}
		
		if (maxNeighbours.size() > 1)
		{
			final List<CoordinateNeighbour> nonReturningNeighbours = getNonReturningMaxNeighbours(lastDirection, maxNeighbours);
			
			if (nonReturningNeighbours != null && !nonReturningNeighbours.isEmpty())
			{
				for (CoordinateNeighbour neighbour : nonReturningNeighbours)
				{
					if (neighbour.getDirection().equals(lastDirection))
					{
						return lastDirection;
					}
				}
				for (Direction direction : ORDERED_DIRECTIONS)
				{
					for (CoordinateNeighbour neighbour : nonReturningNeighbours)
					{
						if (direction.equals(neighbour.getDirection()))
						{
							return direction;
						}
					}
				}
			}
		}
		
		return maxNeighbours.get(0).getDirection();
	}
}
