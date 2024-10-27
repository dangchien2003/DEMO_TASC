package org.example;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Data {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        System.out.println(fakeData(50_000_000, 100));
        LocalDateTime end = LocalDateTime.now();
        System.out.println(ChronoUnit.MILLIS.between(start, end));
    }

    static long fakeData(int quantityCustomer, int quantityFile) {

        FileService fileService = new FileService();

        // tính số bản ghi mỗi file
        long quantityOneFile = (quantityCustomer + quantityFile - 1) / quantityFile;
        while (quantityOneFile >= FileService.numberRecordInFile) {
            quantityFile++;
            quantityOneFile = (quantityCustomer + quantityFile - 1) / quantityFile;
        }
        System.out.println(quantityOneFile);


        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < quantityFile; i++) {
            // tính số bắt đầu và kết thúc
            long startNumber = i * FileService.numberRecordInFile + FileService.indexMin;
            long endPhoneNumber = startNumber + quantityOneFile;

            executorService.submit(() -> {
                Map<String, Customer> customerMap = new HashMap<>();
//                StringBuilder stringBuilder = new StringBuilder();

                for (long j = startNumber; j < endPhoneNumber; j++) {

                    // tạo ra thông tin customer
//                    stringBuilder.setLength(0);
//                    for (int k = phone.length(); k < 10; k++) {
//                        stringBuilder.append("0");
//                    }
//                    phone = stringBuilder + phone;
                    StringBuilder phone = new StringBuilder(String.valueOf(j));
                    while (phone.length() < 10) {
                        phone.insert(0, "0");
                    }

                    try {
                        Customer customer = new Customer(phone.toString(), phone + "@gmail.com", phone.toString());
                        customerMap.put(phone.toString(), customer);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                // lưu
                fileService.saveAll(customerMap, String.format("%10d", startNumber));
            });
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
                executorService.shutdownNow();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return quantityOneFile * quantityFile;
    }
}
