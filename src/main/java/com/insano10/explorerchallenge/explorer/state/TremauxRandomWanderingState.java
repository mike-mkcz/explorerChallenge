package com.insano10.explorerchallenge.explorer.state;

import com.insano10.explorerchallenge.explorer.Utils;
import com.insano10.explorerchallenge.explorer.world.CoordinateInfo;
import com.insano10.explorerchallenge.explorer.world.CoordinateNeighbour;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.List;

import static com.insano10.explorerchallenge.explorer.world.World.worldInstance;

/**
 * Created by mikec on 2/3/15.
 */
public class TremauxRandomWanderingState extends State
{
	@Override
	public Direction getDirection(final Direction lastDirection, final Coordinate location, final List<Direction> availableDirections)
	{
		CoordinateInfo locationInfo = updateLocationDetails(location, availableDirections);
		if (locationInfo.isCrossRoads())
		{
			if (locationInfo.isVisited())
			{
				Direction returnDirection = Utils.getOpposite(lastDirection);
				CoordinateInfo returnNeighbour = worldInstance().computeRelativeIfAbsent(returnDirection, location);
				if (returnNeighbour.getNumVisits() < 2)
				{
					locationInfo.incrementNumVisits();
					return returnDirection;
				}
			}
		}
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
				int randIndex = Utils.getRandom(nonReturningNeighbours.size());
				return nonReturningNeighbours.get(randIndex).getDirection();
			}
		}
		
		return maxNeighbours.get(0).getDirection();
	}
	
	
}
