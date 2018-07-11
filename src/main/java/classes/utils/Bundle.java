package classes.utils;

import java.util.HashMap;

public class Bundle extends HashMap {

    public static Bundle collect(Object[] keys, Object[] values) {
        Bundle bundle = new Bundle();
        if (keys.length == values.length) {
            for (int i = 0; i < keys.length; i++) {
                bundle.put(keys[i], values[i]);
            }
            return bundle;
        } else {
            ScLog.out("Bundle creation mismatch");
            return bundle;
        }
    }



    public boolean getBoolean(String key, boolean def) {
        try {
            return (boolean) this.get(key);
        } catch (Exception e) {
            return def;
        }
    }

    public String getString(String key, String def) {
        try {
            return (String) this.get(key);
        } catch (Exception e) {
            return def;
        }
    }
}
