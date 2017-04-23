package controller.action;

import controller.action.admin.*;
import controller.action.admin.department.*;
import controller.action.admin.doctor.AddDoctorGetAction;
import controller.action.admin.doctor.AddDoctorPostAction;
import controller.action.admin.medic.AddMedicGetAction;
import controller.action.admin.medic.AddMedicPostAction;
import controller.action.authorization.LogOutAction;
import controller.action.authorization.LoginGetAction;
import controller.action.authorization.LoginPostAction;
import controller.action.doctor.VisitDoctorMainpageAction;
import controller.action.medic.VisitMedicHomepageAction;
import controller.action.patient.PatientMainpageGetAction;
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
        actionMap.put("GET/registration", new RegistrationGetAction());
        actionMap.put("POST/registration", new RegistrationPostAction());
        actionMap.put("GET/admin", new AdminMainpageGetAction());
        actionMap.put("GET/admin/add-department", new AddDepartmentGetAction());
        actionMap.put("POST/admin/add-department", new AddDepartmentPostAction());
        actionMap.put("GET/admin/change-department", new ChangeDepartmentGetAction());
        actionMap.put("POST/admin/change-department/delete", new DeleteDepartmentPostAction());
        actionMap.put("GET/admin/change-department/rename", new RenameDepartmentGetAction());
        actionMap.put("POST/admin/change-department/rename", new RenameDepartmentPostAction());
        actionMap.put("GET/admin/add-doctor", new AddDoctorGetAction());
        actionMap.put("POST/admin/add-doctor", new AddDoctorPostAction());
        actionMap.put("GET/admin/add-medic", new AddMedicGetAction());
        actionMap.put("POST/admin/add-medic", new AddMedicPostAction());
        actionMap.put("GET/doctor", new VisitDoctorMainpageAction());
        actionMap.put("GET/medic", new VisitMedicHomepageAction());
        actionMap.put("GET/patient", new PatientMainpageGetAction());
    }

    public static ActionFactory getInstance() {
        return INSTANCE;
    }

    public Optional<Action> getAction(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return Optional.ofNullable(actionMap.get(request.getMethod() + path));
    }
}
