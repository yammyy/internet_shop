package net.yammyy.servlet;

import net.yammyy.utils.HTMLLinks;
import net.yammyy.utils.LogMessages;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    private static final String thisName = "LogoutServlet";

    private void processRequest(HttpServletRequest _request, HttpServletResponse _response) {
        try {
            _request.getSession().invalidate();
            _response.sendRedirect(_request.getContextPath() + HTMLLinks.HOME_PAGE_LINK);
        } catch (IOException _e) {
            System.out.println(thisName + " processRequest " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest _request, HttpServletResponse _response) {
        processRequest(_request, _response);
    }

    @Override
    protected void doPost(HttpServletRequest _request, HttpServletResponse _response) {
        processRequest(_request, _response);
    }

}
