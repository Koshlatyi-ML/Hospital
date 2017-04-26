package controller;

import controller.action.ActionFactory;
import controller.exception.ControllerException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        ActionFactory.getInstance().getAction(request).ifPresent(action -> {
            String url = action.execute(request, response);
            forwardRequest(url, request, response);
        });
    }

    private void forwardRequest(String url, HttpServletRequest request,
                                HttpServletResponse response) {
        if (url == null) {
            return;
        }

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.log(Level.ERROR, "Can't forward request", e);
            throw new ControllerException(e);
        }
    }
}
