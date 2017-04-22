package controller.action.admin.department;

import javax.servlet.http.HttpSession;

class DepartmentActions {

    private DepartmentActions() {}

    static void removeMsgAttributes(HttpSession session) {
        session.removeAttribute("successfulAddDepartment");
        session.removeAttribute("usedDepartmentNameMsg");
    }
}
