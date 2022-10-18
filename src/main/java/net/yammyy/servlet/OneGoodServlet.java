package net.yammyy.servlet;

import net.yammyy.utils.FormingGoods;
import net.yammyy.utils.FormingHTMLElements;
import net.yammyy.utils.FormingNavigation;

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
                urlPatterns = "/*")
public class OneGoodServlet extends HttpServlet
{

    private void processRequest (HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException, SQLException
    {
        System.out.println("----------------------------------------");
        System.out.println("OneGoodServlet 0");
        _response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer_l = _response.getWriter();
        System.out.println("OneGoodServlet 1");
        //Формируем header с используемыми стилями
        List<String> csss_l=new ArrayList<>();
        System.out.println("OneGoodServlet formingHeader 2.1");
        csss_l.add("navbar");
        System.out.println("OneGoodServlet formingHeader 2.2");
        FormingHTMLElements.formingHeader(_request, _response, csss_l);
        System.out.println("OneGoodServlet formingHeader 2.3");
        writer_l.println("</head>");
        System.out.println("OneGoodServlet formingHeader 2.4End");

        System.out.println("OneGoodServlet 3");
        //body
        writer_l.println("<body>");
        System.out.println("OneGoodServlet 4");
        //Формируем навигацию - боковую и верхнюю
        FormingNavigation.formNavBar(_request, _response);
        System.out.println("OneGoodServlet 5");
        writer_l.println("<div id=\"mainContent\" class=\"container\">" +
                         "<div class=\"row\">");
        writer_l.println("<div class=\"col-2 main-container\">");
        writer_l.println("</div>");
        System.out.println("OneGoodServlet 6");
        writer_l.println("<div class=\"col-10\">");
        FormingGoods.formOneGoodView(_request,_response);
        writer_l.println("</div>");
        System.out.println("OneGoodServlet 7");
        writer_l.print("</div></div>");
        //Формируем js-scripts
        List<String> jss_l=new ArrayList<>();
        jss_l.add("navbar");
        FormingHTMLElements.formingJSSection(_request,_response,jss_l);
        System.out.println("OneGoodServlet 8");
        writer_l.println("</body>\n"+"</html>");
        System.out.println("OneGoodServlet 9End");
    }
    @Override
    protected void doGet(
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
