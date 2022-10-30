package net.yammyy.servlet;

import net.yammyy.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "LoginedUserServlet",
        urlPatterns = "/userProfile")
public class LoginedUserServlet extends HttpServlet {
    private static final String thisName = "LoginedUserServlet";

    private void processRequest(HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException, SQLException {
        int thisLogLine = 0;
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        _response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer_l = _response.getWriter();
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем header с используемыми стилями");
        List<String> csss_l = new ArrayList<>();
        csss_l.add("categories");
        csss_l.add("navbar");
        csss_l.add("avatar");
        FormingHTMLElements.formingHeader(_request, _response, csss_l, HTMLLinks.TITLE + " - Товары");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем body");
        writer_l.println("<body>");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем навигацию - боковую и верхнюю");
        FormingNavigation.formNavBar(_request, _response);
        writer_l.println("<div id=\"mainContent\" class=\"container\">" +
                "<div class=\"row\">");
        writer_l.println("<div class=\"col-2\">");
        writer_l.println("</div>");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем список товаров");
        writer_l.println("<div class=\"col-10\">");
        FormingUser.formProfile(_request, _response);
        writer_l.println("</div>" +//col
                "</div>" +//row
                "</div>");//container
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем js-scripts");
        List<String> jss_l = new ArrayList<>();
        jss_l.add("navbar");
        FormingHTMLElements.formingJSSection(_request, _response, jss_l);
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        writer_l.println("</body>\n" + "</html>");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + "End");
    }

    @Override
    protected void doGet(HttpServletRequest _request, HttpServletResponse _response) {
        try {
            processRequest(_request, _response);
        } catch (SQLException | ServletException | IOException _e) {
            System.out.println(thisName + " doGet " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (SQLException | ServletException | IOException _e) {
            System.out.println(thisName + " doPost " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }
}
