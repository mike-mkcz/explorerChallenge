package com.insano10.explorerchallenge.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insano10.explorerchallenge.explorer.Explorer;
import com.insano10.explorerchallenge.explorer.LeftHandWallExplorer;
import com.insano10.explorerchallenge.explorer.StupidExplorer;
import com.insano10.explorerchallenge.maze.Coordinate;
import com.insano10.explorerchallenge.maze.Direction;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/whichWayNow"))
        {
            Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
            Direction[] availableDirections = GSON.fromJson(request.getParameter("availableDirections"), Direction[].class);

            response.getWriter().println(GSON.toJson(explorer.whichWayNow(fromLocation, availableDirections)));
        }
        else if (request.getPathInfo().equals("/name"))
        {
            response.getWriter().println(GSON.toJson(explorer.getName()));
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
            Coordinate entrance = GSON.fromJson(request.getParameter("entrance"), Coordinate.class);

            explorer.enterMaze(entrance);
        }
        else if (request.getPathInfo().equals("/move"))
        {
            Coordinate fromLocation = GSON.fromJson(request.getParameter("fromLocation"), Coordinate.class);
            Coordinate toLocation = GSON.fromJson(request.getParameter("toLocation"), Coordinate.class);

            explorer.move(fromLocation, toLocation);
        }
        else if (request.getPathInfo().equals("/exitMaze"))
        {
            explorer.exitMaze();
        }
        else
        {
            throw new RuntimeException("Unknown post: " + request.getPathInfo());
        }
    }
}