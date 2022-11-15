package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.units.users.User;
import net.yammyy.utils.AppUtils;
import net.yammyy.units.goods.Good;
import net.yammyy.utils.HTMLLinks;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet(name = "AddCartServlet", urlPatterns = "/changelist")
public class AddCartServlet extends HttpServlet {
    final String thisName = "AddCartServlet";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendError(501, "This page doesn't support that request");
        } catch (IOException e) {
            System.out.println(thisName + LogMessages.GET_REQUEST_ERROR + " 501" + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            User loginedUser = AppUtils.getLoginedUser(request.getSession(false));
            DBManager dbManager = DBManager.getInstance();
            System.out.println(thisName + LogMessages.POST_REQUEST + LogMessages.START_REQUEST);
            System.out.println(thisName + LogMessages.POST_REQUEST + "Get which button was clicked");
            String plusMinus2 = request.getParameter(HTMLLinks.BUTTON_REMOVE_FROM_CART);
            boolean fav = (request.getParameter(HTMLLinks.BUTTON_ADD_TO_FAV) != null);
            boolean plusMinus = false;
            if (!fav) {
                plusMinus = (plusMinus2 == null);
            }
            System.out.println(thisName + LogMessages.POST_REQUEST + "Get where to redirect after processing"+ fav + " "+plusMinus);
            String requestUri = request.getParameter(HTMLLinks.PARAMETER_REDIRECT_URI);
            String categoryId = request.getParameter(HTMLLinks.PARAMETER_CATEGORY_ID);
            if (categoryId != null) {
                requestUri = requestUri + "?" + HTMLLinks.PARAMETER_CATEGORY_ID + "=" + categoryId;
            }
            System.out.println(thisName + LogMessages.POST_REQUEST + "Get which good to add to cart");
            int goodId = Integer.parseInt(request.getParameter(HTMLLinks.PARAMETER_GOOD_ID));
            Good good = dbManager.findGood(goodId);
            System.out.println(thisName + LogMessages.POST_REQUEST + "Get quantity");
            int quantity = Integer.parseInt((Objects.equals(request.getParameter(HTMLLinks.PARAMETER_GOOD_QUANTITY),
                    "")) ? "1" : request.getParameter(HTMLLinks.PARAMETER_GOOD_QUANTITY));
            if (loginedUser == null) {//If user is not registered then use session for cart
                if (plusMinus) { //If it is adding button
                    System.out.println(thisName + LogMessages.POST_REQUEST + "Add to session cart");
                    AppUtils.storeGoodToSessionCart(request.getSession(), good, quantity);
                } else {
                    System.out.println(thisName + LogMessages.POST_REQUEST + "Remove from session cart");
                    AppUtils.removeGoodFromSessionCart(request.getSession(), good, quantity);
                }
            } else {//else deliver it to database
                if (fav){//If we want to add to favorite
                    System.out.println(thisName + LogMessages.POST_REQUEST + "Add good to favorite");
                    boolean result = dbManager.insertGoodToList(loginedUser, 1, good);
                    if (result) {
                        loginedUser.addGoodToList(1,good,1);
                    }
                } else if (plusMinus) { //If it is adding button
                    System.out.println(thisName + LogMessages.POST_REQUEST + "Store good to cart");
                    int newQuantity = loginedUser.countGoodInCart(good);
                    boolean result = false;
                    if (newQuantity == 0) {//Это значит, что товара в корзине нет
                        result = dbManager.storeGoodToCart(loginedUser, good, quantity);
                    } else {
                        result = dbManager.updateGoodInCart(loginedUser, good, newQuantity + quantity);
                    }
                    if (result) {
                        loginedUser.storeGoodToCart(good, quantity);
                    }
                } else {
                    boolean result = false;
                    int newQuantity = loginedUser.hasMoreGoodInCart(good, quantity);
                    if (newQuantity == 0) {// Это значит, что товара не осталось и нужно удалить его из корзины
                        System.out.println(thisName + LogMessages.POST_REQUEST + "Remove good from cart");
                        result = dbManager.removeGoodFromCart(loginedUser, 2, good);
                    } else {
                        System.out.println(thisName + LogMessages.POST_REQUEST + "Update good in cart");
                        result = dbManager.updateGoodInCart(loginedUser, good, newQuantity);
                    }
                    if (result) {
                        loginedUser.removeGoodFromCart(good, quantity);
                    }
                }
            }
            response.sendRedirect(requestUri);
        } catch (IOException _e) {
            System.out.println(thisName + " doPost " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
