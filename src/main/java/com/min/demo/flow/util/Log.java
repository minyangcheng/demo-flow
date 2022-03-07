package com.min.demo.flow.util;

import java.util.Arrays;

public class Log {

    public static void log(String... args) {
        String message = Arrays.toString(args);
        System.out.println("============> "+message);
    }

}
