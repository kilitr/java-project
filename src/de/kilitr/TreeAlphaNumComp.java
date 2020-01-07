package de.kilitr;

import java.util.Comparator;

/**
 * This comparator lets TreeMaps sort alpha-numeric keys in the way we'd expect.
 * Example without this class: n0, n1, n10, n11, ...
 * Example using this class: n0, n1, n2, n3, ...
 */
public class TreeAlphaNumComp implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        String str1StringPart = str1.replaceAll("\\d", "");
        String str2StringPart = str2.replaceAll("\\d", "");

        if(str1StringPart.equalsIgnoreCase(str2StringPart)) {
            return extractInt(str1) - extractInt(str2);
        }
        return str1.compareTo(str2);
    }

    private int extractInt(String s) {
        String num = s.replaceAll("\\D", "");
        // return 0 if no digits found
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }
}
