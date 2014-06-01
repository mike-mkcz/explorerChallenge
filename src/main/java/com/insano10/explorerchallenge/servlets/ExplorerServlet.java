package com.insano10.explorerchallenge.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insano10.explorerchallenge.explorer.Explorer;
import com.insano10.explorerchallenge.explorer.LeftHandWallExplorer;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExplorerServlet extends HttpServlet
{
    private static final Gson GSON = new GsonBuilder().create();

    private Explorer explorer;

    @Override
    public void init() throws ServletException
    {
        super.init();
        explorer = new LeftHandWallExplorer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/whichWayNow"))
        {
            whichWay(request, response);
        }
        else if (request.getPathInfo().equals("/name"))
        {
            getName(response);
        }
        else if (request.getPathInfo().equals("/key"))
        {
            getKey(response);
        }
        else
        {
            throw new RuntimeException("Unknown get request: " + request.getPathInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (request.getPathInfo().equals("/enterMaze"))
        {
            enterMaze(request);
        }
        else if (request.getPathInfo().equals("/move"))
        {
            doMove(request);
        }
        else if (request.getPathInfo().equals("/key"))
        {
            keyFound(request);
        }
        else if (request.getPathInfo().equals("/exitReached"))
        {
            exitReached(request);
        }
        else if (request.getPathInfo().equals("/exitMaze"))
        {
            exitMaze();
        }
        else
        {
            throw new RuntimeException("Unknown post: " + request.getPathInfo());
        }
    }

    private void whichWay(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
        Direction[] availableDirections = GSON.fromJson(request.getParameter("availableDirections"), Direction[].class);

        response.getWriter().println(GSON.toJson(explorer.whichWayNow(fromLocation, availableDirections)));
    }

    private void getName(HttpServletResponse response) throws IOException
    {
        response.getWriter().println(GSON.toJson(explorer.getName()));
    }

    private void getKey(HttpServletResponse response) throws IOException
    {
        response.getWriter().println(GSON.toJson(explorer.getKey()));
    }

    private void enterMaze(HttpServletRequest request)
    {
        Coordinate entrance = GSON.fromJson(request.getParameter("entrance"), Coordinate.class);
        explorer.enterMaze(entrance);
    }

    private void doMove(HttpServletRequest request)
    {
        Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
        Coordinate toLocation = GSON.fromJson(request.getParameter("toLocation"), Coordinate.class);

        explorer.move(fromLocation, toLocation);
    }

    private void keyFound(HttpServletRequest request)
    {
        Coordinate location = GSON.fromJson(request.getParameter("location"), Coordinate.class);
        Key key = GSON.fromJson(request.getParameter("key"), Key.class);

        explorer.keyFound(key, location);
    }

    private void exitReached(HttpServletRequest request)
    {
        Coordinate location = GSON.fromJson(request.getParameter("location"), Coordinate.class);
        explorer.exitReached(location);
    }

    private void exitMaze()
    {
        explorer.exitMaze();
    }
}