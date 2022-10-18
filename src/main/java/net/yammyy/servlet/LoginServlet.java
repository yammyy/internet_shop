package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.servletFilter.AppUtils;
import net.yammyy.units.users.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet (
        name = "LoginServlet",
        urlPatterns = "/*")
public class LoginServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            request.setAttribute("errorMessage", "");
            User userAccount=new User(0,"","");
            request.setAttribute("user",userAccount);
            RequestDispatcher dispatcher = request.getRequestDispatcher("loginView.jsp");
            dispatcher.forward(request, response);
        }
        catch (ServletException|IOException _e)
        {
            throw new RuntimeException(_e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println("Забираем соединение");
        try
        {
            DBManager dbManager_l=DBManager.getInstance();
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            User userAccount = dbManager_l.findUser(userName, password);
            if (userAccount==null)
            {
                String errorMessage="Invalid userName or Password";
                request.setAttribute("errorMessage", errorMessage);
                userAccount=new User(0,"","");
                request.setAttribute("user",userAccount);
                RequestDispatcher dispatcher = request.getRequestDispatcher("loginView.jsp");
                dispatcher.forward(request, response);
                return;
            }
            AppUtils.storeLoginedUser(request.getSession(), userAccount);
            int redirectId = -1;
            try {redirectId = Integer.parseInt(request.getParameter("redirectId"));}
            catch (Exception ignored_l) {}
            String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
            if (requestUri != null) {response.sendRedirect(requestUri);}
            //По умолчанию перенаправить на contactus
            else {response.sendRedirect(request.getContextPath() + "/index");}
        }
        catch (SQLException _e)
        {
            throw new RuntimeException(_e);
        }

    }

}
