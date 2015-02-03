package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import java.util.Arrays;
import java.util.HashMap;

import static com.insano10.explorerchallenge.explorer.CoordinateUtils.*;
import static com.insano10.explorerchallenge.explorer.World.worldInstance;

/**
 * Created by mikec on 27/01/15.
 */
public class TheSonOfDarcula implements Explorer
{
	private static final String NAME = "Darcula Jr.";

	private Key key;
	private Direction lastDirection;
    private State currentState;

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public void enterMaze(Coordinate startLocation)
	{
		key = null;
        worldInstance().reset();
        currentState = StateFactory.getWanderingState();
	}

	@Override
	public Direction whichWayNow(final Coordinate fromLocation, final Direction[] availableDirections)
	{
		lastDirection = currentState.getDirection(lastDirection, fromLocation, Arrays.asList(availableDirections));
        return lastDirection;
	}

	@Override
	public void move(Coordinate fromLocation, Coordinate toLocation)
	{
		CoordinateInfo currLocation = worldInstance().computeIfAbsent(fromLocation);
		currLocation.incrementNumVisits();
		currLocation.incrementVisitedNeighbours();
		currLocation.setLastVisit(worldInstance().getTime());

		worldInstance().tick();
	}

	@Override
	public void keyFound(Key key, Coordinate location)
	{
		this.key = key;
	}

	@Override
	public Key getKey()
	{
		return key;
	}

	@Override
	public void exitReached(Coordinate location)
	{
		knowledgebase.get(location).markAsDoor();
	}

	@Override
	public void exitMaze()
	{

	}

	private void checkNeighboursForDoor(final Coordinate location)
	{
		CoordinateInfo locationInfo = knowledgebase.get(location);
		for (Direction direction : ORDERED_DIRECTIONS)
		{
			Coordinate neighbourCoordinate = getCoordsFromDirection(direction, location);
			CoordinateInfo neighbour = knowledgebase.get(neighbourCoordinate);
			if (neighbour != null)
			{
				if (neighbour.isDoor())
				{
					locationInfo.setDirectionToDoor(direction);
					return;
				}
				else if (neighbour.getToDoor() != null)
				{
					locationInfo.setDirectionToDoor(direction);
					return;
				}
			}
		}
	}
}
