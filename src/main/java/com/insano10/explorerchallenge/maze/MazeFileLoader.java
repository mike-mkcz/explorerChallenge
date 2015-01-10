package com.insano10.explorerchallenge.maze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MazeFileLoader
{

    private static final int WIDTH_LINE_INDEX = 0;
    private static final int HEIGHT_LINE_INDEX = 1;
    private static final int ENTRANCE_LINE_INDEX = 2;
    private static final int EXIT_LINE_INDEX = 3;
    private static final int KEY_LINE_INDEX = 4;
    private static final int MAZE_START_LINE_INDEX = 5;

    public Maze loadFromFile(Path filePath) throws IOException
    {
        int width;
        int height;
        Coordinate entrance;
        Coordinate exit;
        boolean[][] grid;
        KeyContainer keyContainer;

        int gridLinesScanned = 0;

        final List<String> lines = Files.readAllLines(filePath);

        width = getIntValueFromLines(lines, WIDTH_LINE_INDEX, "width");
        height = getIntValueFromLines(lines, HEIGHT_LINE_INDEX, "height");
        entrance = getCoordinateValueFromLines(lines, ENTRANCE_LINE_INDEX, "entrance");
        exit = getCoordinateValueFromLines(lines, EXIT_LINE_INDEX, "exit");
        keyContainer = getKeyContainerFromLines(lines, KEY_LINE_INDEX, "key");

        grid = new boolean[width][height];

        int totalMazeLinesScanned = 1;
        for (String line : lines.subList(MAZE_START_LINE_INDEX, lines.size()))
        {
            if (line.startsWith("!"))
            {
                if (gridLinesScanned >= height)
                {
                    throw new RuntimeException("Defined grid should be height " + height);
                }

                int currentYCoordinate = height - gridLinesScanned - 1;
                char[] squares = line.replace("!", "").toCharArray();

                if (squares.length != width)
                {
                    throw new RuntimeException("Gridline " + (gridLinesScanned + 1) + " is not of width " + width);
                }

                for (int i = 0; i < squares.length; i++)
                {
                    grid[i][currentYCoordinate] = canMoveThroughGridSquare(squares[i]);
                }

                gridLinesScanned++;
            }
            else
            {
                if (!line.equals("#maze"))
                {
                    throw new RuntimeException("Invalid content found on line " + (MAZE_START_LINE_INDEX + totalMazeLinesScanned));
                }
            }

            totalMazeLinesScanned++;
        }

        if (gridLinesScanned != height)
        {
            throw new RuntimeException("Defined grid should be height " + height);
        }

        if (keyContainer == null)
        {
            return new Maze(grid, entrance, exit);
        }
        else
        {
            return new Maze(grid, entrance, exit, keyContainer.getKey(), keyContainer.getKeyLocation());
        }
    }

    private int getIntValueFromLines(List<String> lines, int indexOfValue, String valueId)
    {
        try
        {
            String line = lines.get(indexOfValue);
            String[] tokens = line.split("=");

            if (tokens[0].equals(valueId))
            {
                return Integer.parseInt(tokens[1]);
            }
        }
        catch (Exception e)
        {
            //continue to exception
        }
        throw new RuntimeException("Invalid " + valueId + " specified on line " + (indexOfValue + 1));
    }

    private Coordinate getCoordinateValueFromLines(List<String> lines, int indexOfValue, String valueId)
    {
        try
        {
            String line = lines.get(indexOfValue);
            String[] tokens = line.split("=");
            if (tokens[0].equals(valueId))
            {
                return getCoordinateFromString(tokens[1]);
            }
        }
        catch (Exception e)
        {
            //continue to exception
        }
        throw new RuntimeException("Invalid " + valueId + " specified on line " + (indexOfValue + 1));
    }

    private KeyContainer getKeyContainerFromLines(List<String> lines, int indexOfValue, String valueId)
    {
        try
        {
            String line = lines.get(indexOfValue);
            String[] tokens = line.split("=");

            if (tokens[0].equals(valueId))
            {
                String[] keySplit = tokens[1].split("@");
                String password = keySplit[0];
                Coordinate keyLocation = getCoordinateFromString(keySplit[1]);
                return new KeyContainer(new Key(password), keyLocation);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to read key on line " + (indexOfValue + 1) + ". Should be of format 'key=PASSWORD@0,0'");
        }
        return null;
    }

    private Coordinate getCoordinateFromString(String coordinateString)
    {
        String[] coordinates = coordinateString.split(",");
        return Coordinate.create(Integer.parseInt(coordinates[0].trim()), Integer.parseInt(coordinates[1].trim()));
    }

    private boolean canMoveThroughGridSquare(char c)
    {
        if (c == '1')
        {
            return true;
        }
        else if (c == '0')
        {
            return false;
        }
        else
        {
            throw new RuntimeException("Grid contains invalid character: " + c);
        }
    }

    private static class KeyContainer
    {
        private Key key;
        private Coordinate keyLocation;

        public KeyContainer(Key key, Coordinate keyLocation)
        {
            this.key = key;
            this.keyLocation = keyLocation;
        }

        public Key getKey()
        {
            return key;
        }

        public Coordinate getKeyLocation()
        {
            return keyLocation;
        }
    }
}
