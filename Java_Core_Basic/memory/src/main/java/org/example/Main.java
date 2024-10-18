package org.example;

import java.util.Scanner;

public class Main {
    // cấp phát tĩnh
    static int staticAvariable = 12;
    int[] staticArray = new int[5];

    public static void main(String[] args) {
        // cấp phát động
        Person person1 = new Person("Chien");
        Person person2 = new Person("Chiến");
        person2 = null;

        // Gây ra OutOfMemoryError
        try {
            demoOutOfMemory();
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError xảy ra: Không còn đủ bộ nhớ trong heap!");
        }
    }

    private static void demoOutOfMemory(){
        for(int i = 0; ;i++){
            Person person = new Person("Chien" + i);
            System.out.println("tao lan thu " + i);
        }
    }
}
class Person {
    private String name;

    // Khởi tạo đối tượng trên heap
    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    protected void finalize() {
        System.out.println(this.name + " is being garbage collected");
    }
}