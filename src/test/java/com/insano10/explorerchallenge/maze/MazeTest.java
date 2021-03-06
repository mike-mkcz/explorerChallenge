package com.insano10.explorerchallenge.maze;

import com.insano10.explorerchallenge.maze.utils.TestMazes;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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
    public void shouldGetAvailableExitDirectionsFromLocationWithOnlyOneExit()
    {
        Direction[] exits = maze.getExitsFrom(entrance);

        assertThat(exits.length, equalTo(1));
        assertThat(exits[0], equalTo(Direction.EAST));
    }

    @Test
    public void shouldGetAvailableExitDirectionsFromLocationWithMultipleExits()
    {
        Coordinate location = Coordinate.create(3, 1);
        Direction[] exits = maze.getExitsFrom(location);

        assertThat(exits.length, equalTo(2));
        assertTrue(Arrays.asList(exits).contains(Direction.SOUTH));
        assertTrue(Arrays.asList(exits).contains(Direction.WEST));
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

    @Test(expected = InvalidMoveException.class)
    public void shouldNotAllowMovementWhenADirectionHasNotBeenProvided() throws Exception
    {
        Coordinate location = Coordinate.create(0, 2);
        maze.move(location, null);
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

    @Test
    public void shouldNotRequireKeyToExit() throws Exception
    {
        assertFalse(maze.requiresKeyToExit());
    }

    @Test
    public void shouldRequireKeyToExit() throws Exception
    {
        Maze mazeWithKey = TestMazes.testMazeTwo();
        assertTrue(mazeWithKey.requiresKeyToExit());
    }

    @Test
    public void shouldUnlockExitWithCorrectKeyAtCorrectLocation() throws Exception
    {
        Maze mazeWithKey = TestMazes.testMazeTwo();
        assertTrue(mazeWithKey.unlockExitAtLocation(Coordinate.create(4, 0), TestMazes.MAZE_TWO_KEY));
    }

    @Test
    public void shouldNotUnlockExitIfLocationIsIncorrect() throws Exception
    {
        Maze mazeWithKey = TestMazes.testMazeTwo();
        assertFalse(mazeWithKey.unlockExitAtLocation(Coordinate.create(0, 0), TestMazes.MAZE_TWO_KEY));
    }

    @Test
    public void shouldNotUnlockExitWithWrongKey() throws Exception
    {
        Key key = new Key("NOT THE RIGHT KEY");
        Maze mazeWithKey = TestMazes.testMazeTwo();
        assertFalse(mazeWithKey.unlockExitAtLocation(Coordinate.create(4, 0), key));
    }

    @Test
    public void shouldGetKeyLocationIfMazeRequiresKey() throws Exception
    {
        Maze mazeWithKey = TestMazes.testMazeTwo();
        assertThat(mazeWithKey.getKeyLocation(), equalTo(TestMazes.MAZE_TWO_KEY_LOCATION));
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowExceptionWhenGettingKeyLocationFromMazeWithNoKey() throws Exception
    {
        maze.getKeyLocation();
    }
}
