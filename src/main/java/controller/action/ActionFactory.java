package controller.action;

import controller.action.authorization.LogOutAction;
import controller.action.authorization.LoginGetAction;
import controller.action.authorization.LoginSubmitAction;
import controller.action.doctor.VisitDoctorMainpageAction;
import controller.action.medic.VisitMedicHomepageAction;
import controller.action.patient.VisitPatientMainpageAction;
import controller.action.registration.VisitDoctorRegistrationAction;
import controller.action.registration.VisitMedicRegistrationAction;
import controller.action.registration.VisitPatientRegistrationAction;
import controller.action.registration.VisitRegistrationBeginAction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActionFactory {

    private final Map<String, Action> actionMap;
    private static final ActionFactory INSTANCE = new ActionFactory();

    private ActionFactory() {
        actionMap = new HashMap<>();
        actionMap.put("GET/", new LoginGetAction());
        actionMap.put("POST/login", new LoginSubmitAction());
        actionMap.put("GET/login", new LoginGetAction());
        actionMap.put("POST/logout", new LogOutAction());
        actionMap.put("GET/registration", new VisitRegistrationBeginAction());
        actionMap.put("GET/registration/doctor", new VisitDoctorRegistrationAction());
        actionMap.put("GET/registration/medic", new VisitMedicRegistrationAction());
        actionMap.put("GET/registration/patient", new VisitPatientRegistrationAction());
        actionMap.put("GET/doctor", new VisitDoctorMainpageAction());
        actionMap.put("GET/medic", new VisitMedicHomepageAction());
        actionMap.put("GET/patient", new VisitPatientMainpageAction());
    }

    public static ActionFactory getInstance() {
        return INSTANCE;
    }

    public Optional<Action> getAction(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return Optional.ofNullable(actionMap.get(request.getMethod() + path));
    }
}
