package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Good;
import net.yammyy.units.order.Orders;
import net.yammyy.units.users.User;
import net.yammyy.utils.AppUtils;
import net.yammyy.utils.HTMLLinks;
import net.yammyy.utils.LogMessages;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "AddOrderServlet", urlPatterns = "/neworder")
public class AddOrderServlet extends HttpServlet {
    final String thisName = "AddOrderServlet";

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException {
        User loginedUser = AppUtils.getLoginedUser(request.getSession());
        Orders cart = loginedUser.getGoodsInList(2);
        int newOrderID = loginedUser.getMaxOrderID() + 1;
        DBManager dbManager = DBManager.getInstance();
        Date date = new Date();
        boolean result = dbManager.orderFromCart(loginedUser, cart, newOrderID, date);
        if (result) {
            System.out.println("new order from cart");
            loginedUser.addNewOrder(new Orders(newOrderID), newOrderID);
            loginedUser.setStatus(newOrderID, dbManager.getStatus(1), date);
            loginedUser.addGoodsToList(newOrderID, cart.getOrder());
            loginedUser.clearCart();
            request.getSession().removeAttribute(HTMLLinks.PARAMETER_SESSION_CART);
        }
        response.sendRedirect(request.getContextPath() + HTMLLinks.USER_CART);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (IOException _e) {
            System.out.println(thisName + " doPost " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
