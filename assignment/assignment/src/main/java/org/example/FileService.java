package org.example;

import java.io.*;
import java.util.*;

public class FileService implements IFileService {
    final String PATH = "src/main/java/org/example/data.txt";
    final String TMP_PATH = "src/main/java/org/example/tmp.txt";

    @Override
    public boolean save(Customer customer) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true))) {
            bw.write(customer.toString() + "\n");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Customer> readData() {
        Map<String, Customer> customers = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line = reader.readLine();
            while (line != null && !line.isBlank()) {
                String[] split = line.split("-");
                customers.put(split[2], new Customer(split[0], split[1], split[2], false));

                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy dữ liệu");
            System.exit(1);
        }

        return customers;
    }

    @Override
    public boolean update(String phone, Customer newCustomer, Map<String, Customer> data) {
        return writeDataTmp(data, phone) && convertTmpToDataFile();
    }

    @Override
    public boolean delete(String phone, Map<String, Customer> data) {
        if (!writeDataTmp(data, phone))
            return false;

        return convertTmpToDataFile();
    }

    private synchronized boolean writeDataTmp(Map<String, Customer> data, String ignoreKey) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TMP_PATH))) {
            Set<String> keys = data.keySet();
            for (String key : keys) {
                if(!key.equals(ignoreKey))
                    writer.write(data.get(key).toString() + "\n");
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean convertTmpToDataFile() {
        File fileTmp = new File(TMP_PATH);
        File fileData = new File(PATH);
        if (fileTmp.exists() && fileData.delete()) {
            if (!fileTmp.renameTo(fileData))
                return false;
        } else
            return false;

        return true;
    }

    private boolean writeDataTmp(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TMP_PATH))) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private List<String> getDataIgnore(String phone, Map<String, Customer> data) {
        List<String> lines = new ArrayList<>();
        Set<String> keys = data.keySet();

        keys.forEach(key -> {
            Customer customer = data.get(key);

            if (!customer.getPhoneNumber().equals(phone)) {
                lines.add(customer.toString());
            }
        });

        return lines;
    }
}
