package com.insano10.explorerchallenge.explorer.state;

import com.insano10.explorerchallenge.explorer.world.CoordinateInfo;
import com.insano10.explorerchallenge.explorer.world.CoordinateNeighbour;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.List;

import static com.insano10.explorerchallenge.explorer.world.World.worldInstance;

/**
 * Created by mikec on 2/3/15.
 */
public class QuittingState extends State
{
	@Override
	public Direction getDirection(final Direction lastDirection, final Coordinate location, final List<Direction> availableDirections)
	{
		CoordinateInfo currLocation = worldInstance().computeIfAbsent(location);
		
		if (currLocation == null)
		{
			throw new IllegalStateException("There should be no way computeIfAbsent returns null");
		}
		
		final int minStepsToDoor = getMinStepsToDoor(currLocation);
		
		List<CoordinateNeighbour> minStepNeighbours = getMinStepNeighbours(currLocation, minStepsToDoor);
		
		if (minStepNeighbours == null || minStepNeighbours.isEmpty())
		{
			return StateFactory.getOrderedWanderingState().getDirection(lastDirection, location, availableDirections);
		}
		
		return minStepNeighbours.get(0).getDirection();
	}
}
