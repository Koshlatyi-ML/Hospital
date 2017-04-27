package controller.action.patient;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import controller.validation.Validations;
import domain.PatientDTO;
import service.PatientService;
import service.ServiceFactory;
import service.dto.PatientApplicationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChooseDoctorPostAction implements Action {

    private ServiceFactory serviceFactory;

    public ChooseDoctorPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String complaints = request.getParameter("complaints");
        if (!Validations.isValidText(complaints)) {
            Actions.redirectToPage(response, WebPaths.get("patient.main"));
            return null;
        }

        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        PatientDTO user = (PatientDTO) request.getSession().getAttribute("user");

        PatientApplicationDTO dto = new PatientApplicationDTO.Builder()
                .setDoctorId(doctorId)
                .setComplaints(complaints)
                .build();

        serviceFactory.getPatientService().applyToDoctor(user, dto);

        Actions.redirectToPage(response, WebPaths.get("patient.main"));
        return null;
    }
}
