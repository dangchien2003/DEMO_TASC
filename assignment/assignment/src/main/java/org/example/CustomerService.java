package org.example;

import java.util.Set;

public class CustomerService {

    FileService fileService = new FileService();

    public void removeCustomer() {
        String phone = findByPhoneNumber(Utils.inputPhoneNumber(false));

        if(phone == null)
            return;

        if (fileService.delete(phone, Main.customers)) {
            Main.customers.remove(phone);
            System.out.println("Xoá thành công");
        } else {
            System.out.println("Lỗi xoá dữ liệu file");
        }

    }

    public void editCustomer() {
        String phone = findByPhoneNumber(Utils.inputPhoneNumber(false));

        if(phone == null)
            return;

        System.out.println("Thông tin cũ được giữ lại khi trường bị bỏ trống");
        while (true) {
            try {
                Customer customer = Utils.createCustomer(true);

                if (Main.customers.containsKey(customer.getPhoneNumber()) && !customer.getPhoneNumber().equals(phone)) {
                    System.out.println("Số điện thoại đã tồn tại");
                    continue;
                }

                Customer oldData = Main.customers.get(phone);

                mergeCustomer(oldData, customer);
                Main.customers.put(customer.getPhoneNumber(), customer);

                if (!fileService.update(phone, customer, Main.customers)) {
                    Main.customers.remove(customer.getPhoneNumber());
                    Main.customers.put(oldData.getPhoneNumber(), oldData);
                    System.out.println("Lỗi đọc ghi file");

                }

                if (!customer.getPhoneNumber().equals(phone))
                    Main.customers.remove(phone);

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Đã chỉnh sửa");
    }

    public void mergeCustomer(Customer customer, String phone) {
        if (customer.getName() == null || customer.getName().isBlank()) {
            customer.setName(Main.customers.get(phone).getName());
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            customer.setEmail(Main.customers.get(phone).getEmail());
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            customer.setPhoneNumber(Main.customers.get(phone).getPhoneNumber());
        }
    }

    public void mergeCustomer(Customer oldCustomer, Customer newCustomer) {
        if (newCustomer.getName().isBlank()) {
            newCustomer.setName(oldCustomer.getName());
        }

        if (newCustomer.getEmail().isBlank()) {
            newCustomer.setEmail(oldCustomer.getEmail());
        }

        if (newCustomer.getPhoneNumber().isBlank()) {
            newCustomer.setPhoneNumber(oldCustomer.getPhoneNumber());
        }
    }

    public String findByPhoneNumber(String phone) {
        Utils.waitData();

        Customer customer = Main.customers.get(phone);
        if (customer == null) {
            System.out.println("Không tìm thấy");
            return null;
        }
        System.out.println(customer);
        return phone;
    }

    public void showAllCustomer() {
        Utils.waitData();

        if (Main.customers.size() == 0) {
            System.out.println("Không có dữ liệu");
            return;
        }

        Set<String> customerIds = Main.customers.keySet();
        customerIds.forEach(key ->
                System.out.println(Main.customers.get(key).toString())
        );
    }

    public void addCustomer(int n) {

        int turn = 0;
        while (turn < n) {
            try {
                Customer customer = Utils.createCustomer(false);

                if (Main.customers.containsKey(customer.getPhoneNumber())) {
                    System.out.println("Số điện thoại đã tồn tại");
                    continue;
                }

                Main.customers.put(customer.getPhoneNumber(), customer);
                System.out.println("Thêm thành công.");

                if (!fileService.save(customer)) {
                    Main.customers.remove(customer.getPhoneNumber());
                    System.out.println("Lỗi lưu file");
                }

                ++turn;
            } catch (Exception e) {
                System.out.println("Nhập lại do lỗi: " + e.getMessage());
            }
        }
    }
}
