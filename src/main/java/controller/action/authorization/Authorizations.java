package controller.action.authorization;

import javax.servlet.http.HttpSession;

class Authorizations {

    private Authorizations() {}

    static void removeLoginErrorAttributes(HttpSession session) {
        session.removeAttribute("wrongRoleMsg");
        session.removeAttribute("invalidLoginMsg");
        session.removeAttribute("invalidPasswordMsg");
        session.removeAttribute("wrongCredentialsMsg");
    }
}
