package Servlet;

import Http.Request.LeeHttpRequest;
import Http.Response.LeeHttpResponse;

import java.io.PrintWriter;

public abstract class HttpServlet {
    public abstract void init();

    public abstract void doGet(LeeHttpRequest request, LeeHttpResponse response);

}
