package controller.action.authorization;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutAction implements Action {

    private ServiceFactory serviceFactory;

    public LogOutAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        Actions.redirectToPage(response, WebPaths.webPaths.get("login"));
        return null;
    }
}
