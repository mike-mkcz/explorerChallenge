package com.insano10.explorerchallenge.session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insano10.explorerchallenge.maze.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MazeSession
{
    private static final Gson GSON = new GsonBuilder().create();

    private MazeFileLoader mazeLoader = new MazeFileLoader();
    private String mapRoot;
    private Maze maze;

    public MazeSession(String mapRoot)
    {
        this.mapRoot = mapRoot;
    }

    public void getExitsFromLocation(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
        response.getWriter().println(GSON.toJson(maze.getExitsFrom(fromLocation)));
    }

    public void getEntrance(HttpServletResponse response) throws IOException
    {
        response.getWriter().println(GSON.toJson(maze.getEntrance()));
    }

    public void attemptMove(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
        Direction direction = GSON.fromJson(request.getParameter("direction"), Direction.class);

        try
        {
            Coordinate newLocation = maze.move(fromLocation, direction);
            boolean exitReached = maze.isExit(newLocation);

            MoveOutcome moveOutcome = new MoveOutcome(newLocation, exitReached);

            response.getWriter().println(GSON.toJson(moveOutcome));

        } catch (InvalidMoveException e)
        {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Going " + direction + " from " + fromLocation + " is not a valid move");
        }
    }

    public void setMaze(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        maze = loadMaze(request.getParameter("file"));
        response.getWriter().println(GSON.toJson(maze));
    }

    private Maze loadMaze(String fileName) throws IOException
    {
        return mazeLoader.loadFromFile(Paths.get(mapRoot, fileName));
    }


}
