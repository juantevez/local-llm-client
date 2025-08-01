package com.example.qwengateway.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route("")
public class LlamaView extends VerticalLayout {

    public LlamaView() {
        TextArea promptInput = new TextArea("Escribe tu pregunta:");
        promptInput.setWidthFull();

        TextArea output = new TextArea("Respuesta del modelo");
        output.setWidthFull();
        output.setHeight("200px");
        output.setReadOnly(true);

        Button sendButton = new Button("Enviar", event -> {
            String prompt = promptInput.getValue();
            if (prompt == null || prompt.trim().isEmpty()) {
                output.setValue("Por favor, escribe algo.");
                return;
            }

            // Simulamos llamada HTTP real con JavaScript o usamos @JavaScript
            String jsCode = String.format(
                    "fetch('/api/llama/ask', { " +
                            "method: 'POST', " +
                            "headers: {'Content-Type': 'application/json'}, " +
                            "body: JSON.stringify({prompt: '%s'})" +
                            "})" +
                            ".then(res => res.text())" +
                            ".then(text => document.getElementById('output').value = text)" +
                            ".catch(err => document.getElementById('output').value = 'Error: ' + err);",
                    prompt.replace("'", "\\'")
            );

            // Ejecutar JS desde Vaadin
            getUI().ifPresent(ui ->
                    ui.getPage().executeJs(jsCode)
            );
        });

        Div outputDiv = new Div();
        outputDiv.setId("output");
        outputDiv.getStyle().set("margin-top", "20px");
        outputDiv.setText("Aquí aparecerá la respuesta...");

        add(promptInput, sendButton, outputDiv);
    }
}