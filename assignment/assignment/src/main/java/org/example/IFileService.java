package org.example;

import java.util.Map;

public interface IFileService {
    boolean save(Customer customer);

    boolean update(String phone, Customer newCustomer, Map<String, Customer> data);

    boolean delete(String phone, Map<String, Customer> data);

    Map<String, Customer> readData();
}
