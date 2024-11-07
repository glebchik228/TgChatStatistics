package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            Chat chat = parser.parseChat(new File("path"), false);
            Analyzer analyzer = new Analyzer(chat);
//            System.out.println(analyzer.getMessagesCount());
//            analyzer.getCountOfMessagesPerUser().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDay().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDayPerUser().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDayOfWeek().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDayOfWeekPerUser().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerHour().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerHourPerUser().entrySet().forEach(System.out::println);
//            analyzer.getMostPopularMessages()
//                    .entrySet()
//                    .stream()
//                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по значению по убыванию
//                    .forEach(entry -> System.out.println("Message: " + entry.getKey() + ", Count: " + entry.getValue()));
//            analyzer.getMostPopularMessagesPerUser()
//                    .forEach((user, messagesMap) -> {
//                        System.out.println("User: " + user);
//                        messagesMap.entrySet()
//                                .stream()
//                                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по убыванию
//                                .forEach(entry -> System.out.println("  Message: " + entry.getKey() + ", Count: " + entry.getValue()));
//                    });
//            analyzer.getMostPopularWords()
//                    .entrySet()
//                    .stream()
//                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по значению по убыванию
//                    .forEach(entry -> System.out.println("Message: " + entry.getKey() + ", Count: " + entry.getValue()));
            analyzer.getMostPopularWordsPerUser()
                    .forEach((user, messagesMap) -> {
                        System.out.println("User: " + user);
                        messagesMap.entrySet()
                                .stream()
                                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по убыванию
                                .forEach(entry -> System.out.println("  Message: " + entry.getKey() + ", Count: " + entry.getValue()));
                    });



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}