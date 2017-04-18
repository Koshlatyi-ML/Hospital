package controller.action;

import controller.action.authorization.LogOutAction;
import controller.action.authorization.LoginGetAction;
import controller.action.authorization.LoginPostAction;
import controller.action.doctor.VisitDoctorMainpageAction;
import controller.action.medic.VisitMedicHomepageAction;
import controller.action.patient.VisitPatientMainpageAction;
import controller.action.registration.*;

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
        actionMap.put("GET/login", new LoginGetAction());
        actionMap.put("POST/login", new LoginPostAction());
        actionMap.put("POST/logout", new LogOutAction());
        actionMap.put("GET/registration", new BeginRegistrationGetAction());
        actionMap.put("GET/registration/doctor", new DoctorRegistrationGetAction());
        actionMap.put("POST/registration/doctor", new DoctorRegistrationPostAction());
        actionMap.put("GET/registration/medic", new MedicRegistrationGetAction());
        actionMap.put("POST/registration/medic", new MedicRegistrationPostAction());
        actionMap.put("GET/registration/patient", new PatientRegistrationGetAction());
        actionMap.put("POST/registration/patient", new PatientRegistrationPostAction());
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
