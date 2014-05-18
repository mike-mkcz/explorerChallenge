package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

public interface Explorer
{
    String getName();

    void enterMaze(Coordinate startLocation);

    void exitMaze();

    Direction whichWayNow(Coordinate fromLocation, Direction[] availableDirections);

    void move(Coordinate fromLocation, Coordinate toLocation);

    void keyFound(Key key, Coordinate location);
}
