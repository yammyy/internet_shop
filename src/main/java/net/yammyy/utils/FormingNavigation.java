package net.yammyy.utils;

import net.yammyy.db.DBManager;
import net.yammyy.servletFilter.AppUtils;
import net.yammyy.units.goods.Currency;
import net.yammyy.units.goods.Language;
import net.yammyy.units.users.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class FormingNavigation
{
    public static void formNavBar(HttpServletRequest _request, HttpServletResponse _response)
    {
        try
        {
            PrintWriter writer_l = _response.getWriter();
            writer_l.println("<div id=\"navProfile\" class=\"nav-profile closed-nav-profile navbar-dark\">");
            writer_l.println("<div class=\"container nav-profile-container\">");
            writer_l.println("<div class=\"row row-enter\">");
            writer_l.println("<div class=\"col col-10 d-flex align-items-center justify-content-start\">");
            User loginedUser=AppUtils.getLoginedUser(_request.getSession(false));
            if (loginedUser==null)
            {
                writer_l.println("<a href=\""+_request.getContextPath()+"/loginView"+"\">Войти в профиль <i class=\"fa-solid fa-sign-in\"></i></a>");
            }
            else
            {
                formAvatar(_request,_response);
                writer_l.println(loginedUser.getFIO());
            }
            writer_l.println("</div>");
            writer_l.println("<div class=\"col col-2 d-flex align-items-center justify-content-end\">");
            writer_l.println("<a href=\"javascript:void(0)\" class=\"close-profile-btn\" onclick=\"closeNavProfile()\"><span class=\"fa fa-window-close\" /></a>");
            writer_l.println("</div></div><div class=\"row\">");
            writer_l.println("<div class=\"col\"><ul><li>");
            writer_l.println("<a href=\"#\">Каталог</a></li>");
            if (loginedUser!=null){writer_l.println("<li><a href=\"#\">Мои заказы</a></li>");}
            writer_l.println("<li><a href=\"#\">Корзина</a></li>");
            if (loginedUser!=null){writer_l.println("<li><a href=\"#\">Список желаний</a></li>");}
            writer_l.println("</ul></div>");
            writer_l.println("</div><div class=\"row\"><div class=\"col\">");
            writer_l.println("<ul>\n"+"                    <li>");
            writer_l.println("<a href=\"#\">"+
                             "RU"+
                             "</a>");
            writer_l.println("<a href=\"#\">Языковые настройки</a>");
            writer_l.println("</li><li>");
            writer_l.println("<a href=\"#\">" +
                             "UAH" +
                             "</a>");
            writer_l.println("<a href=\"#\">Настройки валюты</a>");
            writer_l.println("</li></ul></div>");
            writer_l.println("</div><div class=\"row row-exit\">");
            writer_l.println("<div class=\"col\">");
            if (loginedUser!=null)
            {
                writer_l.println("<a href=\""+_request.getContextPath()+"/logout"+"\">" +
                                 "<span class=\"fa-sharp fa-solid fa-sign-out\" /> Выйти из профиля" +
                                 "</a>");
            }
            writer_l.println("</div></div></div></div>");
            writer_l.println("<nav class=\"navbar navbar-dark fixed-top\"><div class=\"container\">");
            writer_l.println("<div class=\"row\"><div class=\"col-2\">");
            writer_l.println("<button class=\"navbar-toggler\" onclick=\"openNavProfile()\">");
            writer_l.println("<span class=\"navbar-toggler-icon\" />");
            writer_l.println("</button></div>");
            writer_l.println("<div class=\"col middle-col col-9 d-flex align-items-center justify-content-end\">");
            writer_l.println("<ul><li>");
            System.out.println("formNavBar Language 1.1");
            List<Language> languages_l=null;
            System.out.println("formNavBar Language 1.2");
            DBManager dbManager_l=null;
            dbManager_l=DBManager.getInstance();
            System.out.println("formNavBar Language 1.3");
            languages_l=dbManager_l.getLanguages();
            System.out.println("formNavBar Language 1.4 "+languages_l.size());
            _request.setAttribute("languages",languages_l);
            System.out.println("formNavBar Language 1.5");
            RequestDispatcher rdLng=_request.getRequestDispatcher("forNavbar/languages.jsp");
            System.out.println("formNavBar Language 1.6");
            rdLng.include(_request, _response);
            System.out.println("formNavBar Language 1.7");
            writer_l.println("</li>");
            writer_l.println("<li>");
            System.out.println("formNavBar Currency 1.1");
            List<Currency> currencies_l=null;
            System.out.println("formNavBar Currency 1.2");
            currencies_l=dbManager_l.getCurrencies();
            System.out.println("formNavBar Currency 1.3 "+currencies_l.size());
            _request.setAttribute("currencies",currencies_l);
            System.out.println("formNavBar Currency 1.4");
            RequestDispatcher rdCur=_request.getRequestDispatcher("forNavbar/currencies.jsp");
            System.out.println("formNavBar Currency 1.5");
            rdCur.include(_request, _response);
            System.out.println("formNavBar Currency 1.6");
            writer_l.println("</li>");
            if (loginedUser!=null){writer_l.println("<li><a href=\"#\"><span class=\"fa-sharp fa-solid fa-star\" /></a></li>");}
            writer_l.println("<li>");
            formAvatar(_request,_response);
            writer_l.println("</li>");
            writer_l.println("</ul></div>");
            writer_l.println("<div class=\"col-1 d-flex align-items-center \">");
            writer_l.println("<a href=\"#\"><span class=\"fa-sharp fa-solid fa-cart-shopping\" /></a>");
            writer_l.println("</div></div></div></nav>");
            System.out.println("formNavBar End");

        }
        catch (IOException|ServletException|SQLException _e)
        {
            throw new RuntimeException(_e);
        }
    }
    public static void formAvatar(HttpServletRequest _request,HttpServletResponse _response)
            throws ServletException, IOException
    {
        System.out.println("formAvatar");
        User loginedUser=AppUtils.getLoginedUser(_request.getSession(false));
        System.out.println("formAvatar "+(loginedUser==null));
        if (loginedUser==null)
        {
            PrintWriter writer_l = _response.getWriter();
            writer_l.println("<a href=\"#\"><i class=\"fa-solid fa-right-to-bracket\"></i></a>");
        }
        else
        {
            _request.setAttribute("photo", loginedUser.getPhoto());
            RequestDispatcher rd = _request.getRequestDispatcher("forNavbar/avatar.jsp");
            rd.include(_request, _response);
        }
    }
}
