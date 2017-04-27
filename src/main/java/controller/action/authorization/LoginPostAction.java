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
import java.util.ResourceBundle;

public class LoginPostAction implements Action {

    private ServiceFactory serviceFactory;

    public LoginPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

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

        LoginAttributes attributes = fetchLoginAttributes(request);
        if (attributes == null) {
            Actions.redirectToPage(response, WebPaths.get("login"));
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
                return WebPaths.get("doctor.main");
            case "Medic":
                return WebPaths.get("medic.main");
            case "Patient":
                return WebPaths.get("patient.main");
            case "Admin":
                return WebPaths.get("admin.main");
            default:
                throw new IllegalStateException();
        }
    }

    private LoginAttributes fetchLoginAttributes(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login.equals("administrator") && password.equals("Admin1111")) {
            return new LoginAttributes("Admin", null);
        }

        Object locale = request.getSession().getAttribute("language");
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages",
                Actions.parseLocaleAttribute(locale));

        if (!Validations.isValidLogin(login)) {
            Authorizations.setInvalidLogin(request, bundle);
            return null;
        }

        if (!Validations.isValidPassword(password)) {
            Authorizations.setInvalidPassword(request, bundle);
            return null;
        }

        String role = request.getParameter("role");
        Optional user = getLoginService(role).login(login, password);
        if (!user.isPresent()) {
            Authorizations.setWrongCredentials(request, bundle);
            return null;
        }

        return new LoginAttributes(role, user.get());
    }


    private LoginService getLoginService(String role) {
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
