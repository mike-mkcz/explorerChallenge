package com.insano10.explorerchallenge.maze.utils;

import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Key;
import com.insano10.explorerchallenge.maze.Maze;

public class TestMazes
{
    public static final Key MAZE_TWO_KEY = new Key("MAZE TWO");
    /*
    Coordinate [0,0] is in the bottom left corner.

      [2,0]    [2,2]
        0   0   0
        0   0   0
        0   0   0
      [0,0]    [2,0]

     */


    /*
       00000
    ->   000
       0 000
       0   0
       000 0
          |
          V
    */
    public static Maze testMazeOne()
    {
        final boolean[][] grid = new boolean[5][5];
        Coordinate entrance = Coordinate.create(0, 3);
        Coordinate exit = Coordinate.create(3, 0);

        grid[0][0] = false;
        grid[1][0] = false;
        grid[2][0] = false;
        grid[3][0] = true;
        grid[4][0] = false;

        grid[0][1] = false;
        grid[1][1] = true;
        grid[2][1] = true;
        grid[3][1] = true;
        grid[4][1] = false;

        grid[0][2] = false;
        grid[1][2] = true;
        grid[2][2] = false;
        grid[3][2] = false;
        grid[4][2] = false;

        grid[0][3] = true;
        grid[1][3] = true;
        grid[2][3] = false;
        grid[3][3] = false;
        grid[4][3] = false;

        grid[0][4] = false;
        grid[1][4] = false;
        grid[2][4] = false;
        grid[3][4] = false;
        grid[4][4] = false;

        return new Maze(grid, entrance, exit, null);
    }

    /*
        |
        V
       010000
       010111
       010101
       011101
       000001
            |
            V
    */
    public static Maze testMazeTwo()
    {
        final boolean[][] grid = new boolean[6][5];
        Coordinate entrance = Coordinate.create(1, 4);
        Coordinate exit = Coordinate.create(4, 0);

        grid[0][0] = false;
        grid[1][0] = false;
        grid[2][0] = false;
        grid[3][0] = false;
        grid[4][0] = false;
        grid[5][0] = true;

        grid[0][1] = false;
        grid[1][1] = true;
        grid[2][1] = true;
        grid[3][1] = true;
        grid[4][1] = false;
        grid[5][1] = true;

        grid[0][2] = false;
        grid[1][2] = true;
        grid[2][2] = false;
        grid[3][2] = true;
        grid[4][2] = false;
        grid[5][2] = true;

        grid[0][3] = false;
        grid[1][3] = true;
        grid[2][3] = false;
        grid[3][3] = true;
        grid[4][3] = true;
        grid[5][3] = true;

        grid[0][4] = false;
        grid[1][4] = true;
        grid[2][4] = false;
        grid[3][4] = false;
        grid[4][4] = false;
        grid[5][4] = false;

        return new Maze(grid, entrance, exit, MAZE_TWO_KEY);
    }
}
