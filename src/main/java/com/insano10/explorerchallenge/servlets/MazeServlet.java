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
    public void init() throws ServletException
    {
        super.init();
        try
        {
            loadMaze("theonlywayiseast.maze");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load starting map one.maze");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/move"))
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
        else if (request.getPathInfo().equals("/entrance"))
        {
            response.getWriter().println(GSON.toJson(maze.getEntrance()));
        }
        else if (request.getPathInfo().equals("/exits"))
        {
            Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);

            response.getWriter().println(GSON.toJson(maze.getExitsFrom(fromLocation)));
        }
        else if (request.getPathInfo().equals("/mazes"))
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
            loadMaze(request.getParameter("file"));
        }
       else
        {
            throw new RuntimeException("Unknown post: " + request.getPathInfo());
        }
    }

    private void loadMaze(String fileName) throws IOException
    {
        File mazeFile = new File(MAP_ROOT + fileName);

        maze = mazeLoader.loadFromFile(mazeFile);
    }
}