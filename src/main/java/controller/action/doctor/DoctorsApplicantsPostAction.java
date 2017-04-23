package controller.action.doctor;

import controller.action.Action;
import controller.action.Actions;
import controller.constants.WebPaths;
import controller.validation.Validations;
import domain.DoctorDTO;
import domain.TherapyDTO;
import service.DoctorService;
import service.ServiceFactory;
import service.TherapyService;
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
        if (dto == null) {
            session.setAttribute("failedTherapySubmitting", "yes");
            Actions.redirectToPage(response,
                    WebPaths.webPaths.get("doctor.applicants") + "?" + request.getQueryString());
            return null;
        }

        long userId = ((DoctorDTO) session.getAttribute("user")).getId();
        long patientId = Long.parseLong(request.getParameter("patientId"));
        DoctorService service = serviceFactory.getDoctorService();
        service.prescribeTherapy(userId, patientId, dto);

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

        String datetime = request.getParameter("dateTime");
        if (!Validations.isValidDateTime(datetime)) {
            session.setAttribute("invalidInputMsg",
                    Actions.getFromBundle(request, "i18n/messages", "time.appointment.invalid"));
            return null;
        }

        LocalDateTime localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return new TherapyPrescriptionDTO.Builder()
                .setTitle(title)
                .setType(request.getParameter("therapyType"))
                .setDescription(description)
                .setAppointmentDateTime(Timestamp.valueOf(localDateTime))
                .build();
    }
}
