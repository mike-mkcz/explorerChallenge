package com.insano10.explorerchallenge.explorer.state;

import com.insano10.explorerchallenge.explorer.world.CoordinateInfo;
import com.insano10.explorerchallenge.explorer.world.CoordinateNeighbour;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.List;
import java.util.stream.Collectors;

import static com.insano10.explorerchallenge.explorer.Utils.ORDERED_DIRECTIONS;
import static com.insano10.explorerchallenge.explorer.Utils.isOpposite;
import static com.insano10.explorerchallenge.explorer.world.World.worldInstance;

/**
 * Created by mikec on 2/3/15.
 */
public abstract class State
{
	public abstract Direction getDirection(final Direction lastDirection, final Coordinate location, final List<Direction> availableDirections);

	protected CoordinateInfo updateLocationDetails(final Coordinate location, final List<Direction> availableDirections)
	{
		CoordinateInfo locationInfo = worldInstance().computeIfAbsent(location);
		
		if (locationInfo == null)
		{
			throw new IllegalStateException("There should be no way computeIfAbsent returns null");
		}
		
		locationInfo.setActiveNeighbours(availableDirections);
		
		worldInstance().leadsToDeadEnd(location);

		ORDERED_DIRECTIONS.forEach(direction -> {
			if (!availableDirections.contains(direction))
			{
				CoordinateInfo neighbour = worldInstance().computeRelativeIfAbsent(direction, location);
				neighbour.markAsWall();
			}
		});
		
		return locationInfo;
	}
	
	protected int getMaxNeighbourScore(final CoordinateInfo locationInfo)
	{
		return locationInfo.getActiveNeighbours().stream().mapToInt(CoordinateNeighbour::getScore).max().orElse(Integer.MIN_VALUE);
	}
	
	protected List<CoordinateNeighbour> getMaxNeighbours(final CoordinateInfo locationInfo, final int filterScore)
	{
		return locationInfo.getActiveNeighbours().stream().filter(neighbour -> neighbour.getScore() == filterScore).collect(Collectors.toList());
	}
	
	protected List<CoordinateNeighbour> getNonReturningMaxNeighbours(final Direction fromDirection, List<CoordinateNeighbour> neighbours)
	{
		return neighbours.stream().filter(neighbour -> !isOpposite(fromDirection, neighbour.getDirection()))
		                   .collect(Collectors.toList());
	}
	
	protected int getMinStepsToDoor(final CoordinateInfo locationInfo)
	{
		return locationInfo.getActiveNeighbours().stream().mapToInt(CoordinateNeighbour::getStepsToDoor).min().orElse(Integer.MAX_VALUE);
	}

	protected List<CoordinateNeighbour> getMinStepNeighbours(final CoordinateInfo locationInfo, final int filterSteps)
	{
		return locationInfo.getActiveNeighbours().stream().filter(neighbour -> neighbour.getStepsToDoor() == filterSteps).collect(Collectors.toList());
	}
}
