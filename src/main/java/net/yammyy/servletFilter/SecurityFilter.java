package net.yammyy.servletFilter;

import net.yammyy.units.users.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException
    {
        System.out.println("doFilter 1");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        System.out.println("doFilter 2 "+request.getContextPath());
        String servletPath = request.getServletPath();
        System.out.println("doFilter 3 "+servletPath);
        // Информация пользователя сохранена в Session (После успешного входа в систему).
        User loginedUser = AppUtils.getLoginedUser(request.getSession());
        System.out.println("doFilter 4 "+(loginedUser==null));
        if (servletPath.equals("/loginView"))
        {
            System.out.println("doFilter 4.1");
            chain.doFilter(request, response);
            return;
        }
        // Страницы требующие входа в систему.
        if (SecurityUtils.isSecurityPage(request))
        {

            System.out.println("doFilter 5");
            // Если пользователь еще не вошел в систему,
            // Redirect (перенаправить) к странице логина.
            if (loginedUser == null)
            {
                System.out.println("doFilter 6");
                String requestUri = request.getRequestURI();
                System.out.println("doFilter 7 "+requestUri);
                // Сохранить текущую страницу для перенаправления (redirect) после успешного входа в систему.
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
                System.out.println("doFilter 7.1 "+request.getContextPath() + "/loginView?redirectId=" + redirectId);
                response.sendRedirect(request.getContextPath() + "/loginView?redirectId=" + redirectId);
                return;
            }
            // Проверить пользователь имеет действительную роль или нет?
            boolean hasPermission = SecurityUtils.hasPermission(loginedUser,request);
            System.out.println("doFilter 8 "+hasPermission);
            if (!hasPermission)
            {
                System.out.println("doFilter 9");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");
                System.out.println("doFilter 10");
                dispatcher.forward(request, response);
                return;
            }
        }
        System.out.println("doFilter 11");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }
}
