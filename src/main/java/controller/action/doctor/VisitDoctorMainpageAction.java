package controller.action.doctor;

import controller.action.Action;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitDoctorMainpageAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return WebResources.webResources.get("doctor.main");
    }
}
