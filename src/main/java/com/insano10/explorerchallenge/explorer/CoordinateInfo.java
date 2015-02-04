package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikec on 27/01/15.
 */
public class CoordinateInfo
{
	private int numVisits;
	private int lastVisit;
	private int visitedNeighbours;
	private int stepsToDoor;
	private List<Direction> activeNeighbours;
	private boolean isDoor;
	private boolean isWall;
	private boolean isDeadEnd; //or leadsToDeadEnd

	public CoordinateInfo()
	{
		this.numVisits = 0;
		this.lastVisit = -1;
		this.visitedNeighbours = 0;
		this.activeNeighbours = new ArrayList<>();
		this.isDeadEnd = false;
		this.stepsToDoor = Integer.MAX_VALUE;
		this.isDoor = false;
		this.isWall = false;
	}

	public void incrementNumVisits()
	{
		numVisits++;
	}

	public boolean isVisited()
	{
		return numVisits > 0;
	}

	public void setLastVisit(final int lastVisit)
	{
		this.lastVisit = lastVisit;
	}

	public void markAsWall()
	{
		isWall = true;
	}

	public boolean isWall()
	{
		return isWall;
	}

	public void markAsDeadEnd()
	{
		if (!isDoor())
		{
			isDeadEnd = true;
		}
	}

	public boolean isDeadEnd()
	{
		return isDeadEnd;
	}

	public void markAsDoor()
	{
		isDoor = true;
		setStepsToDoor(0);
		if (isDeadEnd)
		{
			isDeadEnd = false;
		}
	}

	public boolean isDoor()
	{
		return isDoor;
	}
	
	public void setStepsToDoor(final int stepsToDoor)
	{
		this.stepsToDoor = stepsToDoor;
	}
	
	public int getStepsToDoor()
	{
		return stepsToDoor;
	}

	public void setActiveNeighbours(final List<Direction> activeNeighbours)
	{
		this.activeNeighbours = new ArrayList<>(activeNeighbours);
		if (this.activeNeighbours.size() == 1)
		{
			if (!isDoor())
			{
				markAsDeadEnd();
			}
		}
	}
	
	public List<Direction> getActiveNeighbours()
	{
		return activeNeighbours;
		
	}

	public void incrementVisitedNeighbours()
	{
		if (visitedNeighbours < activeNeighbours.size())
		{
			visitedNeighbours++;
		}
	}

	public int computeScore(final int time)
	{
		if (isWall() || (isDeadEnd() && !isDoor()))
		{
			return Integer.MIN_VALUE;
		}
		int score = 0;
		if (!isVisited())
		{
			score += 10000;
		}
		score += (activeNeighbours.size() - visitedNeighbours) * 20;
		if (lastVisit > -1)
		{
			score += (time - lastVisit);
		}
		score -= numVisits;
		return score;
	}

	@Override
	public String toString()
	{
		return "CoordinateInfo{" +
		       "numVisits=" + numVisits +
		       ", lastVisit=" + lastVisit +
		       ", visitedNeighbours=" + visitedNeighbours +
		       ", activeNeighbours=" + activeNeighbours +
		       ", stepsToDoor=" + (stepsToDoor == Integer.MAX_VALUE ? "unknown" : stepsToDoor) +
		       ", isDoor=" + isDoor +
		       ", isWall=" + isWall +
		       ", isDeadEnd=" + isDeadEnd +
		       '}';
	}
}
