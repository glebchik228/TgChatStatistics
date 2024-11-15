package org.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Analyzer {
    private final Chat chat;
    private final Map<String, Integer> countOfMessagesPerUser;

    private final Map<LocalDate, Integer> countOfMessagesPerDay;
    private final Map<LocalDate, Map<String, Integer>> countOfMessagesPerDayPerUser;
    private final Map<DayOfWeek, Integer> countOfMessagesPerDayOfWeek;
    private final Map<DayOfWeek, Map<String, Integer>> countOfMessagesPerDayOfWeekPerUser;
    private final Map<Integer, Integer> countOfMessagesPerHour;
    private final Map<Integer, Map<String, Integer>> countOfMessagesPerHourPerUser;
    private int totalWords;
    private final Map<String, Integer> wordsPerUser;
    private int totalCharacters;
    private final Map<String, Integer> charactersPerUser;
    private final Map<String, Integer> mostPopularMessages; // TODO
    private final Map<String, Map<String, Integer>> mostPopularMessagesPerUser;
    private final Map<String, Integer> mostPopularWords; // TODO
    private final Map<String, Map<String, Integer>> mostPopularWordsPerUser; // TODO


    public Map<LocalDate, Map<String, Integer>> getCountOfMessagesPerDayPerUser() {
        return countOfMessagesPerDayPerUser;
    }

    public Map<DayOfWeek, Integer> getCountOfMessagesPerDayOfWeek() {
        return countOfMessagesPerDayOfWeek;
    }

    public Map<DayOfWeek, Map<String, Integer>> getCountOfMessagesPerDayOfWeekPerUser() {
        return countOfMessagesPerDayOfWeekPerUser;
    }

    public Map<Integer, Integer> getCountOfMessagesPerHour() {
        return countOfMessagesPerHour;
    }

    public Map<Integer, Map<String, Integer>> getCountOfMessagesPerHourPerUser() {
        return countOfMessagesPerHourPerUser;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public Map<String, Integer> getWordsPerUser() {
        return wordsPerUser;
    }

    public int getTotalCharacters() {
        return totalCharacters;
    }

    public Map<String, Integer> getCharactersPerUser() {
        return charactersPerUser;
    }

    public Map<String, Integer> getMostPopularMessages() {
        return mostPopularMessages;
    }

    public Map<String, Map<String, Integer>> getMostPopularMessagesPerUser() {
        return mostPopularMessagesPerUser;
    }

    public Map<String, Integer> getMostPopularWords() {
        return mostPopularWords;
    }

    public Map<String, Map<String, Integer>> getMostPopularWordsPerUser() {
        return mostPopularWordsPerUser;
    }

    public Analyzer(Chat chat) {
        this.chat = chat;
        countOfMessagesPerUser = calculateMessagesPerUser();
        countOfMessagesPerDay = calculateMessagesPerDate();
        countOfMessagesPerDayPerUser = calculateMessagesPerDatePerUser();
        countOfMessagesPerDayOfWeek = calculateMessagesPerDayOfWeek();
        countOfMessagesPerDayOfWeekPerUser = calculateMessagesPerDayOfWeekPerUser();
        countOfMessagesPerHour = calculateMessagesPerHour();
        countOfMessagesPerHourPerUser = calculateMessagesPerHourPerUser();
        mostPopularMessagesPerUser = calculateMostPopularMessagesPerUser();
        mostPopularWordsPerUser = calculateMostPopularWordsPerUser();
        this.wordsPerUser = new HashMap<>();
        this.charactersPerUser = new HashMap<>();
        this.mostPopularMessages = new HashMap<>();
        this.mostPopularWords = new HashMap<>();
        analyzeChat();
    }

    private void analyzeChat() {
        for (Message message : chat.getMessages()) {
            String text = message.getText();
            String sender = message.getSenderName();

            int charCount = text.length();
            totalCharacters += charCount;
            charactersPerUser.put(sender, charactersPerUser.getOrDefault(sender, 0) + charCount);

            String[] words = text.split("\\s+");
            totalWords += words.length;
            wordsPerUser.put(sender, wordsPerUser.getOrDefault(sender, 0) + words.length);

            mostPopularMessages.put(text, mostPopularMessages.getOrDefault(text, 0) + 1);

            for (String word : words) {
                mostPopularWords.put(word, mostPopularWords.getOrDefault(word, 0) + 1);
            }
        }
    }


    public Map<LocalDate, Integer> getCountOfMessagesPerDay() {
        return countOfMessagesPerDay;
    }

    public Map<String, Integer> getCountOfMessagesPerUser() {
        return countOfMessagesPerUser;
    }

    public int getMessagesCount() {
        return chat.getMessages().size();
    }

    private Map<String, Integer> calculateMessagesPerUser() {
        Map<String, Integer> messagesPerUser = new HashMap<>();
        for (Message message : chat.getMessages()) {
            String name = message.getSenderName();
            messagesPerUser.put(name, messagesPerUser.getOrDefault(name, 0) + 1);
        }
        return messagesPerUser;
    }

    private Map<LocalDate, Integer> calculateMessagesPerDate() {
        Map<LocalDate, Integer> messagesPerDate = new HashMap<>();
        for (Message message : chat.getMessages()) {
            LocalDate localDate = message.getDate().toLocalDate();
            messagesPerDate.put(localDate, messagesPerDate.getOrDefault(localDate, 0) + 1);
        }
        return messagesPerDate;
    }

    private Map<LocalDate, Map<String, Integer>> calculateMessagesPerDatePerUser() {
        Map<LocalDate, Map<String, Integer>> messagesPerDatePerUser = new HashMap<>();
        for (Message message : chat.getMessages()) {
            LocalDate localDate = message.getDate().toLocalDate();
            String name = message.getSenderName();
            Map<String, Integer> currentUserMap = messagesPerDatePerUser
                    .computeIfAbsent(localDate, k -> new HashMap<>());
            currentUserMap.put(name, currentUserMap.getOrDefault(name, 0) + 1);
        }
        return messagesPerDatePerUser;
    }

    private Map<DayOfWeek, Integer> calculateMessagesPerDayOfWeek() {
        Map<DayOfWeek, Integer> messagesPerDate = new HashMap<>();
        for (Message message : chat.getMessages()) {
            DayOfWeek dayOfWeek = message.getDate().getDayOfWeek();
            messagesPerDate.put(dayOfWeek, messagesPerDate.getOrDefault(dayOfWeek, 0) + 1);
        }
        return messagesPerDate;
    }

    private Map<DayOfWeek, Map<String, Integer>> calculateMessagesPerDayOfWeekPerUser() {
        Map<DayOfWeek, Map<String, Integer>> messagesPerDatePerUser = new HashMap<>();
        for (Message message : chat.getMessages()) {
            DayOfWeek dayOfWeek = message.getDate().getDayOfWeek();
            String name = message.getSenderName();
            Map<String, Integer> currentUserMap = messagesPerDatePerUser
                    .computeIfAbsent(dayOfWeek, k -> new HashMap<>());
            currentUserMap.put(name, currentUserMap.getOrDefault(name, 0) + 1);
        }
        return messagesPerDatePerUser;
    }

    private Map<Integer, Integer> calculateMessagesPerHour() {
        Map<Integer, Integer> messagesPerDate = new HashMap<>();
        for (Message message : chat.getMessages()) {
            Integer hour = message.getDate().getHour();
            messagesPerDate.put(hour, messagesPerDate.getOrDefault(hour, 0) + 1);
        }
        return messagesPerDate;
    }

    private Map<Integer, Map<String, Integer>> calculateMessagesPerHourPerUser() {
        Map<Integer, Map<String, Integer>> messagesPerDatePerUser = new HashMap<>();
        for (Message message : chat.getMessages()) {
            Integer hour = message.getDate().getHour();
            String name = message.getSenderName();
            Map<String, Integer> currentUserMap = messagesPerDatePerUser
                    .computeIfAbsent(hour, k -> new HashMap<>());
            currentUserMap.put(name, currentUserMap.getOrDefault(name, 0) + 1);
        }
        return messagesPerDatePerUser;
    }

    private Map<String, Map<String, Integer>> calculateMostPopularMessagesPerUser() {
        Map<String, Map<String, Integer>> messagesPerUser = new HashMap<>();
        for (Message message : chat.getMessages()) {
            String text = message.getText().toLowerCase();
            if (text.isEmpty()) continue;
            String name = message.getSenderName();
            Map<String, Integer> userMessagesMap = messagesPerUser.computeIfAbsent(name, k -> new HashMap<>());
            userMessagesMap.put(text, userMessagesMap.getOrDefault(text, 0) + 1);
        }
        return messagesPerUser;
    }


    private Map<String, Map<String, Integer>> calculateMostPopularWordsPerUser() {
        Map<String, Map<String, Integer>> wordsPerUser = new HashMap<>();
        for (Message message : chat.getMessages()) {
            String text = message.getText();
            String[] words = text.split("\\s+");
            String name = message.getSenderName();
            Map<String, Integer> userWordsMap = wordsPerUser.computeIfAbsent(name, k -> new HashMap<>());
            for (String word : words) {
                userWordsMap.put(word, userWordsMap.getOrDefault(word, 0) + 1);
            }
        }
        return wordsPerUser;
    }

}
