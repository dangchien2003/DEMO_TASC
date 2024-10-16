package org.example;

public class Employee implements IPerson, IChilden {
    @Override
    public void eat() {
        System.out.println("Nhăm nhăm");
    }

    @Override
    public void sleep() {
        System.out.println("go to sleep");
    }
}
