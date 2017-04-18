package controller.action.authorization;

import controller.action.Action;
import controller.constants.WebPaths;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutAction implements Action {

    private static final Logger LOG = LogManager.getLogger(LogOutAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        try {
            response.sendRedirect(WebPaths.webPaths.get("login"));
        } catch (IOException e) {
            LOG.log(Level.ERROR, "Can't redirect to home page");
            throw new RuntimeException(e);
        }

        return null;
    }
}
