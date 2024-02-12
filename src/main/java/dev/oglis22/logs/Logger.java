package dev.oglis22.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    private static File file = new File("logs.txt");

    public static void log(String log, String service, LogType logType) throws IOException {

        final String RESET = "\u001B[0m";
        String color_code = "";
        switch (logType) {
            case ERROR -> color_code = "\u001B[31m";
            case INFORMATION -> color_code = "\u001B[37m";
            case WARNING -> color_code = "\u001B[33m";
            case SUCCES -> color_code = "\u001B[32m";
        }

        if (!file.exists()) {
            file.createNewFile();
            System.out.println("Log file created");
        }
        System.out.println(LocalDateTime.now().toString() + " | " + color_code + log + RESET + " | Service: " + service);
        FileWriter fileWriter = new FileWriter("logs.txt");
        fileWriter.write(LocalDateTime.now().toString() + " | " + log + " | Service: " + service);
        fileWriter.close();

    }

}
