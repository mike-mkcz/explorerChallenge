package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import org.apache.log4j.Logger;

public class StupidExplorer implements Explorer
{
    private static final Logger LOGGER = Logger.getLogger(StupidExplorer.class);

    @Override
    public void enterMaze(Coordinate startLocation)
    {
        LOGGER.info("I'm going in! Starting location: " + startLocation);
    }

    @Override
    public Direction whichWayNow()
    {
        LOGGER.info("I like east, I'm gonna go east");
        return Direction.EAST;
    }

    @Override
    public void move(Coordinate fromLocation, Direction direction)
    {
        LOGGER.info("Guess I'll go a bit further! Moving " + direction + " from " + fromLocation);
    }

    @Override
    public void exitMaze()
    {
        LOGGER.info("How the hell did I do that?");
    }
}
