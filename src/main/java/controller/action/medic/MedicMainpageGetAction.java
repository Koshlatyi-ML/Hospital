package controller.action.medic;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import domain.MedicDTO;
import domain.PatientDTO;
import domain.TherapyDTO;
import service.PatientService;
import service.ServiceFactory;
import service.TherapyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class MedicMainpageGetAction implements Action {

    private ServiceFactory serviceFactory;

    public MedicMainpageGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int offset = Actions.paginateRequset(request);

        Long userId = ((MedicDTO)session.getAttribute("user")).getId();
        TherapyService therapyService = serviceFactory.getTherapyService();
        PatientService patientService = serviceFactory.getPatientService();

        List<TherapyDTO> therapies =
                therapyService.getCurrentByPerformerId(userId, offset, Actions.PAGE_SIZE);
        List<PatientDTO> patients = new ArrayList<>();
        therapies.forEach(therapy -> {
            long patientId = therapy.getPatientId();
            PatientDTO patient = patientService.get(patientId).orElseThrow(IllegalStateException::new);
            patients.add(patient);
        });

        session.setAttribute("therapies", therapies);
        session.setAttribute("patients", patients);
        return WebResources.webResources.get("medic.main");
    }
}
