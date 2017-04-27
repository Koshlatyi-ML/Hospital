package controller.action.proxy;

import controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocalizationActionProxy implements Action {

    private Action realAction;

    public LocalizationActionProxy(Action realAction) {
        this.realAction = realAction;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String languageParameter = request.getParameter("language");
        if (languageParameter != null) {
            session.setAttribute("language", languageParameter);
            return realAction.execute(request, response);
        }

        if (session.getAttribute("language") == null) {
            session.setAttribute("language", request.getLocale().toString());
        }

        return realAction.execute(request, response);
    }
}
