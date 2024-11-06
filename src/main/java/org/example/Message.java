package org.example;

import java.time.LocalDateTime;

public class Message {
    private final int id;
    private final LocalDateTime date;
    private final String senderName;
    private final String text;

    public Message(int id, LocalDateTime date, String senderName, String text) {
        this.id = id;
        this.date = date;
        this.senderName = senderName;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getText() {
        return text;
    }
}
