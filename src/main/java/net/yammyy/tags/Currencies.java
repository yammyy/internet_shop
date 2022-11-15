package net.yammyy.tags;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Currency;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class Currencies extends SimpleTagSupport {
    public void doTag() throws IOException {
        DBManager dbManager_l = DBManager.getInstance();
        List<Currency> currencies_l = dbManager_l.getCurrencies();
        JspWriter out = getJspContext().getOut();
        for (Currency currency_l : currencies_l) {
            out.println("<option value=\"" + currency_l.getID() + "\">" + currency_l.getAbbreviation() + "</option>");
        }
    }
}