package de.kilitr;

import java.util.Comparator;

/**
 * This comparator lets TreeMaps sort alpha-numeric keys in the way we'd expect.
 * Example without this class: n0, n1, n10, n11, ...
 * Example using this class: n0, n1, n2, n3, ...
 */
class TreeStringAlphaNumComp implements Comparator<String> {

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

/**
 * This comparator lets TreeMaps sort alpha-numeric keys in the way we'd expect.
 * Example without this class: n0, n1, n10, n11, ...
 * Example using this class: n0, n1, n2, n3, ...
 */
class TreeNodeAlphaNumComp implements Comparator<Node> {

    /**
     * The custom compare function for achieving the described order.
     *
     * @param node1 first node to compare
     * @param node2 second node to compare
     * @return a positive Integer - if v1 &gt; v2 <br> a negative Integer - if v1 &lt; v2 <br> zero - if v1 == v2.
     */
    @Override
    public int compare(Node node1, Node node2) {
        String str1StringPart = node1.getLabel().replaceAll("\\d", "");
        String str2StringPart = node2.getLabel().replaceAll("\\d", "");

        if (str1StringPart.equalsIgnoreCase(str2StringPart)) {
            return extractInt(node1.getLabel()) - extractInt(node2.getLabel());
        }
        return node1.getLabel().compareTo(node2.getLabel());
    }

    private int extractInt(String s) {
        String num = s.replaceAll("\\D", "");
        // return 0 if no digits found
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }
}

