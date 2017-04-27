package controller.action.doctor;

import controller.action.Action;
import controller.constants.WebResources;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoctorMainpageGetAction implements Action {

    private ServiceFactory serviceFactory;

    public DoctorMainpageGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return WebResources.get("doctor.main");
    }
}
