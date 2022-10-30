package net.yammyy.servlet;

import net.yammyy.units.order.Order;
import net.yammyy.units.order.Orders;
import net.yammyy.units.users.User;
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

@WebServlet(name = "OrdersServlet", urlPatterns = "/orders")
public class OrdersServlet extends HttpServlet {

    private final String thisName = "OrderServlet";

    private void processRequest(HttpServletRequest request, HttpServletResponse _response) throws ServletException,
            IOException, SQLException {
        int thisLogLine = 0;
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        _response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = _response.getWriter();
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем header с используемыми стилями");
        List<String> csss_l = new ArrayList<>();
        csss_l.add("categories");
        csss_l.add("navbar");
        csss_l.add("avatar");
        FormingHTMLElements.formingHeader(request, _response, csss_l, HTMLLinks.TITLE + " - Товары");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем body");
        writer.println("<body>");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем навигацию - боковую и верхнюю");
        FormingNavigation.formNavBar(request, _response);
        writer.println("<div id=\"mainContent\" class=\"container\">" + "<div class=\"row\">");
        writer.println("<div class=\"col-2 main-container\">");
        writer.println("</div>");
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем список заказов");
        writer.println("<div class=\"col-10\">");
        User loginedUser = AppUtils.getLoginedUser(request.getSession());
        List<Orders> orders = loginedUser.getOrdersList();
        writer.println("<div class=\"container\">");
        if (request.getParameterMap().isEmpty() || ((!request.getParameter(HTMLLinks.PARAMETER_ORDER_ID).equals("1")) &&
                (!request.getParameter(HTMLLinks.PARAMETER_ORDER_ID).equals("2")))) {
            for (int i = 0; i < orders.size(); i++) {
                if ((orders.get(i).getID() != 1) && (orders.get(i).getID() != 2)) {
                    writer.println("<div class=row>");
                    writer.println("<div class=col-1>");
                    writer.println(orders.get(i).getID());
                    writer.println("</div>");
                    writer.println("<div class=col-11>");
                    List<Order> orderList = orders.get(i).getOrder();
                    for (int j = 0; j < orderList.size(); j++) {
                        writer.println(orderList.get(j).getGood().getName() + " ");
                        writer.println(orderList.get(j).getQuantity() + "<br>");
                    }
                    writer.println("</div>");
                    writer.println("</div>");
                }
            }
        } else if (request.getParameter(HTMLLinks.PARAMETER_ORDER_ID).equals("1")) { //favorites
            List<Order> favorite = loginedUser.getFavorites();
            for (int i = 0; i < favorite.size(); i++) {
                writer.println("<div class=row>" +
                        "<div class=col-4><a href=" + request.getContextPath()+HTMLLinks.ONE_GOOD_LINK + "?" + HTMLLinks.PARAMETER_GOOD_ID +
                        "=" + favorite.get(i).getGood().getID() + ">");
                writer.println(favorite.get(i).getGood().getName());
                writer.println("</a></div>");
                writer.println("<div class=col-4>");
                writer.println(favorite.get(i).getGood().getDescription());
                writer.println("</div>");
                writer.println("<div class=col-4>");
                writer.println(favorite.get(i).getGood().getPrice());
                writer.println("</div>");
                writer.println("<div class=col-4>");
                writer.println(favorite.get(i).getQuantity());
                writer.println("</div>");
                writer.println("</div>");
            }

        }
        writer.println("</div>");
        writer.println("</div>" +//col
                "</div>" +//row
                "</div>");//container
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine + " Формируем js-scripts");
        List<String> jss_l = new ArrayList<>();
        jss_l.add("navbar");
        FormingHTMLElements.formingJSSection(request, _response, jss_l);
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        writer.println("</body>\n" + "</html>");
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
