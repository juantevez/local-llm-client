package com.example.qwengateway.adapter.controller;

import com.example.qwengateway.adapter.outbound.dto.QwenResponseDTO;
import com.example.qwengateway.port.in.QwenUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qwen")
public class QwenController {

    private final QwenUseCase qwenUseCase;

    public QwenController(QwenUseCase qwenUseCase) {
        this.qwenUseCase = qwenUseCase;
    }

    @PostMapping("/ask")
    public ResponseEntity<QwenResponseDTO> ask(@RequestBody RequestDTO dto) {
        String result = qwenUseCase.askToQwen(dto.getPrompt());
        return ResponseEntity.ok(new QwenResponseDTO(result));
    }

    private static class RequestDTO {
        private String prompt;

        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
    }
}