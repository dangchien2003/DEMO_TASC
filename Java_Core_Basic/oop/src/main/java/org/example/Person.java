package org.example;
abstract class Person {
    private String name;
    // không hỗ trợ thuộc tính trừu tượng
//    abstract int age;

    protected Person(String name){
        this.name = name;
    }
    public abstract void eat();
    public void speak(){
        System.out.println("Xin chào tôi tên: " + name);
    }
}
