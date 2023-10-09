package br.dev.marco.infra.web;


import br.dev.marco.domain.entity.UserDetail;
import br.dev.marco.domain.usecase.impl.*;
import br.dev.marco.infra.web.request.FavoriteQuestionRequest;
import br.dev.marco.infra.web.request.QuestionRequest;
import br.dev.marco.infra.web.response.QuestionAnsweredResponse;
import br.dev.marco.mapper.QuestionAdapter;
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
import software.amazon.awssdk.http.HttpStatusCode;


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

    private final FavoriteQuestion favoriteQuestion;
    private final GetFavoriteQuestions getFavoriteQuestions;

    @Inject
    public QuestionController(RetrieveUserDetailFromToken retrieveUserDetailFromToken,
                              UserQuestionRequestValidator userQuestionRequestValidator, @Named("answerGenerator") GenerateAnswer generateAnswer,
                              QuestionAdapter questionAdapter, GetLatestQuestions getLatestQuestions, FavoriteQuestion favoriteQuestion, GetFavoriteQuestions getFavoriteQuestions) {
        this.retrieveUserDetailFromToken = retrieveUserDetailFromToken;
        this.userQuestionRequestValidator = userQuestionRequestValidator;
        this.generateAnswer = generateAnswer;
        this.questionAdapter = questionAdapter;
        this.getLatestQuestions = getLatestQuestions;
        this.favoriteQuestion = favoriteQuestion;
        this.getFavoriteQuestions = getFavoriteQuestions;
    }

    @POST
    @Authenticated
    @Operation(description = "Ask questions about culinary topics")
    @APIResponse(
            responseCode = "201",
            description = "Generated answer successfully",
            content = @Content(schema = @Schema(implementation = QuestionAnsweredResponse.class))
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
                .map(questionResponse -> Response.status(HttpStatusCode.CREATED).entity(questionResponse).build());
    }

    @GET
    @Authenticated
    @Operation(description = "Last questions from specific user")
    @APIResponse(
            responseCode = "200",
            description = "Last questions found correctly",
            content = @Content(schema = @Schema(implementation = QuestionAnsweredResponse.class))
    )
    @APIResponse(responseCode = "400", description = "Questions not found")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    @Path("/{username}/latest")
    public Uni<Response> latestQuestions(String username) {
        return getLatestQuestions.execute(username)
                .map(questionResponse -> Response.ok(questionResponse).build());
    }

    @GET
    @Authenticated
    @Operation(description = "Get user favorites questions")
    @APIResponse(
            responseCode = "200",
            description = "Question added to favorites successfully"
    )
    @APIResponse(responseCode = "400", description = "Questions not found")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    @Path("/{username}/favorites")
    public Uni<Response> favoriteQuestions(@PathParam("username") String username) {

        return getFavoriteQuestions.execute(username)
                .map(questions -> Response.ok(questions).build());
    }
    @PUT
    @Authenticated
    @Operation(description = "Add question to favorites")
    @APIResponse(
            responseCode = "204",
            description = "Question added to favorites successfully"
    )
    @APIResponse(responseCode = "400", description = "Questions not found")
    @APIResponse(responseCode = "500", description = "Some unexpected error occurred")
    @Path("/{username}/favorites")
    public Uni<Response> favoriteQuestion(@PathParam("username") String username,
                                          FavoriteQuestionRequest favoriteQuestionRequest) {
        favoriteQuestionRequest.setUsername(username);
        return favoriteQuestion.execute(favoriteQuestionRequest)
                .map(questionResponse -> Response.noContent().build());
    }
}
