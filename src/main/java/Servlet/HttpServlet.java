package Servlet;

import Http.Request.LeeHttpRequest;
import Http.Response.LeeHttpResponse;

import java.io.PrintWriter;

public class HttpServlet {
    public void init() {

    }

    public void doGet(LeeHttpRequest request, LeeHttpResponse response) {
        PrintWriter out = new PrintWriter(response.getOutputStream());
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        out.write("name is " + name + "\r\n");
        out.write("password is " + password);
        out.flush();
    }
    
}
