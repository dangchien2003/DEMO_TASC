package org.example;

import java.util.Map;

public interface IFileService {
    boolean save(Customer customer);

    boolean delete(String phone);

    Map<String, Customer> readData(int thread, String pathData);

    boolean saveAll(Map<String, Customer> data, String phoneStart);
}
