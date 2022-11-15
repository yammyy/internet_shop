package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.units.order.Order;
import net.yammyy.utils.AppUtils;
import net.yammyy.units.users.User;
import net.yammyy.utils.HTMLLinks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = "/loginView")
public class LoginServlet extends HttpServlet {
    private static final String thisName = "LoginServlet";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("errorMessage", "");
            User userAccount = new User(0, "", "");
            request.setAttribute("user", userAccount);
            RequestDispatcher dispatcher = request.getRequestDispatcher("loginView.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException _e) {
            System.out.println(thisName + LogMessages.GET_REQUEST + LogMessages.POST_REQUEST_ERROR + _e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println(thisName + LogMessages.POST_REQUEST + LogMessages.START_REQUEST);
            DBManager dbManager = DBManager.getInstance();
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            User userAccount = dbManager.findUser(userName, password);
            if (userAccount == null) {
                String errorMessage = "Invalid password. Please try again";
                request.setAttribute("errorMessage", errorMessage);
                userAccount = new User(0, "", "");
                request.setAttribute("user", userAccount);
                RequestDispatcher dispatcher = request.getRequestDispatcher("loginView.jsp");
                dispatcher.forward(request, response);
                return;
            }
            AppUtils.storeLoginedUser(request.getSession(), userAccount);
            List<Order> orderList = AppUtils.getGoodFromSessionCart(request.getSession());
            boolean result = dbManager.insertGoodsToList(userAccount, 2, orderList);
            if (result) {
                userAccount.addGoodsToList(2, orderList);
                request.getSession().removeAttribute(HTMLLinks.PARAMETER_SESSION_CART);
            }
            int redirectID = -1;
            try {
                redirectID = Integer.parseInt(request.getParameter(HTMLLinks.PARAMETER_REDIRECT_ID));
            } catch (Exception ignored_l) {
            }
            String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectID);
            if (requestUri != null) {
                response.sendRedirect(requestUri);
            } else {
                response.sendRedirect(request.getContextPath() + HTMLLinks.HOME_PAGE_LINK);
            }
        } catch (ServletException | IOException | SQLException _e) {
            System.out.println(thisName + " doPost " + LogMessages.POST_REQUEST + " " + _e.getMessage());
        }

    }

}
