package com.company;

import java.io.InputStreamReader;
import java.util.Comparator;

public class IntegerBuilder implements UserTypeBuilder {

    @Override
    public String typeName() {
        return "Integer";
    }

    @Override
    public Object create() {
        Integer newInt = null;
        return newInt;
    }

    @Override
    public Object readValue(InputStreamReader in) {
        Integer newInt = Integer.getInteger(in.toString());
        return newInt;
    }

    @Override
    public Object parseValue(String ss) {
        Integer newInt = Integer.parseInt(ss);
        return newInt;
    }

    @Override
    public Comparator getTypeComparator() {
        Comparator<Integer> comparator = Comparator.naturalOrder();
        return comparator;
    }

}
