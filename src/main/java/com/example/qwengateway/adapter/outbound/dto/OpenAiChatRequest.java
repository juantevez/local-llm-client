package com.example.qwengateway.adapter.outbound.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private int max_tokens;

    public OpenAiChatRequest(String model, List<Message> messages, double temperature, int max_tokens) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }

    // getters y setters
    @Data
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // getters y setters
    }
}