package org.example;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Map<String, Customer> customers;
    static FileService fileService = new FileService();

    static CustomerService customerService = new CustomerService();
    public static void main(String[] args) {
        new Thread(() -> {
//            LocalDateTime start = LocalDateTime.now();
            customers = fileService.readData(4);
//            LocalDateTime end = LocalDateTime.now();
//            System.out.println(ChronoUnit.MILLIS.between(start, end));
//            System.out.println(customers.size());
        }).start();

//
//        LocalDateTime start1 = LocalDateTime.now();
//        fileService.readData();
//        System.out.println(fileService.readData().size());
//        LocalDateTime end1 = LocalDateTime.now();
//        System.out.println(ChronoUnit.MILLIS.between(start1, end1));
        showMenu();
        while (true) {
            process(getChoice());
        }
    }

    static void process(int choice) {
        switch (choice) {
            case 1:
                customerService.showAllCustomer();
                break;
            case 2:
                System.out.println("Số lượng khách hàng sẽ thêm: ");
                int n = scanner.nextInt();
                scanner.nextLine();
                customerService.addCustomer(n);
                break;
            case 3:
                customerService.findByPhoneNumber(Utils.inputPhoneNumber(false));
                break;
            case 4:
                customerService.editCustomer();
                break;
            case 5:
                customerService.removeCustomer();
                break;
            case 6:
                showMenu();
                break;
            case 0:
                System.exit(1);
                break;
            default:
                System.out.println("Lựa chọn không đúng");
        }
    }



    static int getChoice() {
        System.out.println("Nhập lựa chọn: ");
        try {
            int choice = scanner.nextInt();
            return choice;
        } catch (InputMismatchException e) {
            return -1;
        } finally {
            scanner.nextLine();
        }
    }

    static void showMenu() {
        System.out.println("1: Danh sách khách hàng");
        System.out.println("2: Thêm khách hàng");
        System.out.println("3: Tìm theo số điện thoại");
        System.out.println("4: Chỉnh sửa thông tin khách hàng");
        System.out.println("5: Xoá khách hàng");
        System.out.println("6: Xem menu");
        System.out.println("0: Đóng chương trình");
    }
}