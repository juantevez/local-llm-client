package com.example.qwengateway.model;

import lombok.Data;

@Data
public class QwenRequest {
    private String prompt;

    public QwenRequest(String prompt) {
        this.prompt = prompt;
    }

    // getters y setters
}