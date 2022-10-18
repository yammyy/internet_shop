package net.yammyy.servlet;

import net.yammyy.utils.FormingGoods;
import net.yammyy.utils.FormingHTMLElements;
import net.yammyy.utils.FormingNavigation;

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
import java.util.List;

@WebServlet (
        name = "WelcomeServlet",
        urlPatterns = "/*")
public class WelcomeServlet extends HttpServlet
{
    private void processRequest (HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException, SQLException
    {
        System.out.println("----------------------------------------");
        System.out.println("WelcomeServlet 0");
        _response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer_l = _response.getWriter();
        System.out.println("WelcomeServlet 1");
        System.out.println("WelcomeServlet 2");
        //Формируем header с используемыми стилями
        List<String> csss_l=new ArrayList<>();
        System.out.println("WelcomeServlet 2.1");
        csss_l.add("categories");
        csss_l.add("navbar");
        System.out.println("WelcomeServlet 2.2");
        FormingHTMLElements.formingHeader(_request,_response,csss_l);

        System.out.println("WelcomeServlet formingHeader 3");
        writer_l.println("</head>");
        System.out.println("WelcomeServlet formingHeader 4End");


        System.out.println("WelcomeServlet 3");
        //body
        writer_l.println("<body>");
        System.out.println("WelcomeServlet 4");
        //Формируем навигацию - боковую и верхнюю
        FormingNavigation.formNavBar(_request, _response);
        System.out.println("WelcomeServlet 5");
        writer_l.println("<div id=\"mainContent\" class=\"container\">" +
                         "<div class=\"row\">");
        writer_l.println("<div class=\"col-2 main-container\">");
        //Формируем список категорий для боковой панели
        FormingGoods.formCategoriesList(_request,_response);
        writer_l.println("</div>");
        System.out.println("WelcomeServlet 7");
        writer_l.println("<div class=\"col-10\">");
        FormingGoods.formGoodsList(_request,_response);
        writer_l.println("</div>");
        System.out.println("WelcomeServlet 9");
        writer_l.print("</div></div>");
        //Формируем js-scripts
        List<String> jss_l=new ArrayList<>();
        jss_l.add("navbar");
        FormingHTMLElements.formingJSSection(_request,_response,jss_l);
        System.out.println("WelcomeServlet 10");
        writer_l.println("</body>\n"+"</html>");
        System.out.println("WelcomeServlet 11End");
    }
    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            //if (!(request.getServletPath().contains("css/")))
                processRequest(request, response);
        }
        catch (SQLException _e)
        {
            throw new RuntimeException(_e);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        }
        catch (SQLException _e)
        {
            throw new RuntimeException(_e);
        }
    }
}
