package net.yammyy.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (
        name = "LogoutServlet",
        urlPatterns = "/*")
public class LogoutServlet extends HttpServlet
{
        private static final long serialVersionUID = 1L;
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException
        {
            request.getSession().invalidate();

            // Redirect to Home Page.
            response.sendRedirect(request.getContextPath() + "/index");

        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            this.doGet(request, response);
        }

}
