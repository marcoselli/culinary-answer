package br.dev.marco.infra.web;

import br.dev.marco.infra.web.request.QuestionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface QuestionController {
    Uni<Response> ask(QuestionRequest questionRequest);
}
