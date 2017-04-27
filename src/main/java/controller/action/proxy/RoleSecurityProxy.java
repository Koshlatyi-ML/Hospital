package controller.action.proxy;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebActions;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RoleSecurityProxy implements Action {

    private Action realAction;

    public RoleSecurityProxy(Action realAction) {
        this.realAction = realAction;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String actionSignature = request.getMethod() + path;

        if (WebActions.publicActions.contains(actionSignature)) {
            return realAction.execute(request, response);
        }

        if (session.getAttribute("logined") == null) {
            Actions.forwardRequest(WebResources.get("login"), request, response);
            return null;
        }

        if (hasRoleAccess(actionSignature, session)) {
            return realAction.execute(request, response);
        }

        Actions.forwardRequest(WebResources.get("pageNotFound"), request, response);
        return null;
    }

    private boolean hasRoleAccess(String actionSignature, HttpSession session) {
        return WebActions.roleAccessibleActions.get(session.getAttribute("role")).contains(actionSignature);
    }
}
