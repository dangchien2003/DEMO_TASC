package org.example;

import java.util.List;
import java.util.Map;

public class CustomerService {

    FileService fileService = new FileService();

    public void removeCustomer() {

        String phone = Utils.inputPhoneNumber(false);

        if (findCustomerByPhoneNumber(phone) == null) {
            System.out.println("Khách hàng không tồn tại");
            return;
        }

        if (fileService.delete(phone)) {
            System.out.println("Xoá thành công");
        } else {
            System.out.println("Lỗi xoá dữ liệu file");
        }
    }

    public void editCustomer() {
        String phone = Utils.inputPhoneNumber(false);
        Customer oldData = findCustomerByPhoneNumber(phone);
        if (oldData == null) {
            System.out.println("Khách hàng không tồn tại");
            return;
        }

        System.out.println("Thông tin cũ được giữ lại khi trường bị bỏ trống");
        while (true) {
            try {
                Customer customer = Utils.createCustomer(true);
                Customer customerWithNewPhoneNumber = null;
                if (!customer.getPhoneNumber().isEmpty()) {
                    customerWithNewPhoneNumber = findCustomerByPhoneNumber(customer.getPhoneNumber());
                }

                if (customerWithNewPhoneNumber != null &&
                        !customer.getPhoneNumber().equals(phone)) {
                    System.out.println("Số điện thoại đã tồn tại");
                    continue;
                }

                mergeCustomer(oldData, customer);

                if(!fileService.delete(phone) ||
                        !fileService.save(customer)) {
                    System.out.println("Cập nhật thông tin thất bại");
                }else {
                    System.out.println("Đã chỉnh sửa");
                }

                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
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

    public Customer findCustomerByPhoneNumber(String phone) {
        return fileService
                .readData(4, fileService.getPathFile(phone))
                .get(phone);

    }


    public void showAllCustomer() {
        List<String> paths = fileService.getAllPathData();
        if (paths == null) {
            System.out.println("Lỗi đọc thư mục");
            return;
        }

        paths.forEach((path) -> {
            Map<String, Customer> customerInFile = fileService.readData(4, path);
            customerInFile.values().forEach((customer)-> {
            });
        });


    }

    public void addCustomer(int n) {

        int turn = 0;
        while (turn < n) {
            try {
                Customer customer = Utils.createCustomer(false);

                Map<String, Customer> customers = fileService.
                        readData(4, fileService.getPathFile(customer.getPhoneNumber()));


                if (customers.containsKey(customer.getPhoneNumber())) {
                    System.out.println("Số điện thoại đã tồn tại");
                    continue;
                }

                customers.put(customer.getPhoneNumber(), customer);


                if (!fileService.save(customer)) {
                    System.out.println("Lỗi lưu file");
                } else {
                    System.out.println("Thêm thành công.");
                }

                ++turn;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Nhập lại do lỗi: " + e.getMessage());
            }
        }
    }
}
