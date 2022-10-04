package net.yammyy.servlet;

import net.yammyy.units.users.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet (
        name = "UserServlet",
        urlPatterns = "/*")
public class UserServlet extends HttpServlet
{
    private void processRequest(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentServlet</title>");
            out.println("</head>");
            out.println("<body>");

            // List to hold Student objects
            List<User> std = new ArrayList<User>();

            // Adding members to the list. Here we are
            // using the parameterized constructor of
            // class "Student.java"
            std.add(new User(1,"Roxy Willard"));
            std.add(new User(2,"Todd Lanz"));
            std.add(new User(3,"Varlene Lade"));
            std.add(new User(4,"Julio Fairley"));
            std.add(new User(5,"Helena Carlow"));

            // Setting the attribute of the request object
            // which will be later fetched by a JSP page
            request.setAttribute("data", std);

            // Creating a RequestDispatcher object to dispatch
            // the request the request to another resource
            RequestDispatcher rd =
                    request.getRequestDispatcher("user-record.jsp");

            // The request will be forwarded to the resource
            // specified, here the resource is a JSP named,
            // "stdlist.jsp"
            rd.forward(request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
}