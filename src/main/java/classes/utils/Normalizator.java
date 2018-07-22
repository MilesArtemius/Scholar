package classes.utils;

public class Normalizator {
    public static String normalize(String unedited) {
        unedited = unedited.replace("@", "_");
        unedited = unedited.replace(".", "_");
        return unedited;
    }


    public static boolean isNormal(String unchecked) {
        return true;
    }

    public static boolean isEmail(String email) {
        return true;
    }
}
