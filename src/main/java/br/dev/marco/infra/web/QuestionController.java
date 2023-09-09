package br.dev.marco.infra.web;


import br.dev.marco.domain.exception.MessageException;
import br.dev.marco.domain.exception.RandomnessException;
import br.dev.marco.infra.web.request.QuestionRequest;
import br.dev.marco.infra.web.response.QuestionResponse;
import br.dev.marco.mapper.QuestionAdapter;
import br.dev.marco.usecase.impl.GenerateAnswer;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.rmi.NoSuchObjectException;


@ApplicationScoped
@Path("/v1/question")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionController {

    private final GenerateAnswer generateAnswer;
    private final QuestionAdapter questionAdapter;

    @Inject
    public QuestionController(@Named("answerGenerator") GenerateAnswer generateAnswer,
                              QuestionAdapter questionAdapter) {
        this.generateAnswer = generateAnswer;
        this.questionAdapter = questionAdapter;
    }

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
    public Uni<Response> ask(@NotNull @Valid QuestionRequest questionRequest)
            throws MessageException, RandomnessException, NoSuchObjectException, NoSuchFieldException {
        return generateAnswer.execute(questionAdapter.from(questionRequest))
                .map(answer -> QuestionResponse.builder().message(answer).build())
                .map(questionResponse -> Response.ok(questionResponse).build());
    }

}
