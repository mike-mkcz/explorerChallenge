package com.insano10.explorerchallenge.servlets;

import com.insano10.explorerchallenge.explorer.Explorer;
import com.insano10.explorerchallenge.explorer.StupidExplorer;
import com.insano10.explorerchallenge.session.ExplorerSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExplorerServlet extends HttpServlet
{
    private final Map<String, ExplorerSession> sessions = new HashMap<>();

    private Explorer createExplorer()
    {
        return new StupidExplorer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ExplorerSession explorerSession = getExplorerSession(request);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/whichWayNow"))
        {
            explorerSession.whichWay(request, response);
        }
        else if (request.getPathInfo().equals("/name"))
        {
            explorerSession.getName(response);
        }
        else if (request.getPathInfo().equals("/key"))
        {
            explorerSession.getKey(response);
        }
        else
        {
            throw new RuntimeException("Unknown get request: " + request.getPathInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ExplorerSession explorerSession = getExplorerSession(request);

        if (request.getPathInfo().equals("/enterMaze"))
        {
            explorerSession.enterMaze(request);
        }
        else if (request.getPathInfo().equals("/move"))
        {
            explorerSession.doMove(request);
        }
        else if (request.getPathInfo().equals("/key"))
        {
            explorerSession.keyFound(request);
        }
        else if (request.getPathInfo().equals("/exitReached"))
        {
            explorerSession.exitReached(request);
        }
        else if (request.getPathInfo().equals("/exitMaze"))
        {
            explorerSession.exitMaze();
        }
        else
        {
            throw new RuntimeException("Unknown post: " + request.getPathInfo());
        }
    }

    private ExplorerSession getExplorerSession(HttpServletRequest request)
    {
        String sessionId = request.getParameter(Constants.SESSION_ID);
        ExplorerSession explorerSession = sessions.get(sessionId);

        if(explorerSession == null)
        {
            explorerSession = new ExplorerSession(createExplorer());
            sessions.put(sessionId, explorerSession);
        }
        return explorerSession;
    }
}