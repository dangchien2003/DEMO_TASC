package org.example;

public class Me extends Person{

    public Me(){
        super("Chiến");
    }
    @Override
    public void eat() {
        System.out.println("Tôi đang ăn");
    }

    public void start(){
        speak();
    }
}
