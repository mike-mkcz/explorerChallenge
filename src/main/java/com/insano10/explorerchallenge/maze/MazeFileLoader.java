package com.insano10.explorerchallenge.maze;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MazeFileLoader
{
    public Maze loadFromFile(File file) throws IOException
    {
        int width = -1;
        int height = -1;
        Coordinate entrance = null;
        Coordinate exit = null;
        boolean[][] grid = null;

        int gridLinesScanned = 0;
        int expectedDefinedSquares = 0;
        int actualDefinedSquares = 0;

        try(Scanner scanner = new Scanner(file))
        {

            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();

                if(line.startsWith("width"))
                {
                    width = Integer.parseInt(line.split("=")[1]);
                }
                else if(line.startsWith("height"))
                {
                    height = Integer.parseInt(line.split("=")[1]);
                }
                else if(line.startsWith("entrance"))
                {
                    entrance = getCoordinateFromString(line.split("=")[1]);
                }
                else if(line.startsWith("exit"))
                {
                    exit = getCoordinateFromString(line.split("=")[1]);
                }
                else if(line.equals("#maze"))
                {
                    validate(entrance, exit, width, height, file.getAbsolutePath());
                    expectedDefinedSquares = width * height;
                    grid = new boolean[width][height];
                }
                else if(line.startsWith("!"))
                {
                    int currentYCoordinate = height - gridLinesScanned - 1;
                    char[] squares = line.replace("!", "").toCharArray();

                    for(int i=0 ; i<squares.length ; i++)
                    {
                        grid[i][currentYCoordinate] = canMoveThroughGridSquare(squares[i]);
                        actualDefinedSquares++;
                    }

                    gridLinesScanned++;
                }
            }

            if(expectedDefinedSquares != actualDefinedSquares)
            {
                throw new RuntimeException("invalid maze file: " + file.getAbsolutePath());
            }

            return new Maze(grid, entrance, exit);
        }
    }

    private void validate(Coordinate entrance, Coordinate exit, int width, int height, String filePath)
    {
        if(entrance == null || exit == null || width == -1 || height == -1)
        {
            throw new RuntimeException("invalid maze file: " + filePath);
        }
    }

    private Coordinate getCoordinateFromString(String str)
    {
        String[] coordinates = str.split(",");
        return Coordinate.create(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
    }

    private boolean canMoveThroughGridSquare(char c)
    {
        return c == '1';
    }
}
