package net.yammyy.utils;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.ChoosableParameterValue;
import net.yammyy.units.goods.Good;
import net.yammyy.units.users.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class FormingGoods {
    public static void formCategoriesList(HttpServletRequest _request, HttpServletResponse _response,
                                          int _category_id) {
        String thisName = "formCategoriesList";
        int thisLogLine = 0;
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        try {
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine);
            DBManager dbManager_l = DBManager.getInstance();
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine);
            List<ChoosableParameterValue> allCategories_l = dbManager_l.getAllCategories();
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine + " " + allCategories_l.size());
            PrintWriter writer_l = _response.getWriter();
            writer_l.println("<ul>");
            if (_category_id == 0) {
                writer_l.println("<li class=\"selected allCategoriesLink\">");
            } else {
                writer_l.println("<li class=\"allCategoriesLink\">");
            }
            writer_l.println("<a href=\"" + _request.getContextPath() + HTMLLinks.HOME_PAGE_LINK + "\">" +
                    "Все категории </a>" +
                    "</li>");
            for (ChoosableParameterValue category : allCategories_l) {
                if (category.getID() == _category_id) {
                    writer_l.println("<li class=\"selected\">");
                } else {
                    writer_l.println("<li>");
                }
                writer_l.println("<a href=\"" + _request.getContextPath() + HTMLLinks.HOME_PAGE_LINK + "?" +
                        HTMLLinks.PARAMETER_CATEGORY_ID + "=" + category.getID() + "\">" +
                        category.getValue() + "</a>" +
                        "</li>");
            }
            writer_l.println("</ul>");
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine + "End");
        } catch (IOException _e) {
            System.out.println(thisName + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }

    public static void formGoodsList(HttpServletRequest _request, HttpServletResponse _response, int _category_id) {
        String thisName = "formGoodsList";
        int thisLogLine = 0;
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        try {
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine);
            DBManager dbManager_l = DBManager.getInstance();
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine);
            List<Good> allGoods_l = null;
            if (_category_id == 0) {
                allGoods_l = dbManager_l.getGoods();
            } else {
                allGoods_l = dbManager_l.getGoods(5, _category_id);
            }
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine + " " + allGoods_l.size());
            PrintWriter writer_l = _response.getWriter();
            writer_l.println("<div class=\"row\">" +
                    "<div class=\"col-1\"></div>" +
                    "<div class=\"col-6\"></div>" +
                    "</div>" +
                    "<div class=\"row\">");
            for (int i = 0; i < allGoods_l.size(); i++) {
                writer_l.println("<div class=\"col col-xl-2 col-lg-3 col-md-4 col-sm-6\">");
                writer_l.println("<form method=\"POST\" action=\"" + _request.getContextPath() + "/changelist\">" +
                        "<input type=\"hidden\" name=\"redirectURL\" value=\"" + _request.getRequestURI() + "\">" +
                        "<input type=\"hidden\" name=\"" + HTMLLinks.PARAMETER_CATEGORY_ID + "\" value=\"" +
                        (_request.getParameter(HTMLLinks.PARAMETER_CATEGORY_ID) == null ? 0 :
                                _request.getParameter(HTMLLinks.PARAMETER_CATEGORY_ID)) +
                        "\">" +
                        "<input type=\"hidden\" name=\"" + HTMLLinks.PARAMETER_GOOD_ID + "\" value=\"" + allGoods_l.get(i).getID() + "\">" +
                        "<img src=\"\" />" +
                        "<a href=\"" + _request.getContextPath() + HTMLLinks.ONE_GOOD_LINK + "?good_id=" + allGoods_l.get(i).getID() +"&"+
                        HTMLLinks.PARAMETER_CATEGORY_ID+"="+_request.getParameter(HTMLLinks.PARAMETER_CATEGORY_ID)+ "\">" +
                        allGoods_l.get(i).getName() +
                        "</a>" +
                        allGoods_l.get(i).getDescription() +
                        allGoods_l.get(i).getPrice() +

                        "<input type=\"number\" id=\"quantity\" name=\"quantity\" min=\"1\">"
                );

                User loginedUser = AppUtils.getLoginedUser(_request.getSession(false));
                if (loginedUser != null) {
                    //if (allGoods_l.get(i).)
                    writer_l.println("<button name=\"addToFavoriteButton\" class=\"good_for_cart\" type=\"submit\" value=" +
                            " \"fav\" ><i class=\"fa-sharp fa-solid fa-star\"></i></button>");
                }
                writer_l.println("<button name=\"addToCartButton\" class=\"good_for_cart\" type=\"submit\" value= " +
                        "\"add\" ><i class=\"fa-sharp fa-solid fa-cart-plus\"></i></button>" +
                        "<button name=\"removeFromCartButton\" class=\"good_for_cart\" type=\"submit\" value= " +
                        "\"remove\" >-</button>" +
                        "</form>" +
                        "</div>");
            }
            writer_l.println("</div>");//row
        } catch (IOException _e) {
            System.out.println(thisName + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }

    public static void formOneGoodView(HttpServletRequest _request, HttpServletResponse _response) {
        String thisName = "formOneGoodView";
        int thisLogLine = 0;
        thisLogLine++;
        System.out.println(thisName + " " + thisLogLine);
        try {
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine);
            DBManager dbManager_l = DBManager.getInstance();
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine);
            Good good_l = dbManager_l.getGoodByID(Integer.parseInt(_request.getParameter("good_id")));
            thisLogLine++;
            System.out.println(thisName + " " + thisLogLine + " Good with id" + _request.getParameter("good_id") + " " +
                    "found " + (good_l != null));
            PrintWriter writer_l = _response.getWriter();
            writer_l.println(good_l.getName());
            writer_l.println(good_l.getDescription());
            writer_l.println(good_l.getPrice());
            User loginedUser = AppUtils.getLoginedUser(_request.getSession(false));
            if (loginedUser != null) {
                writer_l.println("<a href=\"#\"><i class=\"fa-sharp fa-solid fa-star\"></i></a>");
            }
            writer_l.println("<a href=\"#\"><i class=\"fa-sharp fa-solid fa-cart-shopping\"></i></a>");
            writer_l.println("</div>");
        } catch (IOException _e) {
            System.out.println(thisName + LogMessages.ERROR_EXCEPTION + " " + _e.getMessage());
        }
    }
}
