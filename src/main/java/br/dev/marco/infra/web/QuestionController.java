package br.dev.marco.infra.web;


import br.dev.marco.infra.web.request.QuestionRequest;
import br.dev.marco.infra.web.response.QuestionResponse;
import br.dev.marco.mapper.CulinaryQuestionMapper;
import br.dev.marco.usecase.AnswerGenerator;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;


@ApplicationScoped
@Path("/v1/question")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionController {
    @Inject
    AnswerGenerator answerGenerator;
    @Inject
    CulinaryQuestionMapper culinaryQuestionMapper;

    @POST
    @Authenticated
    @Operation(description = "Ask questions about culinary topics")
    @APIResponse(
            responseCode = "201",
            description = "Generated answer successfully",
            content = @Content(schema = @Schema(implementation = QuestionResponse.class))
    )
    @APIResponse(responseCode = "400", description = "Bad request")
    @APIResponse(responseCode = "408", description = "Connection timed out")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    public Uni<Response> ask(@NotNull @Valid QuestionRequest questionRequest) {
        return answerGenerator.execute(culinaryQuestionMapper.from(questionRequest))
                .map(answer -> QuestionResponse.builder().message(answer).build())
                .map(questionResponse -> Response.ok(questionResponse).build());
    }

}
