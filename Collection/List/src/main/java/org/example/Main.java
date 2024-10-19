package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {
        public static void main(String[] args) {
            List<Integer> arrayList = new ArrayList<>();

            // Thêm phần tử vào ArrayList
            for (int i = 0; i < 5; i++) {
                arrayList.add(i);
            }

            // Tạo các luồng
            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    arrayList.set(i, 10+i);
                    System.out.println("Thread 1 đã thêm: " + (i + 10));
                    try {
                        Thread.sleep(100); // Giả lập thời gian xử lý
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    try {
                        // Thử đọc giá trị tại chỉ mục i
                        System.out.println("Thread 2 đọc: " + arrayList.get(i));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Thread 2 đã gặp lỗi: " + e.getMessage());
                    }
                    try {
                        Thread.sleep(100); // Giả lập thời gian xử lý
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Nội dung ArrayList cuối cùng: " + arrayList);
        }

}