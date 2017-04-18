package controller.filter;

import controller.action.Action;
import controller.action.ActionFactory;
import controller.constants.WebPaths;
import controller.constants.WebResources;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

        request.getRequestDispatcher(WebResources.webResources.get("error"))
                .forward(servletRequest, servletResponse);
    }

    private void forwardErrorPage(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(WebResources.webResources.get("error"))
                .forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
