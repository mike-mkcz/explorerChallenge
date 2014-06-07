package com.insano10.explorerchallenge.maze;

public class EmbeddedMazeProvider
{
    public static String[] getMazes()
    {
        return new String[]{"mazeOne"};
    }

    public static Maze getMaze(String mazeName)
    {
        return getMazeOne();
    }



    private static Maze getMazeOne()
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

        return new Maze(grid, entrance, exit);
    }
}
