package com.example.qwengateway.port.in;

import com.example.qwengateway.port.out.QwenServicePort;
import org.springframework.stereotype.Component;

@Component
public class QwenUseCase {
    private final QwenServicePort qwenServicePort;

    public QwenUseCase(QwenServicePort qwenServicePort) {
        this.qwenServicePort = qwenServicePort;
    }

    public String askToQwen(String prompt) {
        return qwenServicePort.getQwenResponse(prompt);
    }
}