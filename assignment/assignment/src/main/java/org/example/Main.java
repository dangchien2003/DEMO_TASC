package org.example;

import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Map<String, Customer> customers = new HashMap<>();
    static FileService fileService = new FileService();

    public static void main(String[] args) {
        customers = fileService.readData();
        showMenu();
        while (true) {
            process(getChoice());
        }
    }

    static void process(int choice) {
        switch (choice) {
            case 1:
                showAllCustomer();
                break;
            case 2:
                addCustomer();
                break;
            case 3:
                findByPhoneNumber();
                break;
            case 4:
                editCustomer();
                break;
            case 5:
                removeCustomer();
                break;
            case 0:
                System.exit(1);
                break;
            default:
                System.out.println("Lựa chọn không đúng");
        }
    }

    static void removeCustomer() {
        String phone = findByPhoneNumber();
        if (phone == null)
            return;
        if (fileService.delete(phone, customers)) {
            customers.remove(phone);
            System.out.println("Xoá thành công");
        } else {
            System.out.println("Lỗi xoá dữ liệu file");
        }

    }

    static void editCustomer() {
        String phone = findByPhoneNumber();
        if (phone == null)
            return;

        System.out.println("Thông tin cũ được giữ lại khi trường bị bỏ trống");
        while (true) {
            try {
                Customer customer = createCustomer(true);
                mergeCustomer(customer, phone);

                customers.put(customer.getPhoneNumber(), customer);

                if (!fileService.update(phone, customer, customers)) {
                    System.out.println("Lỗi đọc ghi file");
                }

                if (!customer.getPhoneNumber().equals(phone))
                    customers.remove(phone);

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Đã chỉnh sửa");
    }

    static void mergeCustomer(Customer customer, String phone) throws Exception {
        if (customer.getName() == null || customer.getName().isBlank()) {
            customer.setName(customers.get(phone).getName(), false);
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            customer.setEmail(customers.get(phone).getEmail(), false);
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            customer.setPhoneNumber(customers.get(phone).getPhoneNumber(), false);
        }
    }

    static String findByPhoneNumber() {
        System.out.println("Số điện thoại: ");
        String phone = scanner.nextLine();

        Customer customer = customers.get(phone);
        if (customer == null) {
            System.out.println("Không tìm thấy");
            return null;
        }
        System.out.println(customer);
        return phone;
    }

    static int getChoice() {
        System.out.println("Nhập lựa chọn: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    static void showAllCustomer() {
        if (customers.size() == 0) {
            System.out.println("Không có dữ liệu");
            return;
        }

        Set<String> customerIds = customers.keySet();
        customerIds.forEach(key ->
                System.out.println(customers.get(key).toString())
        );
    }

    static void addCustomer() {
        System.out.println("Số lượng khách hàng sẽ thêm: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        int turn = 0;
        while (turn < n) {
            try {
                Customer customer = createCustomer(false);

                if (customers.containsKey(customer.getPhoneNumber())) {
                    System.out.println("Số điện thoại đã tồn tại");
                    continue;
                }

                customers.put(customer.getPhoneNumber(), customer);
                System.out.println("Thêm thành công.");

                if (!fileService.save(customer)) {
                    customers.remove(customer.getPhoneNumber());
                    System.out.println("Lỗi lưu file");
                }


                ++turn;
            } catch (Exception e) {
                System.out.println("Nhập lại do lỗi: " + e.getMessage());
            }
        }
    }

    private static Customer createCustomer(boolean allowNull) throws Exception {
        System.out.println("Nhập tên: ");
        String name = scanner.nextLine();
        System.out.println("Nhập email: ");
        String email = scanner.nextLine();
        System.out.println("Nhập số điện thoại: ");
        String phone = scanner.nextLine();

        return new Customer(name, email, phone, allowNull);
    }


    static void showMenu() {
        System.out.println("1: Danh sách khách hàng");
        System.out.println("2: Thêm khách hàng");
        System.out.println("3: Tìm theo số điện thoại");
        System.out.println("4: Chỉnh sửa thông tin khách hàng");
        System.out.println("5: Xoá khách hàng");
        System.out.println("0: Đóng chương trình");
    }
}