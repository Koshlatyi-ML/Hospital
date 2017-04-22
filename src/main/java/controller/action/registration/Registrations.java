package controller.action.registration;

import controller.action.Actions;
import controller.validation.Validations;
import service.ServiceFactory;
import service.dto.PatientRegistrationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Registrations {

    private Registrations() {
    }

    public static void removeRegistrationErrorAttributes(HttpSession session) {
        session.removeAttribute("invalidNameMsg");
        session.removeAttribute("invalidLoginMsg");
        session.removeAttribute("invalidPasswordMsg");
        session.removeAttribute("alreadyUsedLoginMsg");
    }

    public static PatientRegistrationDTO fetchPatientRegistrationDTO(HttpServletRequest request) {
        request.getSession().setAttribute("submittedAddDoctor", "yes");
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
        if (!Validations.isValidPersonName(name)) {
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
        String invalidNameMsg = Actions.getFromBundle(request, "i18n/messages", "name.invalid");
        request.getSession().setAttribute("invalidNameMsg", invalidNameMsg);
    }

    private static void setInvalidLogin(HttpServletRequest request) {
        String invalidLoginMsg = Actions.getFromBundle(request, "i18n/messages", "login.invalid");
        request.getSession().setAttribute("invalidLoginMsg", invalidLoginMsg);
    }

    private static void setUsedLogin(HttpServletRequest request) {
        String usedLoginMsg = Actions.getFromBundle(request, "i18n/messages", "login.used");
        request.getSession().setAttribute("alreadyUsedLoginMsg", usedLoginMsg);
    }

    private static void setInvalidPassword(HttpServletRequest request) {
        String invalidPwdMsg = Actions.getFromBundle(request, "i18n/messages", "password.invalid");
        request.getSession().setAttribute("invalidPasswordMsg", invalidPwdMsg);
    }
}
