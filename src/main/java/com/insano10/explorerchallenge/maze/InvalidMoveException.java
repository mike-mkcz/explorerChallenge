package com.insano10.explorerchallenge.maze;

public class InvalidMoveException extends Exception
{
    public InvalidMoveException(Coordinate fromLocation, Direction direction)
    {
        super("Cannot move from " + fromLocation + " in direction " + direction);
    }
}
