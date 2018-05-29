package ir.mfava.modfava.pardazesh.util;

import java.util.Map;

public class Util {

    public static String mapToString(Map<String, String> map) {
        String result = "";
        if (map == null || map.isEmpty()) {
            return "{}";
        }
        result = result + "{";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result = result + " \"" + entry.getKey() + "\" : \"" + entry.getValue() + "\" ,";
        }
        result = result.substring(0, result.length() - 1);
        result = result + "}";
        return result;
    }
}
