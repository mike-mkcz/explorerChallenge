package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.explorer.state.State;
import com.insano10.explorerchallenge.explorer.state.StateFactory;
import com.insano10.explorerchallenge.explorer.world.CoordinateInfo;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import static com.insano10.explorerchallenge.explorer.world.World.worldInstance;

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
		//		currentState = StateFactory.getOrderedWanderingState();
		//		currentState = StateFactory.getRandomWanderingState();
		//		currentState = StateFactory.getTremauxWanderingState();
		currentState = StateFactory.getTremauxRandomWanderingState();
	}

	@Override
	public Direction whichWayNow(final Coordinate fromLocation, final Direction[] availableDirections)
	{
		lastDirection = currentState.getDirection(lastDirection, fromLocation, Utils.orderDirections(availableDirections));
		return lastDirection;
	}

	@Override
	public void move(Coordinate fromLocation, Coordinate toLocation)
	{
		CoordinateInfo from = worldInstance().computeIfAbsent(fromLocation);
		from.incrementVisitedNeighbours();
		from.setLastVisit(worldInstance().getTime());
		
		if (worldInstance().doorFound())
		{
			CoordinateInfo to = worldInstance().computeIfAbsent(toLocation);
			if (to.getStepsToDoor() > from.getStepsToDoor() + 1)
			{
				to.setStepsToDoor(from.getStepsToDoor() + 1);
			}
		}

		worldInstance().tick();
	}

	@Override
	public void keyFound(Key key, Coordinate location)
	{
		this.key = key;
		if (worldInstance().doorFound())
		{
			worldInstance().updateDoorCost();
			currentState = StateFactory.getQuittingState();
		}
	}

	@Override
	public Key getKey()
	{
		return key;
	}

	@Override
	public void exitReached(Coordinate location)
	{
		worldInstance().markDoorLocation(location);
	}

	@Override
	public void exitMaze()
	{

	}
}
