package com.insano10.explorerchallenge.maze;

import com.insano10.explorerchallenge.maze.utils.TestMazes;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.unitils.reflectionassert.ReflectionAssert;

import java.io.File;

public class MazeFileLoaderTest
{
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MazeFileLoader fileLoader = new MazeFileLoader();

    @Test
    public void shouldLoadMazeOneFromFile() throws Exception
    {
        File mazeFile = new File("src/test/resources/mazes/one.maze");
        Maze expectedMaze = TestMazes.testMazeOne();

        Maze maze = fileLoader.loadFromFile(mazeFile);

        ReflectionAssert.assertReflectionEquals(expectedMaze, maze);
    }

    @Test
    public void shouldLoadMazeTwoFromFile() throws Exception
    {
        File mazeFile = new File("src/test/resources/mazes/two.maze");
        Maze expectedMaze = TestMazes.testMazeTwo();

        Maze maze = fileLoader.loadFromFile(mazeFile);

        ReflectionAssert.assertReflectionEquals(expectedMaze, maze);
    }

    @Test
    public void shouldLoadMazeWithWhitespaceInCoordinates() throws Exception
    {
        File mazeFile = new File("src/test/resources/mazes/coordinatesWithWhiteSpace.maze");
        Maze expectedMaze = TestMazes.testMazeWithWhiteSpace();

        Maze maze = fileLoader.loadFromFile(mazeFile);

        ReflectionAssert.assertReflectionEquals(expectedMaze, maze);
    }

    @Test
    public void firstLineMustBeWidth() throws Exception
    {
        assertRuntimeException("Invalid width specified on line 1");
        
        File mazeFile = new File("src/test/resources/mazes/badWidth.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void secondLineMustBeHeight() throws Exception
    {
        assertRuntimeException("Invalid height specified on line 2");

        File mazeFile = new File("src/test/resources/mazes/badHeight.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void thirdLineMustBeEntrance() throws Exception
    {
        assertRuntimeException("Invalid entrance specified on line 3");

        File mazeFile = new File("src/test/resources/mazes/badEntrance.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void fourthLineMustBeExit() throws Exception
    {
        assertRuntimeException("Invalid exit specified on line 4");

        File mazeFile = new File("src/test/resources/mazes/badExit.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWithGridThatIsNotTheSpecifiedWidth() throws Exception
    {
        assertRuntimeException("Gridline 3 is not of width 5");

        File mazeFile = new File("src/test/resources/mazes/badGridWidth.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWithGridThatIsNotTheSpecifiedHeight() throws Exception
    {
        assertRuntimeException("Defined grid should be height 5");

        File mazeFile = new File("src/test/resources/mazes/badGridHeight.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWithGridContainingInvalidSymbol() throws Exception
    {
        assertRuntimeException("Grid contains invalid character: A");

        File mazeFile = new File("src/test/resources/mazes/badGridSymbol.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWhereEntranceIsNotWithinTheBoundsOfTheMaze() throws Exception
    {
        assertRuntimeException("Entrance is not within the maze");

        File mazeFile = new File("src/test/resources/mazes/badGridEntranceCoords.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWhereExitIsNotWithinTheBoundsOfTheMaze() throws Exception
    {
        assertRuntimeException("Exit is not within the maze");

        File mazeFile = new File("src/test/resources/mazes/badGridExitCoords.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWhereSpecifiedKeyIsMalformed() throws Exception
    {
        assertRuntimeException("Failed to read key on line 5. Should be of format 'key=PASSWORD@0,0'");

        File mazeFile = new File("src/test/resources/mazes/badKey.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWhereMoreThanOneKeyHasBeenSpecified() throws Exception
    {
        assertRuntimeException("Invalid content found on line 6");

        File mazeFile = new File("src/test/resources/mazes/multipleKeys.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test
    public void shouldNotLoadMazeFileWhereGridDefinitionContainsInvalidContent() throws Exception
    {
        assertRuntimeException("Invalid content found on line 9");

        File mazeFile = new File("src/test/resources/mazes/invalidMazeContent.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    private void assertRuntimeException(String message)
    {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(message);
    }
}
