package com.insano10.explorerchallenge.session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insano10.explorerchallenge.explorer.Explorer;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;
import com.insano10.explorerchallenge.maze.Key;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExplorerSession
{
    private static final Gson GSON = new GsonBuilder().create();
    private final Explorer explorer;

    public ExplorerSession(Explorer explorer)
    {
        this.explorer = explorer;
    }

    public void whichWay(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
        Direction[] availableDirections = GSON.fromJson(request.getParameter("availableDirections"), Direction[].class);

        response.getWriter().println(GSON.toJson(explorer.whichWayNow(fromLocation, availableDirections)));
    }

    public void getName(HttpServletResponse response) throws IOException
    {
        response.getWriter().println(GSON.toJson(explorer.getName()));
    }

    public void getKey(HttpServletResponse response) throws IOException
    {
        response.getWriter().println(GSON.toJson(explorer.getKey()));
    }

    public void enterMaze(HttpServletRequest request)
    {
        Coordinate entrance = GSON.fromJson(request.getParameter("entrance"), Coordinate.class);
        explorer.enterMaze(entrance);
    }

    public void doMove(HttpServletRequest request)
    {
        Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
        Coordinate toLocation = GSON.fromJson(request.getParameter("toLocation"), Coordinate.class);

        explorer.move(fromLocation, toLocation);
    }

    public void keyFound(HttpServletRequest request)
    {
        Coordinate location = GSON.fromJson(request.getParameter("location"), Coordinate.class);
        Key key = GSON.fromJson(request.getParameter("key"), Key.class);

        explorer.keyFound(key, location);
    }

    public void exitReached(HttpServletRequest request)
    {
        Coordinate location = GSON.fromJson(request.getParameter("location"), Coordinate.class);
        explorer.exitReached(location);
    }

    public void exitMaze()
    {
        explorer.exitMaze();
    }
}
