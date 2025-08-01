package com.example.qwengateway.adapter.outbound;


import com.example.qwengateway.adapter.outbound.dto.OpenAiChatRequest;
import com.example.qwengateway.model.OpenAiChatResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LlamaServerServiceAdapter {

    private final WebClient webClient;

    public LlamaServerServiceAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:1234")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // Aumenta buffer si es necesario
                .build();
    }

    public String askLlama(String prompt) {
        OpenAiChatRequest.Message userMessage = new OpenAiChatRequest.Message("user", prompt);
        OpenAiChatRequest request = new OpenAiChatRequest(
                "LM Studio Community/Meta-Llama-3-8B-Instruct-GGUF",
                List.of(userMessage),
                0.7,
                -1
        );

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(request);
            System.out.println("JSON generado:");
            System.out.println(json);
        } catch (JsonProcessingException e) {
            System.err.println("Error al serializar a JSON: " + e.getMessage());
        }

        try {
            Mono<OpenAiChatResponse> responseMono = webClient.post()
                    .uri("/v1/chat/completions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        System.err.println("Error HTTP: " + clientResponse.statusCode());
                        return Mono.error(new RuntimeException("Error HTTP: " + clientResponse.statusCode()));
                    })
                    .bodyToMono(OpenAiChatResponse.class);

            OpenAiChatResponse response = responseMono.block();

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                throw new RuntimeException("La respuesta está vacía");
            }

            String answer = response.getFirstMessageContent();
            System.out.println("Respuesta del modelo: " + answer);
            return answer;

        } catch (Exception e) {
            System.err.println("Error al invocar al servidor: " + e.getMessage());
            return "Hubo un problema obteniendo la respuesta.";
        }
    }
}