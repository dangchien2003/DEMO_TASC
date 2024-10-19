package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nHashMap");
        runMap(new HashMap<>());
        System.out.println("\nLinkedHashMap");
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        runMap(linkedHashMap);
        System.out.println("\nLinkedHashMap order");
        runMap(new LinkedHashMap<>(16, 0.75f, true));
        System.out.println("\nTreeMap");
        runMap(new TreeMap<>());
        System.out.println("\nHashtable");
        runMap(new Hashtable<>());
        System.out.println("\nConcurrentHashMap");
        runMap(new ConcurrentHashMap<>());

        System.out.println("\nHashMap và ConcurrentHashMap sử dụng trong đa luồng");
        // sự đồng bộ trong ConcurrentHashMap
        Map<String, Object> map1 = new HashMap<>();
        thread(map1);
        Map<String, Object> map2 = new ConcurrentHashMap();
        thread(map2);
    }

    static void thread(Map<String, Object> map) {
        // Tạo các luồng để cập nhật bản đồ
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put("key" + i, i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put("key" + i, i + 1000);
            }
        });

        thread1.start();
        thread2.start();


        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Không thể ngăn chặn kết thúc luồng chính");
        }


        // In ra kích thước của bản đồ
        System.out.println("Kích thước: " + map.size());
    }


    static void runMap(Map<String, Object> map) {
        map.put("a", 1);
        map.put("a", 1);
        map.put("c", 2);
        map.put("b", 3);
        try {
            map.put(null, 123);
        } catch (NullPointerException e) {
            System.out.println("Không thể thêm key null");
        }

        try {
            map.put("d", null);
        } catch (NullPointerException e) {
            System.out.println("Không thể thêm giá trị null");
        }
        Object value = map.get("b");
        System.out.println("Giá trị của key 'b': " + value);

        try {
            showMap(map);
        } catch (Exception e) {
            System.out.println("Sơ đồ bị thay đổi");
            showKey(map);
        }
    }

    static void showMap(Map<String, Object> map) {
        for (String key : map.keySet()) {
            System.out.println("key: " + key + " value: " + map.get(key));
        }
    }

    static void showKey(Map<String, Object> map) {
        for (String key : map.keySet()) {
            System.out.println("key: " + key);
        }
    }
}