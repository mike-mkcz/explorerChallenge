package com.insano10.explorerchallenge.maze;

public enum Direction
{
    NORTH(0, 1),
    SOUTH(0, -1),
    EAST(1, 0),
    WEST(-1, 0);

    private int xOffset;
    private int yOffset;

    private Direction(int xOffset, int yOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getxOffset()
    {
        return xOffset;
    }

    public int getyOffset()
    {
        return yOffset;
    }
}
