package controller.action;

import controller.action.admin.*;
import controller.action.admin.department.*;
import controller.action.admin.doctor.AddDoctorGetAction;
import controller.action.admin.doctor.AddDoctorPostAction;
import controller.action.admin.medic.AddMedicGetAction;
import controller.action.admin.medic.AddMedicPostAction;
import controller.action.authorization.HomeGetAction;
import controller.action.authorization.LogOutAction;
import controller.action.authorization.LoginGetAction;
import controller.action.authorization.LoginPostAction;
import controller.action.doctor.*;
import controller.action.medic.MedicMainpageGetAction;
import controller.action.medic.MedicsTherapyPostAction;
import controller.action.patient.ChooseDoctorPostAction;
import controller.action.patient.PatientMainpageGetAction;
import controller.action.proxy.LocalizationActionProxy;
import controller.action.proxy.RoleSecurityProxy;
import controller.action.registration.*;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private final Map<String, Action> actionMap;
    private static final ActionFactory INSTANCE = new ActionFactory();

    private ActionFactory() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        actionMap = new HashMap<>();
        actionMap.put("GET/home", new HomeGetAction(serviceFactory));
        actionMap.put("GET/login", new LoginGetAction(serviceFactory));
        actionMap.put("POST/login", new LoginPostAction(serviceFactory));
        actionMap.put("POST/logout", new LogOutAction(serviceFactory));
        actionMap.put("GET/registration", new RegistrationGetAction(serviceFactory));
        actionMap.put("POST/registration", new RegistrationPostAction(serviceFactory));
        actionMap.put("GET/admin", new AdminMainpageGetAction(serviceFactory));
        actionMap.put("GET/admin/add-department", new AddDepartmentGetAction(serviceFactory));
        actionMap.put("POST/admin/add-department", new AddDepartmentPostAction(serviceFactory));
        actionMap.put("GET/admin/change-department", new ChangeDepartmentGetAction(serviceFactory));
        actionMap.put("POST/admin/change-department/delete", new DeleteDepartmentPostAction(serviceFactory));
        actionMap.put("POST/admin/change-department/rename", new RenameDepartmentPostAction(serviceFactory));
        actionMap.put("GET/admin/add-doctor", new AddDoctorGetAction(serviceFactory));
        actionMap.put("POST/admin/add-doctor", new AddDoctorPostAction(serviceFactory));
        actionMap.put("GET/admin/add-medic", new AddMedicGetAction(serviceFactory));
        actionMap.put("POST/admin/add-medic", new AddMedicPostAction(serviceFactory));
        actionMap.put("GET/doctor", new DoctorMainpageGetAction(serviceFactory));
        actionMap.put("GET/doctor/applicants", new DoctorsApplicantsGetAction(serviceFactory));
        actionMap.put("POST/doctor/applicants", new DoctorsApplicantsPostAction(serviceFactory));
        actionMap.put("GET/doctor/therapies", new DoctorsTherapiesGetAction(serviceFactory));
        actionMap.put("POST/doctor/therapies", new DoctorsTherapiesPostAction(serviceFactory));
        actionMap.put("POST/doctor/therapies/redirect", new DoctorsTherapyRedirectGetAction(serviceFactory));
        actionMap.put("GET/medic", new MedicMainpageGetAction(serviceFactory));
        actionMap.put("POST/medic", new MedicsTherapyPostAction(serviceFactory));
        actionMap.put("GET/patient", new PatientMainpageGetAction(serviceFactory));
        actionMap.put("POST/patient/choose-doctor", new ChooseDoctorPostAction(serviceFactory));
    }

    public static ActionFactory getInstance() {
        return INSTANCE;
    }

    private Action getActionProxy(Action action) {
        return new RoleSecurityProxy(new LocalizationActionProxy(action));
    }

    public Action getAction(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return getActionProxy(actionMap.get(request.getMethod() + path));
    }
}
