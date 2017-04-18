package controller.filter;

import controller.action.ActionFactory;
import controller.constants.WebResources;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (ActionFactory.getInstance().getAction(request).isPresent()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        request.getRequestDispatcher(WebResources.webResources.get("pageNotFound"))
                .forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
