package org.example;

public class Singleton {
    private static Singleton instance;

    private String data;
    private Singleton(){}

    public static synchronized Singleton getInstance(){
        if(instance == null)
            instance = new Singleton();

        return instance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
