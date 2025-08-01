package com.example.qwengateway.adapter.controller;

import com.example.qwengateway.port.in.LlamaUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/llama")
public class LlamaController {

    private final LlamaUseCase llamaUseCase;

    public LlamaController(LlamaUseCase llamaUseCase) {
        this.llamaUseCase = llamaUseCase;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody RequestDTO dto) {
        return llamaUseCase.askToLlama(dto.getPrompt());
    }

    private static class RequestDTO {
        private String prompt;

        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
    }
}