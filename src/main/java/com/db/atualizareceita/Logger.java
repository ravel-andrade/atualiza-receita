package com.db.atualizareceita;

import java.util.List;

public class Logger {
    public static void logError(List<String> errors) {
        errors.forEach(error -> System.out.println("Error: "+error));
    }

    public static void logError(String error) {
        System.out.println("Error: "+error);
    }
}
