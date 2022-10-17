package net.yammyy.servletFilter;

import net.yammyy.db.DBManager;
import net.yammyy.units.goods.ChoosableParameterValue;
import net.yammyy.units.users.Type;
import net.yammyy.units.users.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class SecurityUtils
{
    // Проверить требует ли данный 'request' входа в систему или нет.
    public static boolean isSecurityPage(HttpServletRequest _request)
    {
        System.out.println("isSecurityPage 1 ");
        String urlPattern = getUrlPattern(_request);
        System.out.println("isSecurityPage 2 "+urlPattern);
        DBManager dbManager_l=null;
        try
        {
            dbManager_l=DBManager.getInstance();
            System.out.println("isSecurityPage 3");
            Map<Integer, Type> roles = dbManager_l.getRoles();
            System.out.println("isSecurityPage 4 "+roles.size());
            Iterator it = roles.entrySet().iterator();
            System.out.println("isSecurityPage 5");
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println("isSecurityPage 6 "+((Type)pair.getValue()).getName());
                if (((Type)pair.getValue()).hasURL(urlPattern)){return true;}
            }
        }
        catch (IOException|SQLException _e)
        {
            System.out.println(_e.getMessage());
        }
        return false;
    }
    public static boolean hasPermission (User _user, HttpServletRequest _request)
    {
        String urlPattern = getUrlPattern(_request);
        if (_user.getRole().hasURL(urlPattern)){return true;}
        return false;
    }
    // servletPath:
    // ==> /spath
    // ==> /spath/*
    // ==> *.ext
    // ==> /
    public static String getUrlPattern(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        String urlPattern = null;
        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
            return urlPattern;
        }
        urlPattern = servletPath;

        boolean has = hasUrlPattern(servletContext, urlPattern);
        if (has) {
            return urlPattern;
        }
        int i = servletPath.lastIndexOf('.');
        if (i != -1) {
            String ext = servletPath.substring(i + 1);
            urlPattern = "*." + ext;
            has = hasUrlPattern(servletContext, urlPattern);

            if (has) {
                return urlPattern;
            }
        }
        return "/";
    }
    private static boolean hasUrlPattern(ServletContext servletContext, String urlPattern) {

        Map<String, ? extends ServletRegistration> map = servletContext.getServletRegistrations();

        for (String servletName : map.keySet()) {
            ServletRegistration sr = map.get(servletName);

            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }

        }
        return false;
    }
}