package controller.constants;

public class WebMessages {
    public static final String INVALID_NAME = "Invalid name or surname. 1-64 characters long:" +
            " latin or cyrillic alphabetic.";
    public static final String INVALID_LOGIN = "Invalid login. 8-20 characters long:" +
            " numbers, upper-lowercase alphabetic, . and _ symbols;" +
            " no _ or . at the beginning" +
            " no __ or _. or ._ or .. inside," +
            " no _ or . at the end.";
    public static final String USED_LOGIN = "This login has already used.";
    public static final String INVALID_PASSWORD = "Invalid password  - minimum 8 characters" +
            " maximum 20 characters at least 1 Uppercase Alphabet," +
            " 1 Lowercase Alphabet and 1 Number.";
    public static final String WRONG_CREDENTIALS = "Wrong credentials. Try again.";
}
