package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.List;

/**
 * Created by mikec on 2/3/15.
 */
public interface State
{
	public Direction getDirection(Direction lastDirection, final Coordinate location, final List<Direction> availableDirections);
}
