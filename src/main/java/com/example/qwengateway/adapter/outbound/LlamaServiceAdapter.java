package com.example.qwengateway.adapter.outbound;

import com.example.qwengateway.model.LlamaRequest;
import com.example.qwengateway.model.LlamaResponse;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class LlamaServiceAdapter {

    private final WebClient webClient;

    public LlamaServiceAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:1234")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .build();
    }

    public String askLlama3(String prompt) {
        LlamaRequest request = new LlamaRequest("llama3", prompt);

        try {
            Mono<LlamaResponse> responseMono = webClient.post()
                    .uri("/api/generate")
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.value() >= 400, clientResponse -> {
                        System.err.println("Error HTTP: " + clientResponse.statusCode());
                        return Mono.error(new RuntimeException("Error HTTP: " + clientResponse.statusCode()));
                    })
                    .bodyToMono(LlamaResponse.class);

            LlamaResponse response = responseMono.block();
            if (response == null || response.getResponse() == null || response.getResponse().isEmpty()) {
                throw new RuntimeException("La respuesta de Ollama está vacía");
            }

            return response.getResponse();

        } catch (Exception e) {
            System.err.println("Error al invocar a Ollama: " + e.getMessage());
            return "Hubo un problema obteniendo la respuesta.";
        }
    }
}