package controller.action.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import controller.validation.Validations;
import domain.DoctorDTO;
import domain.TherapyDTO;
import service.PatientService;
import service.ServiceFactory;
import service.dto.TherapyPrescriptionDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoctorsApplicantsPostAction implements Action {

    private ServiceFactory serviceFactory;

    public DoctorsApplicantsPostAction(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("invalidInputMsg");

        TherapyPrescriptionDTO dto = fetchTherapyPrescriptionDTO(request, response);
        if (dto == null ||!checkPerformerId(request, dto)) {
            session.setAttribute("failedTherapySubmitting", "yes");
            Actions.redirectToPage(response,
                    WebPaths.webPaths.get("doctor.applicants") + "?" + request.getQueryString());
            return null;
        }

        long patientId = Long.parseLong(request.getParameter("patientId"));
        PatientService service = serviceFactory.getPatientService();
        service.prescribeTherapy(patientId, dto);

        Actions.redirectToPage(response, WebPaths.webPaths.get("doctor.applicants"));
        return null;
    }

    private TherapyPrescriptionDTO fetchTherapyPrescriptionDTO(HttpServletRequest request,
                                                               HttpServletResponse response) {

        HttpSession session = request.getSession();
        String title = request.getParameter("therapyTitle");
        if (!Validations.isValidTitle(title)) {
            session.setAttribute("invalidInputMsg",
                    Actions.getFromBundle(request, "i18n/messages", "title.invalid"));
            return null;
        }

        String description = request.getParameter("therapyDescription");
        if (!Validations.isValidText(description)) {
            Actions.redirectToPage(response, WebPaths.webPaths.get("doctor.applicants"));
            return null;
        }

        LocalDateTime localDateTime = tryParseDateTime(request);
        if (localDateTime == null) {
            return null;
        }

        return new TherapyPrescriptionDTO.Builder()
                .setTitle(title)
                .setType(request.getParameter("therapyType"))
                .setDescription(description)
                .setAppointmentDateTime(Timestamp.valueOf(localDateTime))
                .setPerformerId(Long.parseLong(request.getParameter("performer")))
                .build();
    }

    private boolean checkPerformerId(HttpServletRequest request, TherapyPrescriptionDTO dto) {
        HttpSession session = request.getSession();
        long userId = ((DoctorDTO) session.getAttribute("user")).getId();
        if (dto.getPerformerId() != userId
                && dto.getType().equals(TherapyDTO.Type.SURGERY_OPERATION.toString())) {

            session.setAttribute("invalidInputMsg",
                    Actions.getFromBundle(request, "i18n/messages", "performer.mismatch"));
            return false;
        }
        return true;
    }

    private LocalDateTime tryParseDateTime(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String datetime = request.getParameter("dateTime");
        if (!Validations.isValidDateTime(datetime)) {
            session.setAttribute("invalidInputMsg",
                    Actions.getFromBundle(request, "i18n/messages", "time.appointment.invalid"));
            return null;
        }

        LocalDateTime localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (localDateTime.isBefore(LocalDateTime.now().minusSeconds(30))) {
            session.setAttribute("invalidInputMsg",
                    Actions.getFromBundle(request, "i18n/messages", "time.appointment.invalid"));
            return null;
        }
        return localDateTime;
    }
}
