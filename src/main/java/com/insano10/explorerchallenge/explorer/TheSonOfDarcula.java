package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import java.util.Arrays;
import java.util.HashMap;

import static com.insano10.explorerchallenge.explorer.CoordinateUtils.*;

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
		if (knowledgebase == null)
		{
			knowledgebase = new HashMap<>();
		}
		knowledgebase.clear();
		time = 0;
        currentState = StateFactory.getWanderingState();
	}

	@Override
	public Direction whichWayNow(final Coordinate fromLocation, final Direction[] availableDirections)
	{
		return currentState.getDirection(knowledgebase, lastDirection, fromLocation, Arrays.asList(availableDirections));
	}

	@Override
	public void move(Coordinate fromLocation, Coordinate toLocation)
	{
		CoordinateInfo currLocation = knowledgebase.get(fromLocation);
		currLocation.incrementNumVisits();
		currLocation.incrementVisitedNeighbours();
		currLocation.setLastVisit(time);
		CoordinateInfo newLocation = knowledgebase.get(toLocation);
		if (currLocation.isDoor())
		{
			newLocation.setDirectionToDoor(getOpposite(lastDirection));
		}
		else
		{
			if (currLocation.getToDoor() != null)
			{
				newLocation.setDirectionToDoor(getOpposite(lastDirection));
			}
			else
			{
				checkNeighboursForDoor(toLocation);
			}
		}
		time++;
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
