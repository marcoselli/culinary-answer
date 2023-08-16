package br.dev.marco.infra.web.impl;

import br.dev.marco.infra.web.QuestionController;
import br.dev.marco.infra.web.request.QuestionRequest;
import br.dev.marco.infra.web.response.QuestionResponse;
import br.dev.marco.mapper.CulinaryQuestionMapper;
import br.dev.marco.usecase.MessageGenerator;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/v1/question")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionControllerImpl implements QuestionController {
    @Inject
    MessageGenerator messageGenerator;
    @Inject
    CulinaryQuestionMapper culinaryQuestionMapper;

    @POST
    public Uni<Response> ask(@Valid QuestionRequest questionRequest) {
        return messageGenerator.execute(culinaryQuestionMapper.from(questionRequest))
                .map(answer -> QuestionResponse.builder().message(answer).build())
                .map(questionResponse -> Response.ok(questionResponse).build());
    }


}
