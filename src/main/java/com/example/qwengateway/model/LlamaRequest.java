package com.example.qwengateway.model;

public class LlamaRequest {
    private String model;
    private String prompt;

    public LlamaRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }

    // getters y setters
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
}