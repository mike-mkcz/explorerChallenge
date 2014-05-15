package com.insano10.explorerchallenge.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        if(response instanceof HttpServletResponse)
        {
            HttpServletResponse alteredResponse = ((HttpServletResponse)response);
            alteredResponse.addHeader("Access-Control-Allow-Origin", "*");
            alteredResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            alteredResponse.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
            alteredResponse.addHeader("Access-Control-Max-Age", "1728000");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig)throws ServletException{}
}