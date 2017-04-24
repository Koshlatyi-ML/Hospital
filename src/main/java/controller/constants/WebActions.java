package controller.constants;

import java.util.*;

public class WebActions {

    private WebActions() {
    }

    public static final List<String> publicActions = Arrays.asList(
            "GET/", "GET/login", "POST/login",
            "POST/logout", "GET/registration", "POST/registration");

    public static final Map<String, List<String>> roleAccessibleActions =
            new HashMap<String, List<String>>() {{
                put("Admin", new ArrayList<String>() {{
                    add("GET/admin");
                    add("GET/admin/add-department");
                    add("GET/admin/change-department");
                    add("POST/admin/add-department");
                    add("POST/admin/change-department/delete");
                    add("GET/admin/change-department/rename");
                    add("POST/admin/change-department/rename");
                    add("GET/admin/add-doctor");
                    add("POST/admin/add-doctor");
                    add("GET/admin/add-medic");
                    add("POST/admin/add-medic");
                    addAll(publicActions);
                }});
                put("Doctor", new ArrayList<String>() {{
                    add("POST/doctor/therapies/redirect");
                    add("POST/doctor/therapies");
                    add("GET/doctor/therapies");
                    add("POST/doctor/applicants");
                    add("GET/doctor/applicants");
                    add("GET/doctor");
                    addAll(publicActions);
                }});
                put("Medic", new ArrayList<String>() {{
                    add("GET/medic");
                    add("POST/medic");
                    addAll(publicActions);
                }});
                put("Patient", new ArrayList<String>() {{
                    add("GET/patient");
                    add("POST/patient/choose-doctor");
                    addAll(publicActions);
                }});
            }};
}
