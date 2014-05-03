package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

public interface Explorer
{
    void enterMaze(Coordinate startLocation);

    Direction whichWayNow(Coordinate fromLocation, Direction[] availableDirections);

    void move(Coordinate fromLocation, Direction direction);

    void exitMaze();
}