package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.ArrayList;
import java.util.List;

import static com.insano10.explorerchallenge.explorer.World.worldInstance;

/**
 * Created by mikec on 2/3/15.
 */
public class QuittingState implements State
{
	@Override
	public Direction getDirection(Direction lastDirection, Coordinate location, List<Direction> availableDirections)
	{
		CoordinateInfo currLocation = worldInstance().computeIfAbsent(location);
		
		if (currLocation == null)
		{
			throw new IllegalStateException("There should be no way computeIfAbsent returns null");
		}
		
		int minStepsToDoor = Integer.MAX_VALUE;
		List<Direction> minStepsDirections = new ArrayList<>();
		for (Direction direction : availableDirections)
		{
			CoordinateInfo neighbour = worldInstance().computeRelativeIfAbsent(direction, location);
			if (neighbour.getStepsToDoor() != Integer.MAX_VALUE)
			{
				if (minStepsToDoor > neighbour.getStepsToDoor())
				{
					minStepsToDoor = neighbour.getStepsToDoor();
					minStepsDirections.clear();
					minStepsDirections.add(direction);
				}
				else if (minStepsToDoor == neighbour.getStepsToDoor())
				{
					minStepsDirections.add(direction);
				}
			}
		}
		if (minStepsDirections.isEmpty())
		{
			return StateFactory.getOrderedWanderingState().getDirection(lastDirection, location, availableDirections);
		}
		return minStepsDirections.get(0);
	}
}
