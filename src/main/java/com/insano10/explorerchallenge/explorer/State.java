package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import java.util.List;
import java.util.Map;

/**
 * Created by mikec on 2/3/15.
 */
public interface State
{
    public Direction getDirection(final Map<Coordinate, CoordinateInfo> knowledgeBase, Direction lastDirection, final Coordinate location, final List<Direction> availableDirections);
}
