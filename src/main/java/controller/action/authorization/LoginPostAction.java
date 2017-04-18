package controller.action.authorization;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import controller.validation.Validations;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginPostAction implements Action {

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

        LoginAttributes attributes = fetchLoginAttributes(request, response);
        if (attributes == null) {
            Actions.redirectToPage(response, WebPaths.webPaths.get("login"));
            return null;
        }

        session.setAttribute("logined", "yes");
        session.setAttribute("role", attributes.role);
        session.setAttribute("user", attributes.user);
        Actions.redirectToPage(response, getHomeURL(attributes.role));

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

    private LoginAttributes fetchLoginAttributes(HttpServletRequest request,
                                                 HttpServletResponse response) {

        String login = request.getParameter("login");
        if (!Validations.isValidLogin(login)) {
            Authorizations.setInvalidLogin(request);
            return null;
        }

        String password = request.getParameter("password");
        if (!Validations.isValidPassword(password)) {
            Authorizations.setInvalidPassword(request);
            return null;
        }

        String role = request.getParameter("role");
        Optional user = getLoginService(role).login(login, password);
        if (!user.isPresent()) {
            Authorizations.setWrongCredentials(request);
            return null;
        }

        return new LoginAttributes(role, user.get());
    }

    private LoginService getLoginService(String role) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        switch (role) {
            case "Doctor":
                return serviceFactory.getDoctorService();
            case "Medic":
                return serviceFactory.getMedicService();
            case "Patient":
                return serviceFactory.getPatientService();
            default:
                throw new IllegalStateException();
        }
    }
}
