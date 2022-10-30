package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Currency;
import net.yammyy.units.goods.Language;
import net.yammyy.units.users.Reason;
import net.yammyy.units.users.Type;
import net.yammyy.units.users.User;
import net.yammyy.utils.HTMLLinks;
import net.yammyy.utils.LogMessages;

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

@WebServlet(
        name = "UserServlet",
        urlPatterns = "/userRecord")
public class UserServlet extends HttpServlet {
    private static final String thisName = "LoginServlet";

    private void processRequest(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentServlet</title>");
            out.println("</head>");
            out.println("<body>");

            List<User> std = new ArrayList<>();

            System.out.println("Забираем соединение");
            DBManager dbManager_l = DBManager.getInstance();

            System.out.println("Забираем пользователей");
            List<User> usr = dbManager_l.getUsers();
            System.out.println("UserServlet 1");

            System.out.println("UserServlet 4");
            request.setAttribute("data", usr);

            RequestDispatcher rd =
                    request.getRequestDispatcher("userRecord.jsp");

            rd.forward(request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameterMap().isEmpty()) {
            processRequest(request, response);
        } else {
            DBManager dbManager_l = DBManager.getInstance();
            int u_id = Integer.parseInt(request.getParameter("user_id"));
            User user_l = dbManager_l.findUser(u_id);
            request.setAttribute("user", user_l);
            response.setContentType("text/html;charset=UTF-8");
            RequestDispatcher rdCur = request.getRequestDispatcher("editUserProfile.jsp");
            rdCur.include(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(thisName + " 1");
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            DBManager dbManager_l = DBManager.getInstance();
            //Получаем все параметры
            int _id = Integer.parseInt(request.getParameter("userID"));
            String userFamilyName_l = request.getParameter("userFamily");
            String userName_l = request.getParameter("userName");
            String userFatherName = request.getParameter("userFather");
            String userPassword = request.getParameter("userPassword");
            String userEmail = request.getParameter("userEmail");
            int role_id = Integer.parseInt(request.getParameter("userRole"));
            Type userRole = dbManager_l.getRoleByID(role_id);
            int language_id = Integer.parseInt(request.getParameter("userLanguage"));
            Language userStandardLanguage = dbManager_l.getLanguageByID(language_id);
            int currency_id = Integer.parseInt(request.getParameter("userCurrency"));
            Currency userStandardCurrency = dbManager_l.getCurrencyByID(currency_id);
            int reason_id = Integer.parseInt(request.getParameter("userBlocking"));
            Reason userReason = dbManager_l.getReasonByID(reason_id);
            //Пытаемся отправить в базу
            User user_l = new User(_id, "", userPassword);
            user_l.setFIO(userFamilyName_l, userName_l, userFatherName);
            user_l.setPassword(userPassword);
            user_l.setEmail(userEmail);
            user_l.setRole(userRole);
            user_l.setStandardLanguage(userStandardLanguage);
            user_l.setStandardCurrency(userStandardCurrency);
            user_l.block((reason_id != 0));
            user_l.setBlockingReason((reason_id != 0), userReason);
            boolean allOk = dbManager_l.updateUser(_id, user_l);
            //И переходим обратно
            response.sendRedirect(request.getContextPath() + HTMLLinks.USER_ADMINISTRATION);
        } catch (IOException _e) {
            System.out.println(thisName + " doPost " + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}