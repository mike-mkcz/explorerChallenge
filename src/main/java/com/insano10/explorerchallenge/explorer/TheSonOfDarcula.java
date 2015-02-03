package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by mikec on 27/01/15.
 */
public class TheSonOfDarcula implements Explorer
{
	private static final String NAME = "Darcula Jr.";

	private Key mazeKey;
	private Map<Coordinate, CoordinateInfo> whatIKnow;
	private int time;
	private Direction chosenDirection;

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public void enterMaze(Coordinate startLocation)
	{
		mazeKey = null;
		if (whatIKnow == null)
		{
			whatIKnow = new HashMap<>();
		}
		whatIKnow.clear();
		time = 0;

	}

	@Override
	public Direction whichWayNow(final Coordinate fromLocation, final Direction[] availableDirections)
	{
		CoordinateInfo currLocation = whatIKnow.computeIfAbsent(fromLocation, location -> new CoordinateInfo());

		if (currLocation == null)
		{
			throw new IllegalStateException("There should be no way computeIfAbsent returns null");
		}

		currLocation.setActiveNeighbours(availableDirections.length);

		Arrays.stream(CoordinateUtils.ORDERED_DIRECTIONS).forEach(direction -> {
			Coordinate neighbour = CoordinateUtils.getCoordsFromDirection(fromLocation, direction);
			whatIKnow.putIfAbsent(neighbour, new CoordinateInfo());
			if (!CoordinateUtils.inArray(direction, availableDirections))
			{
				whatIKnow.get(neighbour).markAsWall();
			}
		});

		if (availableDirections.length == 1)
		{
			currLocation.markAsDeadEnd();
		}
//		else
//		{
//			if (CoordinateUtils.isDeadEnd(fromLocation, whatIKnow))
//			{
//				currLocation.isDeadEnd();
//			}
//		}

		checkNeighboursForDoor(fromLocation);

		if (getKey() != null && CoordinateUtils.inArray(currLocation.getToDoor(), availableDirections))
		{
			return currLocation.getToDoor();
		}

		final Map<Direction, Integer> directionScore = new HashMap<>();
		int tmpScore = -1;

		System.out.println("==============================================================");
		System.out.println("Current Location: " + fromLocation);
		for (Direction direction : availableDirections)
		{
			Coordinate neighbourCoordinate = CoordinateUtils.getCoordsFromDirection(fromLocation, direction);
			CoordinateInfo neighbour = whatIKnow.get(neighbourCoordinate);
			int score = neighbour.computeScore(time);
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
		final Direction fromDirection = chosenDirection;
		chosenDirection = null;
		if (maxScoreDirections.size() == 1)
		{
			chosenDirection = maxScoreDirections.get(0).getKey();
		}
		else
		{
			List<Map.Entry<Direction, Integer>> nonOppositeDirections = maxScoreDirections.stream()
			                                                                              .filter(entry -> !CoordinateUtils
					                                                                              .isOpposite(fromDirection, entry.getKey()))
			                                                                              .collect(Collectors.toList());
			if (nonOppositeDirections.size() == 1)
			{
				chosenDirection = nonOppositeDirections.get(0).getKey();
			}
			else
			{
				for (Map.Entry<Direction, Integer> entry : nonOppositeDirections)
				{
					if (fromDirection.equals(entry.getKey()))
					{
						chosenDirection = entry.getKey();
						return chosenDirection;
					}
				}
				for (Direction direction : CoordinateUtils.ORDERED_DIRECTIONS)
				{
					for (Map.Entry<Direction, Integer> entry : nonOppositeDirections)
					{
						if (direction.equals(entry.getKey()))
						{
							chosenDirection = entry.getKey();
							return chosenDirection;
						}
					}
				}
			}
		}

		return chosenDirection;
	}

	@Override
	public void move(Coordinate fromLocation, Coordinate toLocation)
	{
		CoordinateInfo currLocation = whatIKnow.get(fromLocation);
		currLocation.incrementNumVisits();
		currLocation.incrementVisitedNeighbours();
		currLocation.setLastVisit(time);
		CoordinateInfo newLocation = whatIKnow.get(toLocation);
		if (currLocation.isDoor())
		{
			newLocation.setDirectionToDoor(CoordinateUtils.getOpposite(chosenDirection));
		}
		else
		{
			if (currLocation.getToDoor() != null)
			{
				newLocation.setDirectionToDoor(CoordinateUtils.getOpposite(chosenDirection));
			}
			else
			{
				checkNeighboursForDoor(toLocation);
			}
		}
		time++;
	}

	@Override
	public void keyFound(Key key, Coordinate location)
	{
		mazeKey = key;
	}

	@Override
	public Key getKey()
	{
		return mazeKey;
	}

	@Override
	public void exitReached(Coordinate location)
	{
		whatIKnow.get(location).markAsDoor();
	}

	@Override
	public void exitMaze()
	{

	}

	private void checkNeighboursForDoor(final Coordinate location)
	{
		CoordinateInfo locationInfo = whatIKnow.get(location);
		for (Direction direction : CoordinateUtils.ORDERED_DIRECTIONS)
		{
			Coordinate neighbourCoordinate = CoordinateUtils.getCoordsFromDirection(location, direction);
			CoordinateInfo neighbour = whatIKnow.get(neighbourCoordinate);
			if (neighbour != null)
			{
				if (neighbour.isDoor())
				{
					locationInfo.setDirectionToDoor(direction);
					return;
				}
				else if (neighbour.getToDoor() != null)
				{
					locationInfo.setDirectionToDoor(direction);
					return;
				}
			}
		}
	}
}
