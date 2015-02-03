package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.insano10.explorerchallenge.explorer.CoordinateUtils.ORDERED_DIRECTIONS;
import static com.insano10.explorerchallenge.explorer.CoordinateUtils.isOpposite;
import static com.insano10.explorerchallenge.explorer.World.worldInstance;

/**
 * Created by mikec on 2/3/15.
 */
public class WanderingState implements State
{
	@Override
	public Direction getDirection(Direction lastDirection, Coordinate location, List<Direction> availableDirections)
	{

		CoordinateInfo currLocation = worldInstance().computeIfAbsent(location);

		if (currLocation == null)
		{
			throw new IllegalStateException("There should be no way computeIfAbsent returns null");
		}

		currLocation.setActiveNeighbours(availableDirections.size());

		ORDERED_DIRECTIONS.forEach(direction -> {
			CoordinateInfo neighbour = worldInstance().computeRelativeIfAbsent(direction, location);
			if (!availableDirections.contains(direction))
			{
				neighbour.markAsWall();
			}
		});

		final Map<Direction, Integer> directionScore = new HashMap<>();
		int tmpScore = -1;

		System.out.println("==============================================================");
		System.out.println("Current Location: " + location);
		for (Direction direction : availableDirections)
		{
			CoordinateInfo neighbour = worldInstance().computeRelativeIfAbsent(direction, location);
			int score = neighbour.computeScore(worldInstance().getTime());
			System.out.println(direction + " score: " + score + "   " + neighbour.toString());
			directionScore.put(direction, score);
			if (score > tmpScore)
			{
				tmpScore = score;
			}
		}
		System.out.println("==============================================================");

		final int maxScore = tmpScore;
		List<Map.Entry<Direction, Integer>> maxScoreDirections = directionScore.entrySet().stream().filter(entry -> entry.getValue() == maxScore)
		                                                                       .collect(Collectors.toList());
		final Direction fromDirection = lastDirection;
		lastDirection = null;
		if (maxScoreDirections.size() == 1)
		{
			lastDirection = maxScoreDirections.get(0).getKey();
		}
		else
		{
			List<Map.Entry<Direction, Integer>> nonOppositeDirections = maxScoreDirections.stream()
			                                                                              .filter(entry -> !isOpposite(fromDirection, entry.getKey()))
			                                                                              .collect(Collectors.toList());
			if (nonOppositeDirections.size() == 1)
			{
				lastDirection = nonOppositeDirections.get(0).getKey();
			}
			else
			{
				for (Direction direction : ORDERED_DIRECTIONS)
				{
					for (Map.Entry<Direction, Integer> entry : nonOppositeDirections)
					{
						if (direction.equals(entry.getKey()))
						{
							lastDirection = entry.getKey();
							return lastDirection;
						}
					}
				}
			}
		}

		return lastDirection;
	}
}
