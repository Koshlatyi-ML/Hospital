package controller.action.admin.medic;

import controller.action.registration.Registrations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class MedicRegistrations {

    private MedicRegistrations() {}

    static void prepareRegistrationGetAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("submittedAddMedic") == null) {
            Registrations.removeRegistrationErrorAttributes(session);
            session.removeAttribute("successfulAddMedic");
        }

        session.removeAttribute("submittedAddMedic");
    }
}
