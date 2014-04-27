package com.insano10.explorerchallenge.maze;

import com.insano10.explorerchallenge.maze.utils.TestMazes;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;

public class MazeTest
{
    private Maze maze;
    private Coordinate entrance;

    @Before
    public void setUp() throws Exception
    {
         /*hard code a test grid

        00000
          000
        0 000
        0   0
        000 0

        [0,0] is at bottom left corner
        */

        maze = TestMazes.testMazeOne();
        entrance = maze.getEntrance();
    }

    @Test
    public void shouldDoValidMove() throws Exception
    {
        Coordinate newLocation = maze.move(entrance, Direction.EAST);
        assertThat(newLocation, equalTo(Coordinate.create(1, 3)));
    }

    @Test
    public void shouldRecogniseExit()
    {
       assertTrue(maze.isExit(Coordinate.create(3, 0)));
    }

    @Test
    public void shouldNotClassifyLocationAsExitIfItIsNotTheExit()
    {
        assertFalse(maze.isExit(entrance));
    }

    @Test(expected = InvalidMoveException.class)
    public void shouldNotAllowMoveIntoMazeWall() throws Exception
    {
        Coordinate justPastEntrance = Coordinate.create(1, 3);
        maze.move(justPastEntrance, Direction.EAST);
    }

    @Test(expected = InvalidMoveException.class)
    public void shouldNotAllowMovementOutsideNorthBoundary() throws Exception
    {
        Coordinate location = Coordinate.create(2, 4);
        maze.move(location, Direction.NORTH);
    }

    @Test(expected = InvalidMoveException.class)
    public void shouldNotAllowMovementOutsideSouthBoundary() throws Exception
    {
        Coordinate location = Coordinate.create(2, 0);
        maze.move(location, Direction.SOUTH);
    }

    @Test(expected = InvalidMoveException.class)
    public void shouldNotAllowMovementOutsideEastBoundary() throws Exception
    {
        Coordinate location = Coordinate.create(4, 2);
        maze.move(location, Direction.EAST);
    }

    @Test(expected = InvalidMoveException.class)
    public void shouldNotAllowMovementOutsideWestBoundary() throws Exception
    {
        Coordinate location = Coordinate.create(0, 2);
        maze.move(location, Direction.WEST);
    }

    @Test
    public void shouldAllowValidPathThroughMaze() throws Exception
    {
        Coordinate newLocation = maze.move(entrance, Direction.EAST);
        newLocation = maze.move(newLocation, Direction.SOUTH);
        newLocation = maze.move(newLocation, Direction.SOUTH);
        newLocation = maze.move(newLocation, Direction.EAST);
        newLocation = maze.move(newLocation, Direction.EAST);
        newLocation = maze.move(newLocation, Direction.SOUTH);

        assertTrue(maze.isExit(newLocation));
    }
}
