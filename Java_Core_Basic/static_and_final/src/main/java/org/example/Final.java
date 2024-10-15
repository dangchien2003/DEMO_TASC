package org.example;

public class Final {
    final int a = 5;

    private void updateA(){
        // lỗi khi thay đổi giá trị của a
//        a = 6;
    }
}
// lỗi khi kế thừa lớp final
//class Child extends Person {
//
//}

final class Person {
    String name;
    int age;
}

class Car extends Vihecle {
    public Car(){
        super("VF9");
    }
    // lỗi khi override phương thức final
//    @Override
//    public void run() {
//        System.out.println("run");
//    }
    public static void main(String[] args) {
        Car car = new Car();
        System.out.println("vihecle name: "+ car.name);
        System.out.println("vihecle color: "+ Car.color);
        car.run();
    }
}

class Vihecle {
    final String name;
    public Vihecle(String name){
        this.name = name;
    }

    static final String color;
    static {
        color = "red";
    }

    final public void run(){
        System.out.println("running...");
    }

    public void setName(String name){
        // lỗi khi thay đổi giá trị biến final
//        this.name = name;
    }
}
