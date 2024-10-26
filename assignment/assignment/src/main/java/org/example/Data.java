package org.example;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Data {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        Main.customers = Data.fakeData(10_000_000);
        System.out.println(Main.customers.size());
        Main.fileService.saveAll(Main.customers);
        LocalDateTime end = LocalDateTime.now();
        System.out.println(ChronoUnit.MILLIS.between(start, end));

    }

    static Map<String, Customer> fakeData(int n) {

        Map<String, Customer> customerMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.setLength(0);
            String phone = String.valueOf(i);
            for (int j = phone.length(); j < 10; j++) {
                stringBuilder.append(0);
            }
            phone = stringBuilder + phone;

            try {
                Customer customer = new Customer(phone, phone + "@gmail.com", phone);
                customerMap.put(phone, customer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return customerMap;
    }
}
