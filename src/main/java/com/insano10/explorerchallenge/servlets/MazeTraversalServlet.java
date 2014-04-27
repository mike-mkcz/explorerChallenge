package com.insano10.explorerchallenge.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MazeTraversalServlet extends HttpServlet
{
    private static final Gson GSON = new GsonBuilder().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {


        response.getWriter().println("hello world");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}