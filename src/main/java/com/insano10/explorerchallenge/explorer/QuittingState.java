package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.List;

/**
 * Created by mikec on 2/3/15.
 */
public class QuittingState implements State
{
	@Override
	public Direction getDirection(Direction lastDirection, Coordinate location, List<Direction> availableDirections)
	{
		return null;
	}
}
