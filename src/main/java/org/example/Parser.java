package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {
    private final ObjectMapper objectMapper;

    public Parser() {
        this.objectMapper = new ObjectMapper();
    }

    public Chat parseChat(File jsonFile, boolean includeForwardedMessages) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        String chatName = rootNode.get("name").asText();

        List<Message> messages = new ArrayList<>();
        ArrayNode messagesNode = (ArrayNode) rootNode.get("messages");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (JsonNode messageNode : messagesNode) {
            if (messageNode.has("forwarded_from") && !includeForwardedMessages)
                continue;
            if (!Objects.equals(messageNode.get("type").asText(), "message"))
                continue;
            int id = messageNode.get("id").asInt();
            String from = messageNode.get("from").asText();
            LocalDateTime date = LocalDateTime.parse(messageNode.get("date").asText(), formatter);


            StringBuilder textBuilder = new StringBuilder();
            JsonNode textNode = messageNode.get("text");

            if (textNode.isArray()) {
                ArrayNode textArray = (ArrayNode) textNode;
                for (JsonNode element : textArray) {
                    if (element.isTextual()) {
                        textBuilder.append(element.asText());
                    } else if (element.isObject() && element.has("text")) {
                        textBuilder.append(element.get("text").asText());
                    }
                }
            } else if (textNode.isTextual()) {
                textBuilder.append(textNode.asText());
            }

            String text = textBuilder.toString();
            messages.add(new Message(id, date, from, text));
        }

        return new Chat(chatName, messages);
    }
}
