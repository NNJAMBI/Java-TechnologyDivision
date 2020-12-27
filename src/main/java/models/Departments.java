package models;

import java.util.Objects;

public class Departments {
    private String name;
    private int id;

    public Departments(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
