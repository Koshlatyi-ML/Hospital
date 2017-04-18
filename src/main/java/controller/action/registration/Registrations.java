package controller.action.registration;

import controller.constants.WebMessages;
import controller.validation.Validations;
import service.ServiceFactory;
import service.dto.PatientRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class Registrations {

    private Registrations() {
    }

    static void removeRegistrationErrorAttributes(HttpSession session) {
        session.removeAttribute("invalidNameMsg");
        session.removeAttribute("invalidLoginMsg");
        session.removeAttribute("invalidPasswordMsg");
        session.removeAttribute("alreadyUsedLoginMsg");
    }

    static void prepareRegistrationGetAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("failedRegistration") == null) {
            Registrations.removeRegistrationErrorAttributes(session);
        }

        session.removeAttribute("failedRegistration");
    }

    static PatientRegistrationDTO fetchPatientRegistrationDTO(HttpServletRequest request) {
        String name = request.getParameter("name");
        if (!checkName(request, name)) {
            return null;
        }

        String surname = request.getParameter("surname");
        if (!checkName(request, surname)) {
            return null;
        }

        String login = request.getParameter("login");
        if (!checkLogin(request, login)) {
            return null;
        }

        String password = request.getParameter("password");
        if (!checkPassword(request, password)) {
            return null;
        }

        return new PatientRegistrationDTO.Builder()
                .setName(name)
                .setSurname(surname)
                .setLogin(login)
                .setPassword(password)
                .build();
    }

    private static boolean checkName(HttpServletRequest request, String name) {
        if (!Validations.isValidName(name)) {
            Registrations.setInvalidName(request);
            return false;
        }
        return true;
    }

    private static boolean checkLogin(HttpServletRequest request, String login) {
        if (!Validations.isValidLogin(login)) {
            Registrations.setInvalidLogin(request);
            return false;
        }
        if (ServiceFactory.getInstance().getCredentialsService().hasLogin(login)) {
            Registrations.setUsedLogin(request);
            return false;
        }
        return true;
    }

    private static boolean checkPassword(HttpServletRequest request, String password) {
        if (!Validations.isValidPassword(password)) {
            Registrations.setInvalidPassword(request);
            return false;
        }
        return true;
    }

    private static void setInvalidName(HttpServletRequest request) {
        request.getSession().setAttribute("invalidNameMsg", WebMessages.INVALID_NAME);
        setFailedRegistration(request);
    }

    private static void setInvalidLogin(HttpServletRequest request) {
        request.getSession().setAttribute("invalidLoginMsg", WebMessages.INVALID_LOGIN);
        setFailedRegistration(request);
    }

    private static void setUsedLogin(HttpServletRequest request) {
        request.getSession().setAttribute("alreadyUsedLoginMsg", WebMessages.USED_LOGIN);
        setFailedRegistration(request);
    }

    private static void setInvalidPassword(HttpServletRequest request) {
        request.getSession().setAttribute("invalidPasswordMsg", WebMessages.INVALID_PASSWORD);
        setFailedRegistration(request);
    }

    private static void setFailedRegistration(HttpServletRequest request) {
        request.getSession().setAttribute("failedRegistration", "yes");
    }
}
