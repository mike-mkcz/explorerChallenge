package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class StupidExplorer implements Explorer
{
    private static final Logger LOGGER = Logger.getLogger(StupidExplorer.class);

    @Override
    public String getName()
    {
        return "Indiana Bones";
    }

    @Override
    public void enterMaze(Coordinate startLocation)
    {
        LOGGER.info("I'm going in! Starting location: " + startLocation);
    }

    @Override
    public void exitMaze()
    {
        LOGGER.info("How the hell did I do that?");
    }

    @Override
    public Direction whichWayNow(Coordinate fromLocation, Direction[] availableDirections)
    {
        LOGGER.info("From " + fromLocation + " despite being told I can go " + Arrays.toString(availableDirections) + " I think I'll go EAST");
        return Direction.EAST;
    }

    @Override
    public void move(Coordinate fromLocation, Coordinate toLocation)
    {
        LOGGER.info("Guess I'll go a bit further! Moving from " + fromLocation + " to " + toLocation);
    }

    @Override
    public void keyFound(Key key, Coordinate location)
    {
        LOGGER.info("OMG I found a key. So happy!");
    }

    @Override
    public Key getKey()
    {
        LOGGER.info("Oh Noes I lost my key");
        return null;
    }
}
