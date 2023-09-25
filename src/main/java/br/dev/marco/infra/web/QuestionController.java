package br.dev.marco.infra.web;


import br.dev.marco.domain.entity.UserDetail;
import br.dev.marco.domain.usecase.impl.GetLatestQuestions;
import br.dev.marco.domain.usecase.impl.RetrieveUserDetailFromToken;
import br.dev.marco.domain.usecase.impl.UserQuestionRequestValidator;
import br.dev.marco.infra.web.request.QuestionRequest;
import br.dev.marco.infra.web.response.QuestionResponse;
import br.dev.marco.mapper.QuestionAdapter;
import br.dev.marco.domain.usecase.impl.GenerateAnswer;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.Blocking;
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


@ApplicationScoped
@Path("/v1/question")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionController {

    private final RetrieveUserDetailFromToken retrieveUserDetailFromToken;

    private final UserQuestionRequestValidator userQuestionRequestValidator;
    private final GenerateAnswer generateAnswer;
    private final QuestionAdapter questionAdapter;

    private final GetLatestQuestions getLatestQuestions;

    @Inject
    public QuestionController(RetrieveUserDetailFromToken retrieveUserDetailFromToken,
                              UserQuestionRequestValidator userQuestionRequestValidator, @Named("answerGenerator") GenerateAnswer generateAnswer,
                              QuestionAdapter questionAdapter, GetLatestQuestions getLatestQuestions) {
        this.retrieveUserDetailFromToken = retrieveUserDetailFromToken;
        this.userQuestionRequestValidator = userQuestionRequestValidator;
        this.generateAnswer = generateAnswer;
        this.questionAdapter = questionAdapter;
        this.getLatestQuestions = getLatestQuestions;
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
    @Blocking
    public Uni<Response> ask(@NotNull @Valid QuestionRequest questionRequest) {
        Uni<UserDetail> userDetailUni = retrieveUserDetailFromToken.execute();
        return userDetailUni
                .flatMap(userQuestionRequestValidator::execute)
                .flatMap(ignored -> generateAnswer.execute(questionAdapter.from(
                        questionRequest, userDetailUni.subscribeAsCompletionStage().join()
                )))
                .map(questionResponse -> Response.ok(questionResponse).build());
    }

    @GET
    @Authenticated
    @Operation(description = "Last questions from specific user")
    @APIResponse(
            responseCode = "200",
            description = "Last questions found correctly",
            content = @Content(schema = @Schema(implementation = QuestionResponse.class))
    )
    @APIResponse(responseCode = "201", description = "Questions not found")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    @Path("/{username}/latest")
    public Uni<Response> latestQuestions(String username) {
        return getLatestQuestions.execute(username)
                .map(questionResponse -> Response.ok(questionResponse).build());
    }
}
