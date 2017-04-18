package controller.filter;

import controller.constants.WebPaths;
import controller.constants.WebResources;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (WebPaths.publicPaths.contains(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession();
        String logined = (String) session.getAttribute("logined");
        if ("yes".equals(logined)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        servletRequest.getRequestDispatcher(WebPaths.webPaths.get("login"))
                .forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
