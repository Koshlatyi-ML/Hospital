package controller.action.admin.doctor;

import controller.action.registration.Registrations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class DoctorRegistrations {

    private DoctorRegistrations() {}

    static void prepareRegistrationGetAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("submittedAddDoctor") == null) {
            Registrations.removeRegistrationErrorAttributes(session);
            session.removeAttribute("successfulAddDoctor");
        }

        session.removeAttribute("submittedAddDoctor");
    }
}
