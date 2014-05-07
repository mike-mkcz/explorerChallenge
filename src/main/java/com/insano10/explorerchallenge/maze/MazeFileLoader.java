package com.insano10.explorerchallenge.maze;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MazeFileLoader
{
    public Maze loadFromFile(File file) throws IOException
    {
        int width;
        int height;
        Coordinate entrance;
        Coordinate exit;
        boolean[][] grid;

        int gridLinesScanned = 0;

        List<String> lines = new ArrayList<>();

        try(Scanner scanner = new Scanner(file))
        {
            while(scanner.hasNextLine())
            {
                lines.add(scanner.nextLine());
            }

            width = getIntValueFromLines(lines, 0, "width");
            height = getIntValueFromLines(lines, 1, "height");
            entrance = getCoordinateValueFromLines(lines, 2, "entrance");
            exit = getCoordinateValueFromLines(lines, 3, "exit");

            grid = new boolean[width][height];

            for(String line : lines.subList(4, lines.size()))
            {
                if(line.startsWith("!"))
                {
                    if(gridLinesScanned >= height)
                    {
                        throw new RuntimeException("Defined grid should be height " + height);
                    }

                    int currentYCoordinate = height - gridLinesScanned - 1;
                    char[] squares = line.replace("!", "").toCharArray();

                    if(squares.length != width)
                    {
                        throw new RuntimeException("Gridline " + (gridLinesScanned+1) + " is not of width " + width);
                    }

                    for(int i=0 ; i<squares.length ; i++)
                    {
                        grid[i][currentYCoordinate] = canMoveThroughGridSquare(squares[i]);
                    }

                    gridLinesScanned++;
                }
            }

            if(gridLinesScanned != height)
            {
                throw new RuntimeException("Defined grid should be height " + height);
            }

            return new Maze(grid, entrance, exit);
        }
    }

    private int getIntValueFromLines(List<String> lines, int indexOfValue, String valueId)
    {
        try
        {
            String line = lines.get(indexOfValue);
            String[] tokens = line.split("=");

            if(tokens[0].equals(valueId))
            {
                return Integer.parseInt(tokens[1]);
            }
        }
        catch(Exception e)
        {
            //continue to exception
        }
        throw new RuntimeException("Invalid " + valueId + " specified on line " + (indexOfValue+1));
    }

    private Coordinate getCoordinateValueFromLines(List<String> lines, int indexOfValue, String valueId)
    {
        try
        {
            String line = lines.get(indexOfValue);
            String[] tokens = line.split("=");
            if(tokens[0].equals(valueId))
            {
                String[] coordinates = tokens[1].split(",");
                return Coordinate.create(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            }
        }
        catch(Exception e)
        {
            //continue to exception
        }
        throw new RuntimeException("Invalid " + valueId + " specified on line " + (indexOfValue+1));
    }

    private boolean canMoveThroughGridSquare(char c)
    {
        if(c == '1')
        {
            return true;
        }
        else if(c == '0')
        {
            return false;
        }
        else
        {
            throw new RuntimeException("Grid contains invalid character: " + c);
        }
    }
}
