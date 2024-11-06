package org.example;

import java.util.List;

public class Chat {
    private final String name;
    private final List<Message> messages;

    public String getName() {
        return name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Chat(String name, List<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
}
