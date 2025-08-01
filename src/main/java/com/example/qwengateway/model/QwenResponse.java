package com.example.qwengateway.model;

import lombok.Data;

@Data
public class QwenResponse {
    private String output;

    public QwenResponse(String output) {
        this.output = output;
    }

    // getters y setters
}