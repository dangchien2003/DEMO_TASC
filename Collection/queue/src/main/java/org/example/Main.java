package org.example;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Main {
    public static void main(String[] args) {
//        System.out.println("PriorityQueue");
//        runQueue(new PriorityQueue<>());
//        System.out.println("ArrayBlockingQueue");
//        runQueue(new ArrayBlockingQueue<>(5));
//        System.out.println("LinkedBlockingQueue");
//        runQueue(new LinkedBlockingQueue<>(5));
//        System.out.println("PriorityBlockingQueue");
//        runQueue(new PriorityBlockingQueue<>());
//
//        System.out.println("SynchronousQueue");
//        synchronousQueue();

        runDeque();
    }

    static void runDeque(){
        Deque<Object> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.add(3);
        deque.addLast(2);

        System.out.println("Hàng đợi hiện tại: " + deque);
        System.out.println("Lấy ở đầu: " + deque.pollFirst());
        System.out.println("Hàng đợi hiện tại: " + deque);
        System.out.println("Lấy ở cuối: " + deque.pollLast());
        System.out.println("Hàng đợi hiện tại: " + deque);
        System.out.println("Lấy ở đầu: " + deque.pollFirst());
    }

    static void synchronousQueue(){
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        new Thread(()-> {
            try {
                System.out.println("Đang chờ lấy hàng đợi");
                System.out.println("Lấy phần tử từ hàng đợi: " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                System.out.println("Thêm phần tử vào hàng đợi...");
                queue.add("Hello"); // thêm phần tử vào hàng đợi
                System.out.println("Phần tử đã được thêm.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    static void runQueue(Queue<Object> queue) {
        System.out.println("thêm 1");
        queue.add(1);
        System.out.println("thêm 3");
        queue.add(3);
        System.out.println("thêm 2");
        queue.add(2);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}