package controller.action.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebResources;
import domain.DoctorDTO;
import domain.MedicDTO;
import domain.PatientDTO;
import domain.TherapyDTO;
import service.MedicService;
import service.PatientService;
import service.ServiceFactory;
import service.TherapyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class DoctorsTherapiesGetAction implements Action {

    private ServiceFactory serviceFactory;

    public DoctorsTherapiesGetAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("failedTherapyPerforming") == null) {
            session.removeAttribute("invalidInputMsg");
        }
        session.removeAttribute("failedTherapyPerforming");

        DoctorDTO user = (DoctorDTO) session.getAttribute("user");
        int offset = Actions.paginateRequset(request);

        TherapyService therapyService = serviceFactory.getTherapyService();
        PatientService patientService = serviceFactory.getPatientService();
        MedicService medicService = serviceFactory.getMedicService();

        List<MedicDTO> departmentMedics =
                medicService.getByDepartmentId(user.getDepartmentId(), offset, Actions.PAGE_SIZE);
        List<TherapyDTO> doctorTherapies =
                therapyService.getCurrentByPerformerId(user.getId(), offset, Actions.PAGE_SIZE);
        List<PatientDTO> treatedPatients = new ArrayList<>();
        doctorTherapies.forEach(therapy -> {
            long patientId = therapy.getPatientId();
            PatientDTO patient = patientService.get(patientId).orElseThrow(IllegalStateException::new);
            treatedPatients.add(patient);
        });

        session.setAttribute("therapies", doctorTherapies);
        session.setAttribute("patients", treatedPatients);
        session.setAttribute("medics", departmentMedics);
        session.setAttribute("totalSize", therapyService.getCurrentByDoctorIdCount(user.getId()));

        return WebResources.webResources.get("doctor.therapies");
    }
}
