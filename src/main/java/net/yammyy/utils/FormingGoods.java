package net.yammyy.utils;

import net.yammyy.db.DBManager;
import net.yammyy.servletFilter.AppUtils;
import net.yammyy.units.goods.ChoosableParameterValue;
import net.yammyy.units.goods.Good;
import net.yammyy.units.users.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class FormingGoods
{
    public static void formCategoriesList(HttpServletRequest _request, HttpServletResponse _response)
    {
        System.out.println("formCategoriesList 1");
        try
        {
            System.out.println("formCategoriesList 2");
            DBManager dbManager_l=DBManager.getInstance();
            System.out.println("formCategoriesList 3");
            Map<Integer, ChoosableParameterValue> allCategories_l = dbManager_l.getAllCategories();
            System.out.println("formCategoriesList 4 "+allCategories_l.size());
            _request.setAttribute("categories", allCategories_l);
            System.out.println("formCategoriesList 5");
            RequestDispatcher rd = _request.getRequestDispatcher("categories.jsp");
            System.out.println("formCategoriesList 6");
            rd.include(_request, _response);
            System.out.println("formCategoriesList 7End");
        }
        catch (SQLException _e)
        {
            System.out.println("Ошибка: "+_e.getErrorCode()+" "+_e.getSQLState());
        }
        catch (IOException|ServletException _e)
        {
            throw new RuntimeException(_e);
        }
    }
    public static void formGoodsList(HttpServletRequest _request, HttpServletResponse _response)
    {
        System.out.println("formGoodsList 1");
        try
        {
            System.out.println("formGoodsList 2");
            DBManager dbManager_l=DBManager.getInstance();
            System.out.println("formGoodsList 3");
            List<Good> allGoods_l = dbManager_l.getGoods();
            System.out.println("formGoodsList 4 "+allGoods_l.size());
            System.out.println("formGoodsList 5");
            PrintWriter writer_l=_response.getWriter();
            writer_l.println("<div class=\"row\">"+
                             "<div class=\"col-1\"></div>"+
                             "<div class=\"col-6\"></div>"+
                             "</div>"+
                             "<div class=\"row\">");
            for (int i=0;i<allGoods_l.size();i++)
            {
                writer_l.println("<div class=\"col col-xl-2 col-lg-3 col-md-4 col-sm-6\">"+
                                 "<img src=\"\" />"+
                                 "<a href=\""+_request.getContextPath()+"/good?good_id="+allGoods_l.get(i).getID()+"\">");
                writer_l.println(allGoods_l.get(i).getName());
                writer_l.println(allGoods_l.get(i).getDescription());
                writer_l.println(allGoods_l.get(i).getPrice());
                writer_l.println("</div>");
            }

            writer_l.println("</div>");
        }
        catch (SQLException _e)
        {
            System.out.println("Ошибка: "+_e.getErrorCode()+" "+_e.getSQLState());
        }
        catch (IOException _e)
        {
            throw new RuntimeException(_e);
        }
    }
    public static void formOneGoodView (HttpServletRequest _request, HttpServletResponse _response)
    {
        System.out.println("formGood 1");
        try
        {
            System.out.println("formGood 2");
            DBManager dbManager_l=DBManager.getInstance();
            System.out.println("formGood 3");
            Good good_l = dbManager_l.getGoodByID(Integer.parseInt(_request.getParameter("good_id")));
            System.out.println("formGood 4 "+good_l==null);
            System.out.println("formGood 5");
            PrintWriter writer_l=_response.getWriter();

            writer_l.println(good_l.getName());
            writer_l.println(good_l.getDescription());
            writer_l.println(good_l.getPrice());
            User loginedUser=AppUtils.getLoginedUser(_request.getSession(false));
            if (loginedUser!=null){writer_l.println("<a href=\"#\"><i class=\"fa-sharp fa-solid fa-star\"></i></a>");}
            writer_l.println("<a href=\"#\"><span class=\"fa-sharp fa-solid fa-cart-shopping\" /></a>");

            writer_l.println("</div>");
        }
        catch (SQLException _e)
        {
            System.out.println("Ошибка: "+_e.getErrorCode()+" "+_e.getSQLState());
        }
        catch (IOException _e)
        {
            throw new RuntimeException(_e);
        }
    }
}
