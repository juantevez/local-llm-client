package com.example.qwengateway.model;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiChatResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;

    // Getters
    public List<Choice> getChoices() {
        return choices;
    }

    public String getFirstMessageContent() {
        if (choices == null || choices.isEmpty()) return "Sin respuesta";
        Choice choice = choices.get(0);
        return choice != null && choice.getMessage() != null
                ? choice.getMessage().getContent()
                : "Sin contenido";
    }

    // Inner classes

    @Data
    public static class Choice {
        private int index;
        private Message message;
        private String finish_reason;

        public Message getMessage() {
            return message;
        }
    }

    @Data
    public static class Message {
        private String role;
        private String content;

        public String getContent() {
            return content;
        }
    }

    @Data
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;

        // getters
    }
}