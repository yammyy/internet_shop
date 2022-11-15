package net.yammyy.utils;

import net.yammyy.db.DBManager;
import net.yammyy.units.order.Orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class FormingOrders
{
    public static void formOrdersList(HttpServletRequest _request, HttpServletResponse _response)
    {
        String thisName="formGoodsList";
        int thisLogLine=0;
        thisLogLine++;System.out.println(thisName+" "+thisLogLine);
        try
        {
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            DBManager dbManager_l=DBManager.getInstance();
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            List<Orders> allOrders_l=null;
            allOrders_l = dbManager_l.getOrders();
            thisLogLine++;System.out.println(thisName+" "+thisLogLine+" "+allOrders_l.size());
            PrintWriter writer_l=_response.getWriter();
            writer_l.println("<div class=\"container\">");
            for (int i=0;i<allOrders_l.size();i++)
            {
                writer_l.println("<div class=\"row\">"+
                                 "<div class=\"col-12\">"+
                                 "<div class=\"container\">"+
                                 "<div class=\"row\">"+
                                 "<div class=\"col-4\">"+
                                 "<a href=\""+_request.getContextPath()+HTMLLinks.ONE_ORDER_LINK+"?order_id="+allOrders_l.get(i).getID()+"\">"+
                                 "</div>"+
                                 "<div class=\"col-8\">"+
                                 "доставка почтой"+
                                 "</div>"+
                                 "</div>"+
                                 "<div class=\"row\">"+
                                 "<div class=\"col-4\">"+
                                 "#"+allOrders_l.get(i).getLastStatus().getDateChange()+
                                 "</div>"+
                                 "<div class=\"col-8\">"+
                                 allOrders_l.get(i).getLastStatus().getName()+
                                 "</div>"+//col
                                 "</div>"+//row
                                 "</div>"+//container
                                 "</div>"+//col
                                 "</div>");//row
            }
            writer_l.println("</div>");//container
    } catch (
    IOException _e)
    {
        System.out.println(thisName+LogMessages.ERROR_EXCEPTION+" "+_e.getMessage());
    }
    }
}
