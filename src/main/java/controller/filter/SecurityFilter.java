package controller.filter;

import controller.constants.WebActions;
import controller.constants.WebPaths;
import controller.constants.WebResources;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring(request.getContextPath().length());

        boolean isAllowedResource = isPathToAllowedResource(path);
        if (isAllowedResource) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String actionSignature = request.getMethod() + path;
        if (WebActions.publicActions.contains(actionSignature)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        boolean isRoleAccessibleAction = isRoleAccessibleAction(actionSignature);
        boolean isLogined = session.getAttribute("logined") != null;
        if (isRoleAccessibleAction) {
            if (!isLogined) {
                forwardToLogin(servletRequest, servletResponse);
                return;
            }

            if (hasRoleAccess(actionSignature, session)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        forwardToPageNotFound(servletRequest, servletResponse);
    }

    private boolean hasRoleAccess(String actionSignature, HttpSession session) {
        return WebActions.roleAccessibleActions.get(session.getAttribute("role")).contains(actionSignature);
    }

    private void forwardToLogin(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(WebResources.webResources.get("login"))
                .forward(request, response);
    }

    private void forwardToPageNotFound(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(WebResources.webResources.get("pageNotFound"))
                .forward(request, response);
    }

    private boolean isPathToAllowedResource(String path) {
        return WebResources.allowedFilenameExtensions
                .stream()
                .anyMatch(path::endsWith);
    }

    private boolean isRoleAccessibleAction(String actionSignature) {
        return WebActions.roleAccessibleActions.values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> s.equals(actionSignature));
    }

    @Override
    public void destroy() {

    }
}
