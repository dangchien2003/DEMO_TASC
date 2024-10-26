package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

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
    public boolean saveAll(Map<String, Customer> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true))) {
            data.values()
                    .forEach(customer -> {
                                try {
                                    bw.write(customer.toString() + "\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private Customer convertToCustomer(String lineCustomer) {
        String[] split = lineCustomer.split("-");

        Customer customer = new Customer(split[0], split[1], split[2]);
        return customer;

    }

    public Map<String, Customer> readData(int numberThread) {
        Map<String, Customer> customers = new ConcurrentHashMap<>();
        String[] customersStr;
        try {
            byte[] contentByte = Files.readAllBytes(Paths.get(PATH));
            String contentStr = new String(contentByte, StandardCharsets.UTF_8);
            customersStr = contentStr.split("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int size = customersStr.length;
        int chunkSize = (size + numberThread - 1) / numberThread;
        ExecutorService executorService = Executors.newFixedThreadPool(numberThread);

        for (int i = 0; i < numberThread; i++) {
            int start = i * chunkSize;
            int end = start + chunkSize;
            executorService.submit(()-> {
                for(int j = start; j < end; j++) {
                    String customerData = customersStr[j];
                    try {
                        Customer customer = convertToCustomer(customerData);
                        customers.put(customer.getPhoneNumber(), customer);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1000, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            return null;
        }
        return customers;
    }


    public Map<String, Customer> readDataThread1(int threadCount) {
        Map<String, Customer> customers = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {

            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        String line = reader.readLine();

                        while (line != null && !line.isBlank()) {
                            Customer customer = convertToCustomer(line);
                            customers.put(customer.getPhoneNumber(), customer);
                            line = reader.readLine();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("đọc xong");
        } catch (Exception e) {
            System.out.println("Lỗi lấy dữ liệu");
            System.exit(1);
        }

//        return customers;
        return null;
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
                if (!key.equals(ignoreKey))
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
