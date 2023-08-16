package br.dev.marco.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageError {
    private String errorType;
    private String description;
}
