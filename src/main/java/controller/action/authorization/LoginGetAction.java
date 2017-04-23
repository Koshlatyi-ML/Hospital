package controller.action.authorization;

import controller.action.Action;
import controller.constants.WebResources;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginGetAction implements Action {

    private ServiceFactory serviceFactory;

    public LoginGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("failedLogin") == null) {
            Authorizations.removeLoginErrorAttributes(session);
        }

        session.removeAttribute("failedLogin");
        return WebResources.webResources.get("login");
    }
}
