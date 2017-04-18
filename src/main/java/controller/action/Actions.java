package controller.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Actions {

    private static final Logger LOG = LogManager.getLogger(Actions.class);

    private Actions() {
    }

    public static void redirectToPage(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOG.log(Level.ERROR, "Can't redirect to " + url, e);
            throw new RuntimeException(e);
        }
    }
}
