package classes.utils;

import java.util.HashMap;
import java.util.Map;

public class Bundle<K,V> extends HashMap<K,V> {

    public Bundle(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public Bundle() {
    }

    public static Bundle<String, String> collectStringBundle(String[] keys, String[] values) {
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
            return ((Boolean) this.get(key)).booleanValue();
        } catch (Exception e) {
            return def;
        }
    }

    public String getString(String key, String def) {
        try {
            return this.get(key).toString();
        } catch (Exception e) {
            return def;
        }
    }
}
