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
    public static void formCategoriesList(HttpServletRequest _request, HttpServletResponse _response, int _category_id)
    {
        String thisName="formCategoriesList";
        int thisLogLine=0;
        thisLogLine++;System.out.println(thisName+" "+thisLogLine);
        try
        {
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            DBManager dbManager_l=DBManager.getInstance();
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            Map<Integer, ChoosableParameterValue> allCategories_l = dbManager_l.getAllCategories();
            thisLogLine++;System.out.println(thisName+" "+thisLogLine+" "+allCategories_l.size());
            PrintWriter writer_l=_response.getWriter();
            writer_l.println("<ul>");
            if (_category_id==0){writer_l.println("<li class=\"selected allCategoriesLink\">");}
            else {writer_l.println("<li class=\"allCategoriesLink\">");}
            writer_l.println("<a href=\""+_request.getContextPath()+HTMLLinks.HOME_PAGE_LINK+"\">" +
                             "Все категории </a>"+
                             "</li>");
            for (Map.Entry<Integer, ChoosableParameterValue> category : allCategories_l.entrySet())
            {
                if (category.getKey()==_category_id){writer_l.println("<li class=\"selected\">");}
                else {writer_l.println("<li>");}
                writer_l.println("<a href=\""+_request.getContextPath()+HTMLLinks.HOME_PAGE_LINK+"?"+
                                 HTMLLinks.PARAMETER_CATEGORY_ID+"="+category.getKey()+"\">" +
                                 category.getValue().getValue()+"</a>"+
                                 "</li>");
            }
            writer_l.println("</ul>");
            thisLogLine++;System.out.println(thisName+" "+thisLogLine+"End");
        }
        catch (SQLException _e)
        {
            System.out.println(thisName+LogMessages.ERROR_EXCEPTION+" "+_e.getErrorCode()+" "+_e.getSQLState());
        }
        catch (IOException _e)
        {
            System.out.println(thisName+LogMessages.ERROR_EXCEPTION+" "+_e.getMessage());
        }
    }
    public static void formGoodsList(HttpServletRequest _request, HttpServletResponse _response,int _category_id)
    {
        String thisName="formGoodsList";
        int thisLogLine=0;
        thisLogLine++;System.out.println(thisName+" "+thisLogLine);
        try
        {
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            DBManager dbManager_l=DBManager.getInstance();
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            List<Good> allGoods_l=null;
            if (_category_id==0){allGoods_l = dbManager_l.getGoods();}
            else {allGoods_l = dbManager_l.getGoods(5,_category_id);}
            thisLogLine++;System.out.println(thisName+" "+thisLogLine+" "+allGoods_l.size());
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
                                 "<a href=\""+_request.getContextPath()+HTMLLinks.ONE_GOOD_LINK+"?good_id="+allGoods_l.get(i).getID()+"\">");
                writer_l.println(allGoods_l.get(i).getName()+"</a>");
                writer_l.println(allGoods_l.get(i).getDescription());
                writer_l.println(allGoods_l.get(i).getPrice());
                User loginedUser=AppUtils.getLoginedUser(_request.getSession(false));
                if (loginedUser!=null){writer_l.println("<a href=\"#\"><i class=\"fa-sharp fa-solid fa-star\"></i></a>");}
                writer_l.println("<a href=\""+_request.getContextPath()+"\"><span class=\"fa-sharp fa-solid fa-cart-shopping\" /></a>");
                writer_l.println("</div>");
            }
            writer_l.println("</div>");//row
        }
        catch (SQLException _e)
        {
            System.out.println(thisName+LogMessages.ERROR_EXCEPTION+" "+_e.getErrorCode()+" "+_e.getSQLState());
        }
        catch (IOException _e)
        {
            System.out.println(thisName+LogMessages.ERROR_EXCEPTION+" "+_e.getMessage());
        }
    }
    public static void formOneGoodView (HttpServletRequest _request, HttpServletResponse _response)
    {
        String thisName="formOneGoodView";
        int thisLogLine=0;
        thisLogLine++;System.out.println(thisName+" "+thisLogLine);
        try
        {
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            DBManager dbManager_l=DBManager.getInstance();
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            Good good_l = dbManager_l.getGoodByID(Integer.parseInt(_request.getParameter("good_id")));
            thisLogLine++;System.out.println(thisName+" "+thisLogLine+" Good with id"+_request.getParameter("good_id")+" found "+(good_l!=null));
            PrintWriter writer_l=_response.getWriter();
            writer_l.println(good_l.getName());
            writer_l.println(good_l.getDescription());
            writer_l.println(good_l.getPrice());
            User loginedUser=AppUtils.getLoginedUser(_request.getSession(false));
            if (loginedUser!=null){writer_l.println("<a href=\"#\"><i class=\"fa-sharp fa-solid fa-star\"></i></a>");}
            writer_l.println("<a href=\"#\"><i class=\"fa-sharp fa-solid fa-cart-shopping\"></i></a>");
            writer_l.println("</div>");
        }
        catch (SQLException _e)
        {
            System.out.println(thisName+LogMessages.ERROR_EXCEPTION+" "+_e.getErrorCode()+" "+_e.getSQLState());
        }
        catch (IOException _e)
        {
            System.out.println(thisName+LogMessages.ERROR_EXCEPTION+" "+_e.getMessage());
        }
    }
}
