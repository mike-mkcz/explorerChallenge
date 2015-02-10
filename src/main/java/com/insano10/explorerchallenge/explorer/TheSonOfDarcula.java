package com.insano10.explorerchallenge.explorer;

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
    private final String name;
    private final boolean continueAlongChosenDirection;
    private final boolean isRandom;
    private final boolean useTremaux;
    private final boolean isSmartArse;

    private Key key;
	private Direction lastDirection;
	private ExplorerState currentExplorerState;

    public TheSonOfDarcula()
    {
        this(false, false, false, false);
    }

    public TheSonOfDarcula(final boolean continueAlongChosenDirection, final boolean isRandom, final boolean useTremaux, final boolean isSmartArse)
    {
        this.continueAlongChosenDirection = continueAlongChosenDirection;
        this.isRandom = isRandom;
        this.useTremaux = useTremaux;
        this.isSmartArse = isSmartArse;
        this.name = NAME + (this.continueAlongChosenDirection ? " C" : "") + (this.isRandom ? " R" : "") + (this.useTremaux ? " T" : "")+ (this.isSmartArse ? " S" : "") ;
    }

    @Override
	public String getName()
	{
		return name;
	}

	@Override
	public void enterMaze(Coordinate startLocation)
	{
		key = null;
		lastDirection = null;
		worldInstance().reset();
        currentExplorerState = new ExplorerState(continueAlongChosenDirection, isRandom, useTremaux, isSmartArse);
	}

	@Override
	public Direction whichWayNow(final Coordinate fromLocation, final Direction[] availableDirections)
	{
		lastDirection = currentExplorerState.getDirection(lastDirection, fromLocation, Utils.orderDirections(availableDirections));
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
			currentExplorerState.itsQuittingTime();
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
