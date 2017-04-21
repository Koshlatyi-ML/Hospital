package controller.action.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

class Authorizations {

    static void removeLoginErrorAttributes(HttpSession session) {
        session.removeAttribute("invalidLoginMsg");
        session.removeAttribute("invalidPasswordMsg");
        session.removeAttribute("wrongCredentialsMsg");
    }

    static void setInvalidLogin(HttpServletRequest request, ResourceBundle bundle) {
        request.getSession().setAttribute("invalidLoginMsg", bundle.getString("login.invalid"));
        setFailedAuthorization(request);
    }

    static void setInvalidPassword(HttpServletRequest request, ResourceBundle bundle) {
        request.getSession().setAttribute("invalidPasswordMsg", bundle.getString("password.invalid"));
        setFailedAuthorization(request);
    }

    static void setWrongCredentials(HttpServletRequest request, ResourceBundle bundle)  {
        request.getSession().setAttribute("wrongCredentialsMsg", bundle.getString("credentials.wrong"));
        setFailedAuthorization(request);
    }

    private static void setFailedAuthorization(HttpServletRequest request) {
        request.getSession().setAttribute("failedLogin", "yes");
    }
}
