package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class LeftHandWallExplorer implements Explorer
{
    private static final Logger LOGGER = Logger.getLogger(LeftHandWallExplorer.class);

    private Direction currentlyFacing;
    private Key key;

    @Override
    public String getName()
    {
        return "Mr Lefty";
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
        List<Direction> directions = Arrays.asList(availableDirections);

        Direction[] directionPreference = getDirectionPreference();

        for(int i=0 ; i<4 ; i++)
        {
            if(directions.contains(directionPreference[i]))
            {
                currentlyFacing = directionPreference[i];
                break;
            }
        }

        return currentlyFacing;
    }

    @Override
    public void move(Coordinate fromLocation, Coordinate toLocation)
    {
        LOGGER.info("Guess I'll go a bit further! Moving from " + fromLocation + " to " + toLocation);
    }

    @Override
    public void keyFound(Key key, Coordinate location)
    {
        LOGGER.info("Ooooh a key! I'll put this somewhere for safe keeping");
        this.key = key;
    }

    /*
    From whichever way you are facing, try and follow the left hand wall
     */
    private Direction[] getDirectionPreference()
    {
        Direction[] preference = new Direction[4];

        if(currentlyFacing == Direction.EAST)
        {
            preference[0] = Direction.NORTH;
            preference[1] = Direction.EAST;
            preference[2] = Direction.SOUTH;
            preference[3] = Direction.WEST;
        }
        else if(currentlyFacing == Direction.SOUTH)
        {
            preference[0] = Direction.EAST;
            preference[1] = Direction.SOUTH;
            preference[2] = Direction.WEST;
            preference[3] = Direction.NORTH;
        }
        else if(currentlyFacing == Direction.WEST)
        {
            preference[0] = Direction.SOUTH;
            preference[1] = Direction.WEST;
            preference[2] = Direction.NORTH;
            preference[3] = Direction.EAST;
        }
        else if(currentlyFacing == Direction.NORTH)
        {
            preference[0] = Direction.WEST;
            preference[1] = Direction.NORTH;
            preference[2] = Direction.EAST;
            preference[3] = Direction.SOUTH;
        }
        else
        {
            preference[0] = Direction.WEST;
            preference[1] = Direction.NORTH;
            preference[2] = Direction.EAST;
            preference[3] = Direction.SOUTH;
        }

        return preference;
    }
}
