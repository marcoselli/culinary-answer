package br.dev.marco.infra.web.request;

import lombok.Data;

@Data
public class FavoriteQuestionRequest {
    private String questionId;
    private String topic;
    private String tittle;
    private String username;
}
