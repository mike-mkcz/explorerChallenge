package com.insano10.explorerchallenge.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insano10.explorerchallenge.maze.EmbeddedMazeProvider;
import com.insano10.explorerchallenge.session.MazeSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MazeServlet extends HttpServlet
{
    private static final Gson GSON = new GsonBuilder().create();

    private final Map<String, MazeSession> sessions = new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        MazeSession mazeSession = getMazeSession(request);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/entrance"))
        {
            mazeSession.getEntrance(response);
        }
        else if (request.getPathInfo().equals("/exits"))
        {
            mazeSession.getExitsFromLocation(request, response);
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
        MazeSession mazeSession = getMazeSession(request);

        if (request.getPathInfo().equals("/maze"))
        {
            mazeSession.setMaze(request, response);
        }
        else if (request.getPathInfo().equals("/move"))
        {
            mazeSession.attemptMove(request, response);
        }
        else
        {
            throw new RuntimeException("Unknown post: " + request.getPathInfo());
        }
    }

    private void getMazes(HttpServletResponse response) throws IOException
    {
        String[] mazeFiles = EmbeddedMazeProvider.getMazes();
        Arrays.sort(mazeFiles);
        response.getWriter().println(GSON.toJson(mazeFiles));
    }

    private MazeSession getMazeSession(HttpServletRequest request)
    {
        String sessionId = request.getParameter(Constants.SESSION_ID);
        MazeSession mazeSession = sessions.get(sessionId);

        if(mazeSession == null)
        {
            mazeSession = new MazeSession();
            sessions.put(sessionId, mazeSession);
        }
        return mazeSession;
    }
}