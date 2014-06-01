package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;
import org.apache.log4j.Logger;

public class BrokenExplorer implements Explorer
{
    private static final Logger LOGGER = Logger.getLogger(BrokenExplorer.class);

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public void enterMaze(Coordinate startLocation)
    {
        LOGGER.info("enter maze");
    }

    @Override
    public void exitMaze()
    {
        LOGGER.info("exit maze");
    }

    @Override
    public Direction whichWayNow(Coordinate fromLocation, Direction[] availableDirections)
    {
        return null;
    }

    @Override
    public void move(Coordinate fromLocation, Coordinate toLocation)
    {
        LOGGER.info("move");
    }

    @Override
    public void keyFound(Key key, Coordinate location)
    {
        LOGGER.info("key found");
    }

    @Override
    public Key getKey()
    {
        return new Key("HAXOR");
    }

    @Override
    public void exitReached(Coordinate location)
    {
    }
}
