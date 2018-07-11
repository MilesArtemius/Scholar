package classes;

import classes.utils.Bundle;
import classes.windows.Initialization;

public class UserInfo {
    private static UserInfo ourInstance = new UserInfo();

    public static final String secure_key         = "SECURE";
    public static final String name_key           = "NAME";
    public static final String email_key          = "EMAIL";
    public static final String subject_key        = "SUBJECT";
    public static final String login_key          = "LOGIN";
    public static final String passwordHash_key   = "PASSWORD_HASH";

    public String name;
    public String email;
    public String subject;
    public boolean secure;
    public String login;
    public String passwordHash;



    public static UserInfo get() {
        if (ourInstance == null) {
            ourInstance = new UserInfo();
        }
        return ourInstance;
    }

    private UserInfo() {}

    public void update(Bundle bundle) {
        ourInstance.name = bundle.getString(name_key, "Nulla Pariatur");
        ourInstance.email = bundle.getString(email_key, "spqr@roma.la");
        ourInstance.subject = bundle.getString(subject_key, Initialization.ult_const);
        ourInstance.secure = bundle.getBoolean(secure_key, false);
        ourInstance.login = bundle.getString(login_key, "khazad-dum");
        ourInstance.passwordHash = bundle.getString(passwordHash_key, "melon");

        InternetPassage.open().connect(ourInstance.email);
        InternetPassage.open().updateInfo(Bundle.collect(new String[]{name_key, subject_key}, new String[]{ourInstance.name, ourInstance.subject}));
    }
}
