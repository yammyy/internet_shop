package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Parameter;
import net.yammyy.units.users.Type;
import net.yammyy.units.users.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            System.out.println("Забираем соединение");
            DBManager dbManager_l=DBManager.getInstance();

            System.out.println("Забираем пользователей");
            Map<Integer,User> usr = dbManager_l.getUsers();
            System.out.println("UserServlet 1");
            Iterator it = usr.entrySet().iterator();
            System.out.println("UserServlet 3");
            while (it.hasNext())
            {
                System.out.println("UserServlet 3.1");
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println("UserServlet 3.2");
                std.add((User)pair.getValue());
                System.out.println(((User)pair.getValue()).getLogin());
            }

            System.out.println("UserServlet 4");
            // Setting the attribute of the request object
            // which will be later fetched by a JSP page
            request.setAttribute("data", std);

            // Creating a RequestDispatcher object to dispatch
            // the request the request to another resource
            RequestDispatcher rd =
                    request.getRequestDispatcher("userRecord.jsp");

            // The request will be forwarded to the resource
            // specified, here the resource is a JSP named,
            // "stdlist.jsp"
            rd.forward(request, response);
            out.println("</body>");
            out.println("</html>");
        }
        catch (SQLException _e)
        {
            System.out.println(_e.getErrorCode());
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