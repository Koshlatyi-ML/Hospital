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
        put("doctor.main", "/jsp/doctor/main.jsp");
        put("doctor.applicants", "/jsp/doctor/doctor-applicants.jsp");
        put("doctor.therapies", "/jsp/doctor/doctor-therapies.jsp");
        put("medic.main", "/jsp/medic/main.jsp");
        put("patient.main", "/jsp/patient/main.jsp");
        put("admin.main", "/jsp/admin/main.jsp");
        put("admin.department.add", "/jsp/admin/department/add-department.jsp");
        put("admin.department.change", "/jsp/admin/department/change-department.jsp");
        put("admin.doctor.add", "/jsp/admin/doctor/add-doctor.jsp");
        put("admin.medic.add", "/jsp/admin/medic/add-medic.jsp");
    }};

    public static final List<String> allowedFilenameExtensions =
            Arrays.asList(".css", ".js", ".js.map", ".png", ".ico",
                    ".woff", ".woff2", "ttf", ".jpg");
}
