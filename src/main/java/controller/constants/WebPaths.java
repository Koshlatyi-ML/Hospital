package controller.constants;

import java.util.HashMap;
import java.util.Map;

public class WebPaths {
    public static final Map<String, String> webPaths = new HashMap<String, String>() {{
        put("login", "/login");
        put("logout", "/logout");
        put("registration", "/registration");
        put("patient.main", "/patient");
        put("doctor.main", "/doctor");
        put("doctor.applicants", "/doctor/applicants");
        put("medic.main", "/medic");
        put("admin.main", "/admin");
        put("admin.department.add", "/admin/add-department");
        put("admin.department.change", "/admin/change-department");
        put("admin.department.change.rename", "/admin/change-department/rename");
        put("admin.doctor.add", "/admin/add-doctor");
        put("admin.medic.add", "/admin/add-medic");
    }};
}
