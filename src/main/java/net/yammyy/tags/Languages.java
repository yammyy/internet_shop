package net.yammyy.tags;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Language;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class Languages extends SimpleTagSupport {
    public void doTag() throws IOException {
        DBManager dbManager_l = DBManager.getInstance();
        List<Language> languages_l = dbManager_l.getLanguages();
        JspWriter out = getJspContext().getOut();
        for (Language language_l : languages_l) {
            out.println("<option value=\"" + language_l.getID() + "\">" + language_l.getAbbreviation() + "</option>");
        }
    }
}