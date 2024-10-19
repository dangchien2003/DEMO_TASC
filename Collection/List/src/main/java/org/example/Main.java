package org.example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Main {
    private static final int NUM_THREADS = 100;
    private static final int NUM_ELEMENTS = 1000;

    public static void main(String[] args) {
//        mô phỏng sự khác nhau trong môi trương đa luồng
        List<Integer> vector = new Vector<>();
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        System.out.println("vector");
        run(vector);
        System.out.println("array list");
        run(arrayList);
        System.out.println("linked list");
        run(linkedList);
    }
    
    private static void run(List list){
        Thread[] vectorThreads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            vectorThreads[i] = new Thread(() -> {
                for (int j = 0; j < NUM_ELEMENTS; j++) {
                    list.add(j);
                }
            });
        }
        long startTime = System.nanoTime();
        for (Thread thread : vectorThreads) {
            thread.start();
        }
        try {
            for (Thread thread : vectorThreads) {
                thread.join();
            }
        }catch (InterruptedException e){
            System.out.println("error join thread");
        }
        long endTime = System.nanoTime();
        System.out.println("list size: " + list.size());
        System.out.println("list time: " + (endTime - startTime) +" ns");
    }

    static void linkedList(){
        LinkedList<Integer> list = new LinkedList<>();

        // Thêm các phần tử vào ArrayList
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.add(10);
        System.out.println("Phần tử thứ 3: " + list.get(2));

        list.add(2, 99);
        System.out.println("Danh sách sau khi thêm phần tử 99 vào vị trí thứ 3: " + list);

        list.set(3, 100);
        System.out.println("Danh sách sau khi thêm phần tử 100 vào vị trí thứ 4: " + list);


        list.remove(2);
        System.out.println("Danh sách sau khi xoá phần tử thứ 3: " + list);

        System.out.println(list.size());
    }

    static void vector(){
        Vector<Integer> list = new Vector<>();

        // Thêm các phần tử vào ArrayList
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.add(10);
        System.out.println("Phần tử thứ 3: " + list.get(2));

        list.add(2, 99);
        System.out.println("Danh sách sau khi thêm phần tử 99 vào vị trí thứ 3: " + list);

        list.set(3, 100);
        System.out.println("Danh sách sau khi thêm phần tử 100 vào vị trí thứ 4: " + list);


        list.remove(2);
        System.out.println("Danh sách sau khi xoá phần tử thứ 3: " + list);

        System.out.println(list.size());
    }

    static void arrayList(){
        ArrayList<Integer> list = new ArrayList<>();

        // Thêm các phần tử vào ArrayList
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.add(10);
        System.out.println("Phần tử thứ 3: " + list.get(2));

        list.add(2, 99);
        System.out.println("Danh sách sau khi thêm phần tử 99 vào vị trí thứ 3: " + list);

        list.set(3, 100);
        System.out.println("Danh sách sau khi thêm phần tử 100 vào vị trí thứ 4: " + list);


        list.remove(2);
        System.out.println("Danh sách sau khi xoá phần tử thứ 3: " + list);

        // trimToSize() để thu hẹp kích thước bộ nhớ
        list.trimToSize();
        System.out.println(list.size());
    }
}