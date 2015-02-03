package com.insano10.explorerchallenge.explorer;

import com.insano10.explorerchallenge.maze.Direction;

/**
 * Created by mikec on 27/01/15.
 */
public class CoordinateInfo
{
	private int numVisits;
	private int lastVisit;
	private int visitedNeighbours;
	private int activeNeighbours;
	private Direction toDoor;
	private boolean isDoor;
	private boolean isWall;
	private boolean isDeadEnd; //or leadsToDeadEnd

	public CoordinateInfo()
	{
		this.numVisits = 0;
		this.lastVisit = -1;
		this.visitedNeighbours = 0;
		this.activeNeighbours = 0;
		this.isDeadEnd = false;
		this.toDoor = null;
		this.isDoor = false;
		this.isWall = false;
	}

	public void setDirectionToDoor(final Direction directionToDoor)
	{
		this.toDoor = directionToDoor;
	}

	public Direction getToDoor()
	{
		return toDoor;
	}

	public void markAsDoor()
	{
		isDoor = true;
		if (isDeadEnd)
		{
			isDeadEnd = false;
		}
	}

	public void incrementNumVisits()
	{
		numVisits++;
	}

	public void markAsWall()
	{
		isWall = true;
	}

	public void setLastVisit(final int lastVisit)
	{
		this.lastVisit = lastVisit;
	}

	public boolean isDeadEnd()
	{
		return isDeadEnd;
	}

	public void setActiveNeighbours(final int activeNeighbours)
	{
		this.activeNeighbours = activeNeighbours;
		if (this.activeNeighbours == 1)
		{
			if (!isDoor())
			{
				markAsDeadEnd();
			}
		}
	}

	public boolean isDoor()
	{
		return isDoor;
	}

	public void markAsDeadEnd()
	{
		if (!isDoor())
		{
			isDeadEnd = true;
		}
	}

	public void incrementVisitedNeighbours()
	{
		if (visitedNeighbours < activeNeighbours)
		{
			visitedNeighbours++;
		}
	}

	public int computeScore(final int time)
	{
		if (isWall() || (isWall() && !isDoor()))
		{
			return Integer.MIN_VALUE;
		}
		int score = 0;
		if (!isVisited())
		{
			score += 10000;
		}
		score += (activeNeighbours - visitedNeighbours) * 20;
		if (lastVisit > -1)
		{
			score += (time - lastVisit);
		}
		score -= numVisits;
		return score;
	}

	public boolean isWall()
	{
		return isWall;
	}

	public boolean isVisited()
	{
		return numVisits > 0;
	}

	@Override
	public String toString()
	{
		return "CoordinateInfo{" +
		       "numVisits=" + numVisits +
		       ", lastVisit=" + lastVisit +
		       ", visitedNeighbours=" + visitedNeighbours +
		       ", activeNeighbours=" + activeNeighbours +
		       ", toDoor=" + toDoor +
		       ", isDoor=" + isDoor +
		       ", isWall=" + isWall +
		       ", isDeadEnd=" + isDeadEnd +
		       '}';
	}
}
