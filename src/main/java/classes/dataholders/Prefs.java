package classes.dataholders;

import classes.utils.Bundle;

import java.util.Map;
import java.util.prefs.Preferences;

public class Prefs {
    // class is quite useless, in addition it works as a locator to the resources in package;
    private static Prefs preferences = new Prefs();

    private Preferences prefs;

    public static final String init_key           = "KEY_INIT";
    public static final String secure_key         = "SECURE";
    public static final String name_key           = "NAME";
    public static final String email_key          = "EMAIL";
    public static final String subject_key        = "SUBJECT";
    public static final String login_key          = "LOGIN";
    public static final String passwordHash_key   = "PASSWORD_HASH";
    public static final String path_key           = "PATH";


    public static Prefs pull() {
        if (preferences == null) {
            preferences = new Prefs();
        }
        return preferences;
    }

    private Prefs() {
        this.prefs = Preferences.userRoot().node("scholar-admin");
    }



    public String getString(String key, String def) {
        return preferences.prefs.get(key, def);
    }

    public boolean getBoolean(String key, boolean def) {
        return preferences.prefs.getBoolean(key, def);
    }



    public void setString(String key, String value) {
        preferences.prefs.put(key, value);
    }

    public void setBoolean(String key, boolean value) {
        preferences.prefs.putBoolean(key, value);
    }

    public void setBundle(Bundle<String, Object> value) {
        for (Map.Entry<String, Object> entry: value.entrySet()) {
            if (entry.getValue() instanceof Boolean) {
                setBoolean(entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
            } else {
                setString(entry.getKey(), entry.getValue().toString());
            }
        }
    }
}
