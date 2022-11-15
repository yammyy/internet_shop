package net.yammyy.tags;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.Currency;
import net.yammyy.units.goods.Language;
import net.yammyy.units.users.Type;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Roles extends SimpleTagSupport {
    public void doTag() throws IOException {
        DBManager dbManager_l = DBManager.getInstance();
        List<Type> roles_l = new ArrayList<>(dbManager_l.getRoles().values());
        JspWriter out = getJspContext().getOut();
        for (Type role : roles_l) {
            out.println("<option value=\"" + role.getID() + "\">" + role.getName() + "</option>");
        }
    }
}