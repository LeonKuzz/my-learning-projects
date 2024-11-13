package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Date {
    String name;
    String date;
    public Date(String name, String date) {
        this.date = date;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
    public String toString() {
        return name + " " + date + System.lineSeparator();
    }
}
