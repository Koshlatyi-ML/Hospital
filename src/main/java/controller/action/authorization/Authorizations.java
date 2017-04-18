package controller.action.authorization;

import controller.constants.WebMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class Authorizations {

    private Authorizations() {}

    static void removeLoginErrorAttributes(HttpSession session) {
        session.removeAttribute("invalidLoginMsg");
        session.removeAttribute("invalidPasswordMsg");
        session.removeAttribute("wrongCredentialsMsg");
    }

    static void setInvalidLogin(HttpServletRequest request) {
        request.getSession().setAttribute("invalidLoginMsg", WebMessages.INVALID_LOGIN);
        setFailedAuthorization(request);
    }

    static void setInvalidPassword(HttpServletRequest request) {
        request.getSession().setAttribute("invalidPasswordMsg", WebMessages.INVALID_PASSWORD);
        setFailedAuthorization(request);
    }

    static void setWrongCredentials(HttpServletRequest request) {
        request.getSession().setAttribute("wrongCredentialsMsg", WebMessages.WRONG_CREDENTIALS);
        setFailedAuthorization(request);
    }

    private static void setFailedAuthorization(HttpServletRequest request) {
        request.getSession().setAttribute("failedLogin", "yes");
    }
}
