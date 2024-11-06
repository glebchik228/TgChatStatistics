package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            Chat chat = parser.parseChat(new File("path"), false);
            System.out.println("end");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}