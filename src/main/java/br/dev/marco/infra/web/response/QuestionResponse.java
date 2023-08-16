package br.dev.marco.infra.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class QuestionResponse {
    public String message;
}
