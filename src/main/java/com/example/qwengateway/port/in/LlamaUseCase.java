package com.example.qwengateway.port.in;

import com.example.qwengateway.adapter.outbound.LlamaServerServiceAdapter;
import org.springframework.stereotype.Component;

@Component
public class LlamaUseCase {

    private final LlamaServerServiceAdapter llamaServerServiceAdapter;

    public LlamaUseCase(LlamaServerServiceAdapter llamaServerServiceAdapter) {
        this.llamaServerServiceAdapter = llamaServerServiceAdapter;
    }

    public String askToLlama(String prompt) {
        return llamaServerServiceAdapter.askLlama(prompt);
    }
}