package br.dev.marco.infra.web.impl;


import br.dev.marco.infra.web.QuestionController;
import br.dev.marco.infra.web.request.QuestionRequest;
import br.dev.marco.infra.web.response.QuestionResponse;
import br.dev.marco.mapper.CulinaryQuestionMapper;
import br.dev.marco.usecase.MessageGenerator;
import io.micrometer.core.annotation.Timed;
import io.quarkus.micrometer.runtime.MicrometerCounted;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;


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
    public Uni<Response> ask(QuestionRequest questionRequest) {

        return messageGenerator.execute(culinaryQuestionMapper.from(questionRequest))
                .map(answer -> QuestionResponse.builder().message(answer).build())
                .map(questionResponse -> Response.ok(questionResponse).build());
    }

}
