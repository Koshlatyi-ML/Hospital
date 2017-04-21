package controller.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebResources {
    public static final Map<String, String> webResources = new HashMap<String, String>() {{
        put("pageNotFound", "/jsp/PageNotFound.jsp");
        put("error", "/jsp/error.jsp");
        put("login", "/jsp/login.jsp");
        put("registration", "/jsp/registration.jsp");
//        put("registration.doctor", "/jsp/doctor/registration.jsp");
//        put("registration.medic", "/jsp/medic/registration.jsp");
//        put("registration.patient", "/jsp/registration.jsp");
        put("doctor.main", "/jsp/doctor/main.jsp");
        put("medic.main", "/jsp/medic/main.jsp");
        put("patient.main", "/jsp/patient/main.jsp");
        put("admin.main", "/jsp/admin/main.jsp");
        put("admin.department.add", "/jsp/admin/add-department.jsp");
        put("admin.department.change", "/jsp/admin/change-department.jsp");
        put("admin.department.change.rename", "/jsp/admin/change-department-rename.jsp");
    }};

    public static final List<String> allowedFilenameExtensions =
            Arrays.asList(".css", ".js", ".js.map", ".png", ".ico",
                    ".woff", ".woff2", "ttf", ".jpg");
}
