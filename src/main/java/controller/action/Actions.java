package controller.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Actions {

    private static final Logger LOG = LogManager.getLogger(Actions.class);

    private Actions() {
    }

    public static Locale parseLocaleAttribute(Object attribute) {
        if (attribute instanceof Locale) {
            return (Locale) attribute;
        }

        String[] split = ((String) attribute).split("_");
        return new Locale(split[0], split[1]);
    }

    public static void redirectToPage(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOG.log(Level.ERROR, "Can't redirect to " + url, e);
            throw new RuntimeException(e);
        }
    }

    public static String getFromBundle(HttpServletRequest request, String baseName, String key) {
        Object locale = request.getSession().getAttribute("language");
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, parseLocaleAttribute(locale));
        return bundle.getString(key);
    }
}
