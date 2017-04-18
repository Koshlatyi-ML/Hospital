package controller.constants;

import java.util.HashMap;
import java.util.Map;

public class WebResources {
    public static final Map<String, String> webResources = new HashMap<String, String>() {{
        put("pageNotFound", "/jsp/PageNotFound.jsp");
        put("error", "/jsp/error.jsp");
        put("login", "/jsp/login.jsp");
        put("registration", "/jsp/registration.jsp");
        put("registration.doctor", "/jsp/doctor/registration.jsp");
        put("registration.medic", "/jsp/medic/registration.jsp");
        put("registration.patient", "/jsp/patient/registration.jsp");
        put("doctor.main", "/jsp/stuff/doctor/main.jsp");
        put("medic.main", "/jsp/stuff/medic/main.jsp");
        put("patient.main", "/jsp/patient/main.jsp");
    }};
}
