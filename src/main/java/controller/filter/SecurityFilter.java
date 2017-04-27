package controller.filter;

import controller.constants.WebActions;
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
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String actionSignature = request.getMethod() + path;

        for (String suffix : WebResources.allowedFilenameExtensions) {
            if (path.endsWith(suffix)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        for (String act : WebActions.allActions) {
            if (actionSignature.equals(act)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        request.getRequestDispatcher(WebResources.get("pageNotFound"))
                .forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
