package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikec on 2/3/15.
 */
public final class World
{
    private static World INSTANCE;

    public static World getInstance()
    {
        if (World.INSTANCE == null)
        {
            World.INSTANCE = new World();
        }
        return World.INSTANCE;
    }

    private Map<Coordinate, CoordinateInfo> knowledgebase;
    private int time;

    private World()
    {
        knowledgebase = new HashMap<>();
        reset();
    }

    public void reset()
    {
        knowledgebase.clear();
        time = 0;
    }

    public Map<Coordinate, CoordinateInfo> getKnowledgebase()
    {
        return knowledgebase;
    }

    public CoordinateInfo computeIfAbset()

    public CoordinateInfo get(final Direction neighbour, final Coordinate fromLocation)
    {
        return get(CoordinateUtils.getCoordsFromDirection(neighbour, fromLocation));
    }

    public CoordinateInfo get(final Coordinate location)
    {
        return knowledgebase.get(location);
    }

    public void tick()
    {
        time++;
    }

    public int getTime()
    {
        return time;
    }
}
