package controller.constants;

import java.util.HashMap;
import java.util.Map;

public class WebResources {
    public static final Map<String, String> webResources = new HashMap<String, String>() {{
        put("error", "/jsp/error.jsp");
        put("login", "/jsp/login.jsp");
        put("registration", "/jsp/registration.jsp");
        put("doctor.registration", "/jsp/doctor/registration.jsp");
        put("doctor.main", "/jsp/doctor/main.jsp");
        put("medic.registration", "/jsp/medic/registration.jsp");
        put("medic.main", "/jsp/medic/main.jsp");
        put("patient.registration", "/jsp/patient/registration.jsp");
        put("patient.main", "/jsp/patient/main.jsp");
    }};
}
