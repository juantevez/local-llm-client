package com.example.qwengateway.adapter.outbound;

import com.example.qwengateway.model.QwenRequest;
import com.example.qwengateway.model.QwenResponse;
import com.example.qwengateway.port.out.QwenServicePort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class QwenServiceAdapter implements QwenServicePort {

    private final WebClient webClient;

    public QwenServiceAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
                .build();
    }

    @Override
    public String getQwenResponse(String prompt) {
        QwenRequest request = new QwenRequest(prompt);

        Mono<QwenResponse> responseMono = webClient.post()
                .header("Authorization", "Bearer TU_API_KEY")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(QwenResponse.class);

        return responseMono.block().getOutput(); // Bloqueo síncrono para simplicidad
    }
}