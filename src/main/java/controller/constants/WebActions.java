package controller.constants;

import java.util.*;
import java.util.stream.Collectors;

public class WebActions {

    private static ResourceBundle bundle = ResourceBundle.getBundle("controller/actions");

    private WebActions() {
    }

    public static String getAction(String key) {
        return bundle.getString(key);
    }

    public static final List<String> publicActions = Arrays.asList(
            bundle.getString("home.get"),
            bundle.getString("login.get"),
            bundle.getString("login.post"),
            bundle.getString("logout.post"),
            bundle.getString("registration.get"),
            bundle.getString("registration.post"));

    public static final Map<String, List<String>> roleAccessibleActions =
            new HashMap<String, List<String>>() {{
                put("Admin", new ArrayList<String>() {{
                    add(bundle.getString("admin.get"));
                    add(bundle.getString("admin.add-department.get"));
                    add(bundle.getString("admin.change-department.get"));
                    add(bundle.getString("admin.add-department.post"));
                    add(bundle.getString("admin.change-department.delete.post"));
                    add(bundle.getString("admin.change-department.rename.get"));
                    add(bundle.getString("admin.change-department.rename.post"));
                    add(bundle.getString("admin.add-doctor.get"));
                    add(bundle.getString("admin.add-doctor.post"));
                    add(bundle.getString("admin.add-medic.get"));
                    add(bundle.getString("admin.add-medic.post"));
                    addAll(publicActions);
                }});
                put("Doctor", new ArrayList<String>() {{
                    add(bundle.getString("doctor.get"));
                    add(bundle.getString("doctor.applicants.get"));
                    add(bundle.getString("doctor.applicants.post"));
                    add(bundle.getString("doctor.therapies.get"));
                    add(bundle.getString("doctor.therapies.post"));
                    add(bundle.getString("doctor.therapies.redirect.post"));
                    addAll(publicActions);
                }});
                put("Medic", new ArrayList<String>() {{
                    add(bundle.getString("medic.get"));
                    add(bundle.getString("medic.post"));
                    addAll(publicActions);
                }});
                put("Patient", new ArrayList<String>() {{
                    add(bundle.getString("patient.get"));
                    add(bundle.getString("patient.choose-doctor.post"));
                    addAll(publicActions);
                }});
            }};

    public static final List<String> allActions = WebActions.roleAccessibleActions.values().stream()
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
}
