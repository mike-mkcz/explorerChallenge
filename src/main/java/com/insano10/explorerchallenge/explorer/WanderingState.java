package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.insano10.explorerchallenge.explorer.CoordinateUtils.*;
import static com.insano10.explorerchallenge.explorer.CoordinateUtils.ORDERED_DIRECTIONS;

/**
 * Created by mikec on 2/3/15.
 */
public class WanderingState implements State {
    @Override
    public Direction getDirection(Map<Coordinate, CoordinateInfo> knowledgeBase, Direction lastDirection, Coordinate location, List<Direction> availableDirections) {
        CoordinateInfo currLocation = knowledgeBase.computeIfAbsent(location, cLocation -> new CoordinateInfo());

        if (currLocation == null)
        {
            throw new IllegalStateException("There should be no way computeIfAbsent returns null");
        }

        currLocation.setActiveNeighbours(availableDirections.size());

        ORDERED_DIRECTIONS.forEach(direction -> {
            Coordinate neighbour = getCoordsFromDirection(direction, location);
            knowledgeBase.putIfAbsent(neighbour, new CoordinateInfo());
            if (!availableDirections.contains(direction)) {
                knowledgeBase.get(neighbour).markAsWall();
            }
        });

        if (availableDirections.size() == 1)
        {
            currLocation.markAsDeadEnd();
        }
//		else
//		{
//			if (CoordinateUtils.isDeadEnd(location, knowledgeBase))
//			{
//				currLocation.isDeadEnd();
//			}
//		}

        checkNeighboursForDoor(location);

        final Map<Direction, Integer> directionScore = new HashMap<>();
        int tmpScore = -1;

        System.out.println("==============================================================");
        System.out.println("Current Location: " + location);
        for (Direction direction : availableDirections)
        {
            Coordinate neighbourCoordinate = getCoordsFromDirection(direction, location);
            CoordinateInfo neighbour = knowledgeBase.get(neighbourCoordinate);
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
        final Direction fromDirection = lastDirection;
        lastDirection = null;
        if (maxScoreDirections.size() == 1)
        {
            lastDirection = maxScoreDirections.get(0).getKey();
        }
        else
        {
            List<Map.Entry<Direction, Integer>> nonOppositeDirections = maxScoreDirections.stream()
                    .filter(entry -> !
                            isOpposite(fromDirection, entry.getKey()))
                    .collect(Collectors.toList());
            if (nonOppositeDirections.size() == 1)
            {
                lastDirection = nonOppositeDirections.get(0).getKey();
            }
            else
            {
                for (Map.Entry<Direction, Integer> entry : nonOppositeDirections)
                {
                    if (fromDirection.equals(entry.getKey()))
                    {
                        lastDirection = entry.getKey();
                        return lastDirection;
                    }
                }
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
