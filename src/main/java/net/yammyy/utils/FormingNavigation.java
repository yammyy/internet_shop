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
            writer_l.println("<div id=\"navProfile\" class=\"nav-profile closed-nav-profile navbar-dark\">"+
                             "<div class=\"container nav-profile-container\">"+
                             "<div class=\"row row-enter\">"+
                             "<div class=\"col col-10 d-flex align-items-center justify-content-start\">");
            User loginedUser=AppUtils.getLoginedUser(_request.getSession(false));
            if (loginedUser==null)
            {
                writer_l.println("<a href=\""+_request.getContextPath()+HTMLLinks.LOGIN_LINK+"\">Войти в профиль <i class=\"fa-solid fa-sign-in\"></i></a>");
            }
            else
            {
                writer_l.println("<a href=\""+_request.getContextPath()+HTMLLinks.PROFILE_LINK+"\">" +
                                 "<img class=\"avatar\" src=\""+loginedUser.getPhoto()+"\">" +
                                 "</a>");
                writer_l.println(loginedUser.getFamilyAndName());
            }
            writer_l.println("</div>"+//col
                             "<div class=\"col col-2 d-flex align-items-center justify-content-end\">"+
                             "<a href=\"javascript:void(0)\" class=\"close-profile-btn\" onclick=\"closeNavProfile()\"><span class=\"fa fa-window-close\" /></a>"+
                             "</div>" +//col
                             "</div>" +//row
                             "<div class=\"row\">"+
                             "<div class=\"col\">" +
                             "<ul>" +
                             "<li><a href=\""+_request.getContextPath()+HTMLLinks.HOME_PAGE_LINK+"\">Каталог</a></li>");
            if (loginedUser!=null){writer_l.println("<li><a href=\""+_request.getContextPath()+HTMLLinks.USER_ORDERS+"\">Мои заказы</a></li>");}
            writer_l.println("<li><a href=\""+_request.getContextPath()+HTMLLinks.USER_CART+"\">Корзина</a></li>");
            if (loginedUser!=null){writer_l.println("<li><a href=\""+_request.getContextPath()+HTMLLinks.USER_FAVORITES+"\">Список желаний</a></li>");}
            writer_l.println("</ul>" +
                             "</div>"+//col
                             "</div>" +//row
                             "<div class=\"row\">" +
                             "<div class=\"col\">");
            writer_l.println("<ul>" +
                             "<li>");
            writer_l.println("<a href=\"#\">");
            if (loginedUser==null){writer_l.println("RU");}
            else {writer_l.println(loginedUser.getStandardLanguage().getAbbr());}
            writer_l.println(" Языковые настройки</a>");
            writer_l.println("</li>" +
                             "<li>");
            writer_l.println("<a href=\"#\">");
            if (loginedUser==null){writer_l.println("UAH");}
            else {writer_l.println(loginedUser.getStandardCurrency().getAbbr());}
            writer_l.println(" Настройки валюты</a>");
            writer_l.println("</li>" +
                             "</ul>" +
                             "</div>");//col
            writer_l.println("</div>" +//row
                             "<div class=\"row row-exit\">");
            writer_l.println("<div class=\"col\">");
            if (loginedUser!=null)
            {
                writer_l.println("<a href=\""+_request.getContextPath()+HTMLLinks.LOGOUT_LINK+"\">" +
                                 "<span class=\"fa-sharp fa-solid fa-sign-out\" /> Выйти из профиля" +
                                 "</a>");
            }
            writer_l.println("</div>" +//col
                             "</div>" +//row
                             "</div>" +//container
                             "</div>");//.navProfile
            writer_l.println("<nav class=\"navbar navbar-dark fixed-top\">" +
                             "<div class=\"container\">"+
                             "<div class=\"row\">" +
                             "<div class=\"col-2\">" +
                             "<button class=\"navbar-toggler\" onclick=\"openNavProfile()\"><span class=\"navbar-toggler-icon\" /></button>" +
                             "</div>"+//col
                             "<div class=\"col middle-col col-9 d-flex align-items-center justify-content-end\">" +
                             "<ul>" +
                             "<li>");
            DBManager dbManager_l=DBManager.getInstance();
            List<Language> languages_l=dbManager_l.getLanguages();
            System.out.println("formNavBar Language Количество языков "+languages_l.size());
            _request.setAttribute("languages",languages_l);
            RequestDispatcher rdLng=_request.getRequestDispatcher("forNavbar/languages.jsp");
            rdLng.include(_request, _response);
            writer_l.println("</li>" +
                             "<li>");
            List<Currency> currencies_l=dbManager_l.getCurrencies();
            System.out.println("formNavBar Language Количество валют "+currencies_l.size());
            _request.setAttribute("currencies",currencies_l);
            RequestDispatcher rdCur=_request.getRequestDispatcher("forNavbar/currencies.jsp");
            rdCur.include(_request, _response);
            writer_l.println("</li>");
            if (loginedUser!=null){writer_l.println("<li><a href=\""+_request.getContextPath()+HTMLLinks.USER_FAVORITES+"\">" +
                                                    "<span class=\"fa-sharp fa-solid fa-star\" /></a></li>");}
            writer_l.println("<li>");
            if (loginedUser!=null)
            {
                writer_l.println(
                        "<div class=\"dropdown\">"+
                        "<a href=\""+_request.getContextPath()+HTMLLinks.PROFILE_LINK+"\"><img class=\"avatar\" src=\""+loginedUser.getPhoto()+"\">"+"</a>"+
                        "<ul>"+
                        "<div class=\"dropdown-content\">"+
                        "<li>" +
                        "<a href=\""+_request.getContextPath()+HTMLLinks.PROFILE_LINK+"\"><span class=\"fa-sharp fa-solid fa-user\" />Профиль</a>"+
                        "</li>"+
                        "<li>" +
                        "<a href=\""+_request.getContextPath()+HTMLLinks.LOGOUT_LINK+"\"><span class=\"fa-sharp fa-solid fa-sign-out\" />Выйти</a>"+
                        "</li>"+
                        "</div>"+
                        "</ul>"+
                        "</div>");
            }
            else {writer_l.println("<a href=\""+_request.getContextPath()+HTMLLinks.LOGIN_LINK+"\"><i class=\"fa-solid fa-right-to-bracket\"></i></a>");}
            writer_l.println("</li>");
            writer_l.println("</ul>" +
                             "</div>" +//col
                             "<div class=\"col-1 d-flex align-items-center \">" +
                             "<a href=\""+_request.getContextPath()+HTMLLinks.USER_CART+"\"><span class=\"fa-sharp fa-solid fa-cart-shopping\" /></a>" +
                             "</div>" +//col
                             "</div>" +//row
                             "</div>" +//container
                             "</nav>");

        }
        catch (IOException|ServletException|SQLException _e)
        {
            System.out.println("formNavBar "+LogMessages.ERROR_EXCEPTION+" "+_e.getMessage());
        }
    }
}
