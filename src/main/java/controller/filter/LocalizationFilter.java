package controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocalizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        String languageParameter = httpServletRequest.getParameter("language");
        if (languageParameter != null) {
            session.setAttribute("language", languageParameter);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (session.getAttribute("language") == null) {
            session.setAttribute("language", httpServletRequest.getLocale().toString());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
