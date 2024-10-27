package org.example;

import java.util.Scanner;

public class Utils {
    public static void waitData() {
        if (Main.customers == null)
            System.out.println("Loading data");
        while (Main.customers == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static Customer createCustomer(boolean allowEmpty) {
        return new Customer(inputName(allowEmpty), inputEmail(allowEmpty), inputPhoneNumber(allowEmpty));
    }

    public static String inputPhoneNumber(boolean allowEmpty) {
        String phoneNumber;
        boolean invalidData = false;
        do {
            if (invalidData)
                System.out.println("Nhập lại số điện thoại: ");
            else
                System.out.println("Nhập số điện thoại: ");
            invalidData = true;

            phoneNumber = scanner.nextLine();
        } while ((!allowEmpty && phoneNumber.isEmpty()) ||
                (!phoneNumber.isEmpty() && !phoneNumber.matches("^[0-9]{10}$")));
        return phoneNumber;
    }

    public static String inputEmail(boolean allowEmpty) {
        String email;
        boolean invalidData = false;
        do {
            if (invalidData)
                System.out.println("Nhập lại email: ");
            else
                System.out.println("Nhập email: ");
            invalidData = true;

            email = scanner.nextLine().trim();
        } while ((!allowEmpty && email.isEmpty()) ||
                (!email.isEmpty() &&
                        !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")));
        return email;
    }

    public static String inputName(boolean allowEmpty) {
        String name;
        boolean invalidData = false;
        do {
            if (invalidData)
                System.out.println("Nhập lại tên: ");
            else
                System.out.println("Nhập tên: ");
            invalidData = true;

            name = scanner.nextLine().trim();
        } while (!allowEmpty && name.isEmpty());
        return name;
    }
}
