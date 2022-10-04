package net.yammyy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: HelloServlet
 *
 */
public class SimpleServlet extends HttpServlet {

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public SimpleServlet() {
        super();
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
         HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String title = "HTTP Request Example";

        String contentType = "<!DOCTYPE html>\n";

        writer.println(contentType + "<html>\n" +
                       "<head><title>" + title + "</title></head>" +
                       "<body>" +
                       "<h1>HTTP Servlet Request Example </h1>"
        );
        Enumeration headers = request.getHeaderNames();

        while (headers.hasMoreElements()) {
            String parameterName = (String) headers.nextElement();
            String parameterValue = request.getHeader(parameterName);
            writer.println("<h3>" + parameterName + ": " + parameterValue + "</h3>" + "\n");
        }

        writer.println("</body>");
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
         HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}