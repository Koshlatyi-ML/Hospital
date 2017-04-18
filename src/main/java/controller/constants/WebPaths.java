package controller.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebPaths {
    public static final Map<String, String> webPaths = new HashMap<String, String>() {{
        put("login", "/login");
        put("logout", "/logout");
        put("registration", "/medic");
        put("registration.patient", "/registration/patient");
        put("registration.doctor", "/registration/doctor");
        put("registration.medic", "/registration/medic");
        put("patient.main", "/patient");
        put("doctor.main", "/doctor");
        put("medic.main", "/medic");
    }};

    public static final List<String> publicPaths = Arrays.asList(
            "/login", "/logout", "/registration", "/registration/doctor",
            "/registration/medic", "/registration/patient");

    public static final Map<String, List<String>> roleAccessiblePaths =
            new HashMap<String, List<String>>() {{
                put("Doctor", Arrays.asList("/", "/doctor"));
                put("Medic", Arrays.asList("/", "/medic"));
                put("Patient", Arrays.asList("/", "/patient"));
            }};
}
