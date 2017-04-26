package controller.action;

import controller.exception.ControllerException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Actions {

    private static final Logger LOG = LogManager.getLogger(Actions.class);
    public static final int PAGE_SIZE = 5;

    private Actions() {
    }

    /**
     * Define page of requested web-resource and return offset of it elements.
     *
     * @param request
     * @return offset of elements collection if current page
     */
    public static int paginateRequset(HttpServletRequest request) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        HttpSession session = request.getSession();
        session.setAttribute("page", page);
        return (page - 1) * Actions.PAGE_SIZE;
    }

    public static Locale parseLocaleAttribute(Object attribute) {
        if (attribute instanceof Locale) {
            return (Locale) attribute;
        }

        String[] split = ((String) attribute).split("_");
        switch (split.length) {
            case 1:
                return new Locale(split[0]);
            case 2:
                return new Locale(split[0], split[1]);
            case 3:
                return new Locale(split[0], split[1], split[2]);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static void redirectToPage(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOG.log(Level.ERROR, "Can't redirect to " + url, e);
            throw new ControllerException(e);
        }
    }

    public static String getFromBundle(HttpServletRequest request, String baseName, String key) {
        Object locale = request.getSession().getAttribute("language");
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, parseLocaleAttribute(locale));
        return bundle.getString(key);
    }
}
