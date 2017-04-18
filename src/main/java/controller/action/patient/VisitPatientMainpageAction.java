package controller.action.patient;

import controller.action.Action;
import controller.constants.WebResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitPatientMainpageAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return WebResources.webResources.get("patient.main");
    }
}
