package org.example;

public class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name, boolean allowNull) throws Exception {
        if (!allowNull && (name == null || name.isBlank()))
            throw new Exception("Invalid name");

        this.name = name;
    }
}
