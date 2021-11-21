package com.company;

import java.io.InputStreamReader;
import java.util.Comparator;

public class StringBuilder implements UserTypeBuilder{
    @Override
    public String typeName() {
        return "String";
    }

    @Override
    public Object create() {
        return null;
    }

    @Override
    public Object readValue(InputStreamReader in) {
        return in.toString();
    }

    @Override
    public Object parseValue(String ss) {
        return ss;
    }

    @Override
    public Comparator getTypeComparator() {
        Comparator<String> comparator = Comparator.naturalOrder();
        return comparator;
    }
}
