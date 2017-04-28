package controller.action.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import controller.validation.Validations;
import service.PatientService;
import service.ServiceFactory;
import service.TherapyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoctorsTherapiesPostAction implements Action {

    private ServiceFactory serviceFactory;

    public DoctorsTherapiesPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("invalidInputMsg");

        String diagnosis = request.getParameter("diagnosis");
        if (diagnosis == null) {
//            long therapyId = Long.parseLong(request.getParameter("therapyId"));
            performTherapy(Long.parseLong(request.getParameter("therapyId")));
            Actions.redirectToPage(response, WebPaths.get("doctor.therapies"));
            return null;
        }

        if (!Validations.isValidTitle(diagnosis)) {
            setInvalidDiagnosisMesage(request);
            Actions.redirectToPage(response, WebPaths.get("doctor.therapies"));
            return null;
        }

        long patientId = Long.parseLong(request.getParameter("patientId"));
        dischargePatient(patientId, diagnosis);
        Actions.redirectToPage(response, WebPaths.get("doctor.therapies"));
        return null;
    }

    private void performTherapy(long therapyId) {
        TherapyService service = serviceFactory.getTherapyService();
        service.performTherapy(therapyId);
    }

    private void setInvalidDiagnosisMesage(HttpServletRequest request) {
        request.getSession().setAttribute("failedTherapyPerforming", "yes");
        request.getSession().setAttribute("invalidInputMsg",
                Actions.getFromBundle(request, "i18n/messages", "diagnosis.invalid"));
    }

    private void dischargePatient(long therapyId, String diagnosis) {
        PatientService service = serviceFactory.getPatientService();
        service.discharge(therapyId, diagnosis);
    }
}
