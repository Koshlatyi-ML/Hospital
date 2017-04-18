package controller.action.registration;

import controller.action.Action;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitDoctorRegistrationAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return WebResources.webResources.get("doctor.registration");
    }
}
