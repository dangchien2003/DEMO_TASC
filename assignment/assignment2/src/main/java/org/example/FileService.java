package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileService implements IFileService {
    private final String PRE_PATH = "src/main/resources";
    public static final long indexMin = 100_000_000;
    public static final int numberRecordInFile = 1_000_000;
    private long getIndexFile(String phone) {


        return (Integer.parseInt(phone) - indexMin) / numberRecordInFile + 1;
    }

    public String getPathFile(String phone) {
        return PRE_PATH + "/data" + getIndexFile(phone) + ".txt";
    }

    public String getPathTmp(String phone) {
        return PRE_PATH + "/tmp" + getIndexFile(phone) + ".txt";
    }

    @Override
    public boolean save(Customer customer) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(getPathFile(customer.getPhoneNumber()), true))) {
            bw.write(customer + "\n");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean saveAll(Map<String, Customer> data, String phoneStart) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getPathFile(phoneStart)))) {
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

    public Map<String, Customer> readData(int numberThread, String pathData) {
        Map<String, Customer> customers = new ConcurrentHashMap<>();
        String[] customersStr;
        try {
            Path path = Paths.get(pathData);

            if (!Files.exists(path)) {
                return customers;
            }

            byte[] contentByte = Files.readAllBytes(path);
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
            executorService.submit(() -> {
                for (int j = start; j < end; j++) {
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
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            return null;
        }
        return customers;
    }

    @Override
    public boolean delete(String phone) {
        Map<String, Customer> data = readData(4, getPathFile(phone));
        data.remove(phone);

        String pathTmp = getPathTmp(phone);
        if (!writeDataTmp(pathTmp, data, phone))
            return false;

        return convertTmpToDataFile(pathTmp, getPathFile(phone));
    }

    public List<String> getAllPathData() {
        Path dirPath = Paths.get("src/main/resources");
        try {
            return Files.list(dirPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }

    private synchronized boolean writeDataTmp(String pathTmp, Map<String, Customer> data, String ignoreKey) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathTmp))) {
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
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean convertTmpToDataFile(String pathTmp, String pathData) {
        File fileTmp = new File(pathTmp);
        File fileData = new File(pathData);
        if (fileTmp.exists() && fileData.delete()) {
            if (!fileTmp.renameTo(fileData))
                return false;
        } else
            return false;

        return true;
    }
}
