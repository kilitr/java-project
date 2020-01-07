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
        String notDigit = "[^\\d]";
        int int1 = Integer.parseInt(str1.replaceAll(notDigit, ""));
        int int2 = Integer.parseInt(str2.replaceAll(notDigit, ""));
        return Integer.compare(int1,int2);
    }
}
