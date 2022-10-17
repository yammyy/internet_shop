package net.yammyy.servlet;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Good;
import net.yammyy.units.goods.GoodParameter;
import net.yammyy.units.goods.Parameter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet (
        name = "GoodsServlet",
        urlPatterns = "/*")
public class GoodsServlet extends HttpServlet
{
    private void processRequest(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println("Обрабатываем запрос к товаров");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            System.out.println("Забираем соединение");
            DBManager dbManager_l=DBManager.getInstance();

            Map<Integer,Parameter> params_l = dbManager_l.getAllParameters();
            request.setAttribute("params", params_l);

            System.out.println("Забираем товары");
            List<Good> goods_l = dbManager_l.getGoods();

            request.setAttribute("data", goods_l);

            RequestDispatcher rd = request.getRequestDispatcher("goods.jsp");

            rd.forward(request, response);
        }
        catch (SQLException _e)
        {
            System.out.println("Ошибка: "+_e.getErrorCode()+" "+_e.getSQLState());
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
}