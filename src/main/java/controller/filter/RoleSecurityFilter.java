package controller.filter;

import controller.constants.WebPaths;
import controller.constants.WebResources;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RoleSecurityFilter implements Filter {

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

        String roleAttribute = (String) request.getSession().getAttribute("role");
        if (roleAttribute == null
                || WebPaths.roleAccessiblePaths.get(roleAttribute).contains(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        request.getRequestDispatcher(WebResources.webResources.get("error"))
                .forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
