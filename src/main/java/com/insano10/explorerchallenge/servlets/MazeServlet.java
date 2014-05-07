package com.insano10.explorerchallenge.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insano10.explorerchallenge.maze.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

public class MazeServlet extends HttpServlet
{
    private static final Gson GSON = new GsonBuilder().create();
    private static final String MAP_ROOT = "src/main/resources/mazes/";

    private MazeFileLoader mazeLoader = new MazeFileLoader();
    private Maze maze;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/entrance"))
        {
            getEntrance(response);
        }
        else if (request.getPathInfo().equals("/exits"))
        {
            getExitsFromLocation(request, response);
        }
        else if (request.getPathInfo().equals("/mazes"))
        {
            getMazes(response);
        }
        else
        {
            throw new RuntimeException("Unknown get request: " + request.getPathInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (request.getPathInfo().equals("/maze"))
        {
            setMaze(request, response);
        }
        else if (request.getPathInfo().equals("/move"))
        {
            attemptMove(request, response);
        }
        else
        {
            throw new RuntimeException("Unknown post: " + request.getPathInfo());
        }
    }

    private void getMazes(HttpServletResponse response) throws IOException
    {
        File mapFolder = new File(MAP_ROOT);
        String[] mazeFiles = mapFolder.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".maze");
            }
        });
        Arrays.sort(mazeFiles);
        response.getWriter().println(GSON.toJson(mazeFiles));
    }

    private void getExitsFromLocation(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
        response.getWriter().println(GSON.toJson(maze.getExitsFrom(fromLocation)));
    }

    private void getEntrance(HttpServletResponse response) throws IOException
    {
        response.getWriter().println(GSON.toJson(maze.getEntrance()));
    }

    private void attemptMove(HttpServletRequest request, HttpServletResponse response) throws IOException
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

    private void setMaze(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        maze = loadMaze(request.getParameter("file"));
        response.getWriter().println(GSON.toJson(maze));
    }

    private Maze loadMaze(String fileName) throws IOException
    {
        File mazeFile = new File(MAP_ROOT + fileName);

        return mazeLoader.loadFromFile(mazeFile);
    }
}