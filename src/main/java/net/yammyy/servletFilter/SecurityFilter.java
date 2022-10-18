package net.yammyy.servletFilter;

import net.yammyy.units.users.User;
import net.yammyy.utils.HTMLLinks;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter
{
    private static final String thisName="SecurityFilter";
    @Override
    public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain _filterChain)
            throws IOException, ServletException
    {
        int thisLogLine=0;
        thisLogLine++;System.out.println(thisName+" "+thisLogLine);
        HttpServletRequest request_l = (HttpServletRequest) _request;
        HttpServletResponse response_l = (HttpServletResponse) _response;
        thisLogLine++;System.out.println(thisName+" "+thisLogLine+" "+request_l.getContextPath()+" / "+request_l.getServletPath());
        String servletPath = request_l.getServletPath();
        if (servletPath.contains("css/")||servletPath.contains("js/")||servletPath.contains("img/"))
        {
            _filterChain.doFilter(request_l, response_l);
            return;
        }
        if (servletPath.equals(HTMLLinks.LOGIN_LINK))
        {
            _filterChain.doFilter(request_l, response_l);
            return;
        }
        thisLogLine++;System.out.println(thisName+" "+thisLogLine+" Информация пользователя сохранена в Session (После успешного входа в систему).");
        User loginedUser_l = AppUtils.getLoginedUser(request_l.getSession());
        thisLogLine++;System.out.println(thisName+" "+thisLogLine+" User successfully logined:"+(loginedUser_l!=null));
        thisLogLine++;System.out.println(thisName+" "+thisLogLine+" Страницы требующие входа в систему.");
        if (SecurityUtils.isSecurityPage(request_l))
        {
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            // Если пользователь еще не вошел в систему,
            // Redirect (перенаправить) к странице логина.
            if (loginedUser_l == null)
            {
                thisLogLine++;System.out.println(thisName+" "+thisLogLine);
                String requestUri = request_l.getRequestURI();
                thisLogLine++;System.out.println(thisName+" "+thisLogLine+" "+requestUri);
                // Сохранить текущую страницу для перенаправления (redirect) после успешного входа в систему.
                int redirectID = AppUtils.storeRedirectAfterLoginUrl(request_l.getSession(), requestUri);
                thisLogLine++;System.out.println(thisName+" "+thisLogLine+request_l.getContextPath() +
                                                 HTMLLinks.LOGIN_LINK + "?"+HTMLLinks.REDIRECT_ID+"=" + redirectID);
                response_l.sendRedirect(request_l.getContextPath() + HTMLLinks.LOGIN_LINK + "?"+HTMLLinks.REDIRECT_ID+"=" + redirectID);
                return;
            }
            // Проверить имеет ли пользователь доступ?
            boolean hasPermission = SecurityUtils.hasPermission(loginedUser_l,request_l);
            thisLogLine++;System.out.println(thisName+" "+thisLogLine);
            if (!hasPermission)
            {
                thisLogLine++;System.out.println(thisName+" "+thisLogLine);
                RequestDispatcher dispatcher = request_l.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");
                thisLogLine++;System.out.println(thisName+" "+thisLogLine);
                dispatcher.forward(request_l, response_l);
                return;
            }
        }
        thisLogLine++;System.out.println(thisName+" "+thisLogLine+" Не требуется вход в систему.");
        _filterChain.doFilter(request_l, response_l);
    }
}
