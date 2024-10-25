package org.example;

import java.util.HashMap;
import java.util.Map;

public class Data {
    static Map<String, Customer> fakeData(int n) {

        Map<String, Customer> customerMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String phone = String.valueOf(i);
            for (int j = phone.length(); j < 10; j++) {
                phone = "0" + phone;
            }
            try {
                Customer customer = new Customer(phone, phone + "@gmail.com", phone, false);
                customerMap.put(phone, customer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return customerMap;
    }
}
