package controller.action.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class PatientRegistrations {

    private PatientRegistrations() {}

    static void prepareRegistrationGetAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("failedRegistration") == null) {
            Registrations.removeRegistrationErrorAttributes(session);
        }

        session.removeAttribute("failedRegistration");
    }
}
