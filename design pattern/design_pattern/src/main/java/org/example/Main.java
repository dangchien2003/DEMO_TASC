package org.example;

public class Main {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.setData("chien");
        System.out.println(instance.getData());

        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance1.getData());

        AnimalFactory animalFactory = new DogFactory();
        animalFactory.createAnimal().speak();

        AnimalFactory animalFactory2 = new CatFactory();
        animalFactory2.createAnimal().speak();
    }
}