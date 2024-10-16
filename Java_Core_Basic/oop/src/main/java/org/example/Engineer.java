package org.example;

abstract class Engineer extends Person{
    protected Engineer(String name){
        super(name);
    }

    protected abstract void work();
}
