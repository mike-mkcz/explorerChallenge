package com.insano10.explorerchallenge.maze;

public class MoveOutcome
{
    private Coordinate location;
    private boolean exitReached;

    public MoveOutcome(Coordinate location, boolean exitReached)
    {
        this.location = location;
        this.exitReached = exitReached;
    }
}
