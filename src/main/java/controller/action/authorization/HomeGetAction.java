package controller.action.authorization;

import controller.action.Action;
import controller.constants.WebResources;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeGetAction implements Action {

    private ServiceFactory serviceFactory;

    public HomeGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return WebResources.get("home");
    }
}
