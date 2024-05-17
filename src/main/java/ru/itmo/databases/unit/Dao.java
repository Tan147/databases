package ru.itmo.databases.unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dao {
    private final Set<String> strings;
    public Dao() {
        strings = new HashSet<>();
    }

    public boolean addString(String string) throws TestException {
        if (string == null || string.isEmpty()) throw new TestException("string error");
       return strings.add(string);
    }

    public List<String> getStringsByFirstLetter(String firstLetter) {
        return strings.stream()
                .filter(s -> s.startsWith(firstLetter))
                .toList();
    }

    public int getNumberOfStrings() {
        return strings.size();
    }
}
