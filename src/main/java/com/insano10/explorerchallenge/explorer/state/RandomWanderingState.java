package com.insano10.explorerchallenge.explorer.state;

import com.insano10.explorerchallenge.explorer.Utils;
import com.insano10.explorerchallenge.explorer.world.CoordinateInfo;
import com.insano10.explorerchallenge.explorer.world.CoordinateNeighbour;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.List;


/**
 * Created by mikec on 2/3/15.
 */
public class RandomWanderingState extends State
{
	@Override
	public Direction getDirection(final Direction lastDirection, final Coordinate location, final List<Direction> availableDirections)
	{
		CoordinateInfo locationInfo = markLocationDetailsAndReturnNeighbours(location, availableDirections);
		
		final int maxScore = getMaxNeighbourScore(locationInfo);
		
		final List<CoordinateNeighbour> maxNeighbours = getMaxNeighbours(locationInfo, maxScore);
		
		if (maxNeighbours == null || maxNeighbours.isEmpty())
		{
			return null;
		}
		
		Direction chosenDirection = maxNeighbours.get(0).getDirection();
		
		if (maxNeighbours.size() > 1)
		{
			final List<CoordinateNeighbour> nonReturningNeighbours = getNonReturningNeighbours(locationInfo, lastDirection);
			
			if (nonReturningNeighbours != null && !nonReturningNeighbours.isEmpty())
			{
				int randIndex = Utils.getRandom(nonReturningNeighbours.size());
				chosenDirection = nonReturningNeighbours.get(randIndex).getDirection();
			}
		}
		
		return chosenDirection;
	}
}
