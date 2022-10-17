package net.yammyy.servletFilter;

import net.yammyy.units.users.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AppUtils {

    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<Integer, String>();
    private static final Map<String, Integer> uri_id_map = new HashMap<String, Integer>();

    // Сохранить информацию пользователя в Session.
    public static void storeLoginedUser(HttpSession _session, User _loginedUser)
    {
        // На JSP можно получить доступ через ${loginedUser}
        _session.setAttribute("loginedUser", _loginedUser);
    }
    // Получить информацию пользователя, сохраненную в Session.
    public static User getLoginedUser(HttpSession _session) {
        User loginedUser_l = (User) _session.getAttribute("loginedUser");
        return loginedUser_l;
    }
    public static int storeRedirectAfterLoginUrl(HttpSession _session, String _requestUri)
    {
        Integer id = uri_id_map.get(_requestUri);
        if (id == null)
        {
            id = REDIRECT_ID++;
            uri_id_map.put(_requestUri, id);
            id_uri_map.put(id, _requestUri);
            return id;
        }
        return id;
    }
    public static String getRedirectAfterLoginUrl(HttpSession _session, int _redirectId) {
        String url = id_uri_map.get(_redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }

}