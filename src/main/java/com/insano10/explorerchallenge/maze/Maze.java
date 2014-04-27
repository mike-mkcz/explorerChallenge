package com.insano10.explorerchallenge.maze;

import java.io.File;

public class Maze
{
    private int maxXCoordinate;
    private int maxYCoordinate;
    private boolean[][] grid;
    private Coordinate entrance;
    private Coordinate exit;
    private boolean keyRequiredToExit;

    public Maze(boolean[][] grid, Coordinate entrance, Coordinate exit, boolean keyRequiredToExit)
    {
        this.grid = grid;
        this.entrance = entrance;
        this.exit = exit;
        this.keyRequiredToExit = keyRequiredToExit;
        this.maxXCoordinate = grid.length - 1;
        this.maxYCoordinate = grid[0].length - 1;
    }

    public void load(File file)
    {

    }

    public boolean isKeyRequiredToExitMaze()
    {
        return keyRequiredToExit;
    }

    public Coordinate move(Coordinate fromLocation, Direction direction) throws InvalidMoveException
    {
        Coordinate newLocation = Coordinate.create(fromLocation.getX() + direction.getxOffset(),
                                                   fromLocation.getY() + direction.getyOffset());

        if (coordinateIsWithinMaze(newLocation))
        {
            if(grid[newLocation.getX()][newLocation.getY()])
            {
                return newLocation;
            }
        }

        throw new InvalidMoveException(fromLocation, direction);
    }

    public Coordinate getEntrance()
    {
        return entrance;
    }

    public boolean isExit(Coordinate location)
    {
        return exit.equals(location);
    }

    private boolean coordinateIsWithinMaze(Coordinate location)
    {
        if(location.getX() >= 0 && location.getX() <= maxXCoordinate &&
           location.getY() >= 0 && location.getY() <= maxYCoordinate)
        {
            return true;
        }
        return false;
    }
}
