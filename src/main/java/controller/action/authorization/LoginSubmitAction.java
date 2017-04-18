package controller.action.authorization;

import controller.action.Action;
import controller.constants.WebMessages;
import controller.constants.WebPaths;
import controller.validation.Validations;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginSubmitAction implements Action {

    private static final Logger LOG = LogManager.getLogger(LoginSubmitAction.class);

    private static class LoginAttributes {
        private String role;
        private Object user;

        LoginAttributes(String role, Object user) {
            this.role = role;
            this.user = user;
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Authorizations.removeLoginErrorAttributes(session);

        Optional<LoginAttributes> loginAttributesOptional = fetchLoginAttributes(request, response);
        loginAttributesOptional.ifPresent(attributes -> {
            session.setAttribute("logined", "yes");
            session.setAttribute("role", attributes.role);
            session.setAttribute("user", attributes.user);
            redirectToPage(response, getHomeURL(attributes.role));
        });

        return null;
    }

    private String getHomeURL(String role) {
        switch (role) {
            case "Doctor":
                return WebPaths.webPaths.get("doctor.main");
            case "Medic":
                return WebPaths.webPaths.get("medic.main");
            case "Patient":
                return WebPaths.webPaths.get("patient.main");
            default:
                throw new IllegalStateException();
        }
    }

    private Optional<LoginAttributes> fetchLoginAttributes(HttpServletRequest request,
                                                           HttpServletResponse response) {

        String role = request.getParameter("role");
        Optional<LoginService> loginService = getLoginService(role);
        if (!loginService.isPresent()) {
            redirectWrongRole(request, response);
            return Optional.empty();
        }

        String login = request.getParameter("login");
        if (!Validations.isValidLogin(login)) {
            redirectInvalidLogin(request, response);
            return Optional.empty();
        }

        String password = request.getParameter("password");
        if (!Validations.isValidPassword(password)) {
            redirectInvalidPassword(request, response);
            return Optional.empty();
        }

        Optional user = loginService.get().login(login, password);
        if (!user.isPresent()) {
            redirectWrongCredentials(request, response);
            return Optional.empty();
        }

        return Optional.of(new LoginAttributes(role, user.get()));
    }

    private Optional<LoginService> getLoginService(String role) {

        if (role == null) {
            return Optional.empty();
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        switch (role) {
            case "Doctor":
                return Optional.of(serviceFactory.getDoctorService());
            case "Medic":
                return Optional.of(serviceFactory.getMedicService());
            case "Patient":
                return Optional.of(serviceFactory.getPatientService());
            default:
                return Optional.empty();
        }
    }


    private void redirectWrongRole(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("wrongRoleMsg", WebMessages.WRONG_ROLE);
        redirectFailedAuthorization(request, response);
    }

    private void redirectInvalidLogin(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("invalidLoginMsg", WebMessages.INVALID_LOGIN);
        redirectFailedAuthorization(request, response);
    }

    private void redirectInvalidPassword(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("invalidPasswordMsg", WebMessages.INVALID_PASSWORD);
        redirectFailedAuthorization(request, response);
    }

    private void redirectWrongCredentials(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("wrongCredentialsMsg", WebMessages.WRONG_CREDENTIALS);
        redirectFailedAuthorization(request, response);
    }

    private void redirectFailedAuthorization(HttpServletRequest request,
                                             HttpServletResponse response) {

        request.getSession().setAttribute("hasError", "yes");
        redirectToPage(response, WebPaths.webPaths.get("login"));
    }

    private void redirectToPage(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOG.log(Level.ERROR, "Can't redirect to login page");
            throw new RuntimeException(e);
        }
    }
}
