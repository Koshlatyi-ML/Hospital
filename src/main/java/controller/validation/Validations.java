package controller.validation;

public class Validations {

    private Validations() {}

    public static boolean isValidLogin(String login) {
        return login.matches("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$");
    }

    public static boolean isValidPersonName(String name) {
        return name.matches("^[a-zA-Zа-яА-ЯёЁ ,.'-]{1,64}$");
    }

    public static boolean isValidDepartmentName(String name) {
        return name.matches("^[a-zA-Zа-яА-ЯёЁ0-9 №-]{1,64}$");
    }
}
