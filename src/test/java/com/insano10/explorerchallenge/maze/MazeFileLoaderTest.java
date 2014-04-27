package com.insano10.explorerchallenge.maze;

import com.insano10.explorerchallenge.maze.utils.TestMazes;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.io.File;

public class MazeFileLoaderTest
{
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

    @Test(expected = RuntimeException.class)
    public void shouldNotLoadMazeFileMissingInformation() throws Exception
    {
        File mazeFile = new File("src/test/resources/mazes/missingField.maze");
        fileLoader.loadFromFile(mazeFile);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotLoadMazeFileWithIncompleteGrid() throws Exception
    {
        File mazeFile = new File("src/test/resources/mazes/invalidGrid.maze");
        fileLoader.loadFromFile(mazeFile);
    }
}
