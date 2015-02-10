package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.explorer.Utils;
import com.insano10.explorerchallenge.explorer.world.CoordinateInfo;
import com.insano10.explorerchallenge.explorer.world.CoordinateNeighbour;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.insano10.explorerchallenge.explorer.Utils.ORDERED_DIRECTIONS;
import static com.insano10.explorerchallenge.explorer.Utils.isOpposite;
import static com.insano10.explorerchallenge.explorer.world.World.worldInstance;

/**
 * Created by mikec on 2/3/15.
 */
public class ExplorerState
{
    private final boolean continueAlongChosenDirection;
    private final boolean isRandom;
    private final boolean useTremaux;
    private final boolean isSmartArse;
    private boolean shouldQuit;

    public ExplorerState(final boolean continueAlongChosenDirection, final boolean isRandom, final boolean useTremaux, final boolean isSmartArse)
    {
        this.continueAlongChosenDirection = continueAlongChosenDirection;
        this.isRandom = isRandom;
        this.useTremaux = useTremaux;
        this.isSmartArse = isSmartArse;
        this.shouldQuit = false;
    }

    public Direction getDirection(final Direction lastDirection, final Coordinate location, final List<Direction> availableDirections)
    {
        CoordinateInfo locationInfo = updateLocationDetails(location, availableDirections);
        if (useTremaux)
        {
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
        }
        locationInfo.incrementNumVisits();

        if (shouldQuit)
        {
            final int minStepsToDoor = getMinStepsToDoor(locationInfo);

            List<CoordinateNeighbour> minStepNeighbours = getMinStepNeighbours(locationInfo, minStepsToDoor);

            if (minStepNeighbours != null && !minStepNeighbours.isEmpty())
            {
                return minStepNeighbours.get(0).getDirection();
            }
        }

        if (isSmartArse)
        {
            worldInstance().guessAboutTheWorld(location, new HashSet<>());
        }

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
                if (continueAlongChosenDirection)
                {
                    for (CoordinateNeighbour neighbour : nonReturningNeighbours)
                    {
                        if (neighbour.getDirection().equals(lastDirection))
                        {
                            return lastDirection;
                        }
                    }
                }
                if (isRandom)
                {
                    int randIndex = Utils.getRandom(nonReturningNeighbours.size());
                    return nonReturningNeighbours.get(randIndex).getDirection();
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
		
		locationInfo.setGuess(false);
		
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
		if (fromDirection == null)
		{
			return neighbours;
		}
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

    public void itsQuittingTime()
    {
        shouldQuit = true;
    }
}
