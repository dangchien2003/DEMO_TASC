/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.primitive_and_object_data_type;

/**
 *
 * @author chien
 */
public class Primitive_and_object_data_type {

    public static void main(String[] args) {
        convertDatatype();
        compareDatatype();
        dataType();
    }

    private static void dataType(){
        int min_int = -2147483648;
        int max_int = 2147483647;
        float min_float = -3.40282347E+38f;
        float max_float = 3.40282347E+38f;
        double min_double = -1.79769313486231570E+308;
        double max_double = 1.79769313486231570E+308;
        long min_long = -9223372036854775808L;
        long max_long = 9223372036854775807L;
        byte min_byte = -128;
        byte max_byte = 127;
        short min_short = -32768;
        short max_short = 32767;
        boolean b1 = true;
        boolean b2 = false;
        char min_char = 0;
        char max_char = 65535;

        Integer integer = null;
        Integer min_integer = min_int;
        Integer max_integer = max_int;
    }

    private static void compareDatatype(){
        Integer a = 5;
        int b = a;
        if(a == b){
            System.out.println("a == b");
        }else {
            System.out.println("a != b");
        }
    }

    private static void convertDatatype(){
        // unboxing
        Integer a = 5;
        int b = a;
        System.out.println("Đã chuyển đổi Integer sang int.\nb = " + b);

        // boxing
        int c = 6;
        Integer d = c;
        System.out.println("Đã chuyển đổi int sang Integer.\nd = " + d);

//         lỗi khi chuyển class sang int
//        Person person = new Person();
//        int age = person;
    }
}


class Person {
    int age = 1;
}