package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Good;
import net.yammyy.units.order.Order;
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

@WebServlet(name = "ShowCartServlet", urlPatterns = "/opencart")
public class ShowCartServlet extends HttpServlet {
    private final String thisName = "ShowCartServlet";

    private void processRequest(HttpServletRequest _request, HttpServletResponse _response) throws ServletException,
            IOException, SQLException {
        System.out.println(thisName + LogMessages.START_REQUEST);
        _response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer_l = _response.getWriter();
        System.out.println(thisName + " Формируем header с используемыми стилями");
        List<String> csss_l = new ArrayList<>();
        csss_l.add("navbar");
        csss_l.add("avatar");
        FormingHTMLElements.formingHeader(_request, _response, csss_l, HTMLLinks.TITLE + " - Корзина");
        System.out.println(thisName + " Формируем body");
        writer_l.println("<body>");
        System.out.println(thisName + " Формируем навигацию - боковую и верхнюю");
        FormingNavigation.formNavBar(_request, _response);
        writer_l.println("<div id=\"mainContent\" class=\"container\">" + "<div class=\"row\">" +
                "<div class=\"col-2 main-container\">" +
                "</div>");
        System.out.println(thisName + " Формируем список заказов");
        writer_l.println("<div class=\"col-10\">");
        User loginedUser = AppUtils.getLoginedUser(_request.getSession());
        List<Order> goodsInCart = null;
        if (loginedUser == null) {
            goodsInCart = AppUtils.getGoodFromSessionCart(_request.getSession());
        } else {
            goodsInCart = loginedUser.getCart();
        }
        System.out.println(thisName + " Получено товаров " + goodsInCart.size());
        writer_l.println("<form method=\"POST\" action=\"" + _request.getContextPath() + "/neworder\">" +
                "<div class=\"container\">");
        if (goodsInCart != null) {
            for (Order good : goodsInCart) {
                writer_l.println("<div class=\"row\">" +
                        "<div class=\"col\">" + good.getGood().getName() + "</div>" +
                        "<div class=\"col\">" + good.getGood().getDescription() + "</div>" +
                        "<div class=\"col\">" + good.getGood().getPrice() + "</div>" +
                        "<div class=\"col\">" + good.getQuantity() + "</div>" +
                        "</div>");
            }
        }
        writer_l.println("</div>" +
                "<input type=submit>" +
                "</form></div>" +//col
                "</div>" +//row
                "</div>");//container
        System.out.println(thisName + " Формируем js-scripts");
        List<String> jss_l = new ArrayList<>();
        jss_l.add("navbar");
        FormingHTMLElements.formingJSSection(_request, _response, jss_l);
        writer_l.println("</body></html>");
        System.out.println(thisName + LogMessages.END_REQUEST);
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
