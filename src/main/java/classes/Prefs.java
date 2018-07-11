package classes;

import java.util.prefs.Preferences;

public class Prefs {
    // class is quite useless, in addition it works as a locator to the resources in package;
    private static Prefs preferences = new Prefs();

    private Preferences prefs;

    public static final String keyInit = "KEY_INIT";
    public static final String keySecured = "KEY_SECURED";



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
}
