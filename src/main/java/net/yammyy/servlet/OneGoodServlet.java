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
        name = "OneGoodServlet",
        urlPatterns = "/good")
public class OneGoodServlet extends HttpServlet {
    private static final String thisName = "OneGoodServlet";

    private void processRequest(HttpServletRequest _request, HttpServletResponse _response)
            throws IOException, SQLException {
        int thisLogLine = 0;
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        _response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer_l = _response.getWriter();
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем header с используемыми стилями");
        List<String> csss_l = new ArrayList<>();
        csss_l.add("navbar");
        csss_l.add("categories");
        csss_l.add("avatar");
        FormingHTMLElements.formingHeader(_request, _response, csss_l, HTMLLinks.TITLE + " - Описание товара");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем body");
        writer_l.println("<body>");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем навигацию - боковую и верхнюю");
        FormingNavigation.formNavBar(_request, _response);
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем основной контент");
        writer_l.println("<div id=\"mainContent\" class=\"container\">" +
                "<div class=\"row\">");
        writer_l.println("<div class=\"col-2 main-container\">"+
                "<a href=\""+_request.getContextPath()+HTMLLinks.HOME_PAGE_LINK);
        if (!_request.getParameter(HTMLLinks.PARAMETER_CATEGORY_ID).equals("null")){
            writer_l.println("?"+HTMLLinks.PARAMETER_CATEGORY_ID+"="+_request.getParameter(HTMLLinks.PARAMETER_CATEGORY_ID));
        }
        writer_l.println("\">Назад</i></a>"+
                "</div>" +
                "<div class=\"col-10\">");
        FormingGoods.formOneGoodView(_request, _response);
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
        writer_l.println("</body></html>");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + "End");
    }

    @Override
    protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
            throws IOException {
        try {
            processRequest(_request, _response);
        } catch (SQLException _e) {
            System.out.println(thisName + " doGet " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest _request, HttpServletResponse _response)
            throws IOException {
        try {
            processRequest(_request, _response);
        } catch (SQLException _e) {
            System.out.println(thisName + " doPost " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }
}
