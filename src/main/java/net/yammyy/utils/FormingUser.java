package net.yammyy.utils;

import net.yammyy.units.users.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormingUser
{
    public static void formProfile (HttpServletRequest _request, HttpServletResponse _response)
            throws IOException, ServletException
    {
        User user_l=(User)_request.getSession().getAttribute(HTMLLinks.PARAMETER_SESSION_USER);
        _request.setAttribute(HTMLLinks.PARAMETER_SESSION_USER,user_l);
        RequestDispatcher rdCur=_request.getRequestDispatcher("userProfile.jsp");
        rdCur.include(_request, _response);
    }
}
