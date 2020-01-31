package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

/**
 * This comparator lets TreeMaps sort alpha-numeric keys in the way we'd expect.
 * Example without this class: n0, n1, n10, n11, ...
 * Example using this class: n0, n1, n2, n3, ...
 */
public class TreeAlphaNumComp implements Comparator<Vertex> {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);

    @Override
    public int compare(Vertex v1, Vertex v2) {
        String str1StringPart = v1.getLabel().replaceAll("\\d", "");
        String str2StringPart = v2.getLabel().replaceAll("\\d", "");

        if (str1StringPart.equalsIgnoreCase(str2StringPart)) {
            return extractInt(v1.getLabel()) - extractInt(v2.getLabel());
        }
        return v1.getLabel().compareTo(v2.getLabel());
    }

    private int extractInt(String s) {
        String num = s.replaceAll("\\D", "");
        // return 0 if no digits found
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }
}
