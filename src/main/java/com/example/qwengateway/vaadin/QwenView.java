package com.example.qwengateway.vaadin;

import com.example.qwengateway.adapter.outbound.dto.QwenResponseDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Route("qwen")
@PageTitle("Qwen AI | App Demo")
public class QwenView extends VerticalLayout {

    private final WebClient webClient;

    public QwenView(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();

        TextField promptField = new TextField("Escribe tu pregunta:");
        promptField.setWidth("500px");

        TextArea responseArea = new TextArea("Respuesta de Qwen");
        responseArea.setWidth("600px");
        responseArea.setHeight("300px");
        responseArea.setReadOnly(true);

        Button sendButton = new Button("Enviar", event -> {
            String prompt = promptField.getValue();
            if (prompt == null || prompt.trim().isEmpty()) {
                responseArea.setValue("Por favor, escribe algo.");
                return;
            }

            try {
                QwenResponseDTO dto = webClient.post()
                        .uri("http://localhost:8080/api/qwen/ask")
                        .bodyValue(new RequestDTO(prompt))
                        .retrieve()
                        .bodyToMono(QwenResponseDTO.class)
                        .block(); // Para simplicidad, bloquea hasta recibir resultado

                responseArea.setValue(dto != null ? dto.getResponse() : "Sin respuesta.");

            } catch (WebClientResponseException e) {
                responseArea.setValue("Error: " + e.getStatusCode() + " - " + e.getStatusText());
            } catch (Exception ex) {
                responseArea.setValue("Ocurrió un error: " + ex.getMessage());
            }
        });

        add(promptField, sendButton, responseArea);
    }

    // DTO interno para la solicitud
    private record RequestDTO(String prompt) {}
}