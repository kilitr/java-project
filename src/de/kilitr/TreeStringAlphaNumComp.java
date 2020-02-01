package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

/**
 * This comparator lets TreeMaps sort alpha-numeric keys in the way we'd expect.
 * Example without this class: n0, n1, n10, n11, ...
 * Example using this class: n0, n1, n2, n3, ...
 */
public class TreeStringAlphaNumComp implements Comparator<String> {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);

    /**
     * The custom compare function for achieving the described order.
     *
     * @return a positive Integer - if v1 &gt; v2 <br> a negative Integer - if v1 &lt; v2 <br> zero - if v1 == v2.
     */
    @Override
    public int compare(String str1, String str2) {
        String str1StringPart = str1.replaceAll("\\d", "");
        String str2StringPart = str2.replaceAll("\\d", "");

        if (str1StringPart.equalsIgnoreCase(str2StringPart)) {
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
