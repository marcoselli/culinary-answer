package br.dev.marco.domain.usecase.impl;

import br.dev.marco.domain.entity.Question;
import br.dev.marco.domain.entity.UserDetail;
import br.dev.marco.domain.usecase.Command;
import br.dev.marco.domain.usecase.enuns.QuestionType;
import br.dev.marco.domain.usecase.exceptions.OpenAiException;
import br.dev.marco.domain.usecase.exceptions.UnsupportedQuestionException;
import br.dev.marco.infra.repository.CulinaryTopicsRepository;
import br.dev.marco.infra.repository.UserDetailRepository;
import br.dev.marco.infra.repository.orm.UserDetailORM;
import br.dev.marco.infra.security.vault.VaultRecoveryManager;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.infra.security.vault.model.OpenAISecret;
import br.dev.marco.infra.web.response.QuestionAnsweredResponse;
import br.dev.marco.infra.web.response.QuestionResponse;
import br.dev.marco.mapper.CompletionRequestAdapter;
import br.dev.marco.mapper.QuestionAnsweredORMAdapter;
import br.dev.marco.mapper.QuestionAnsweredResponseMapper;
import com.theokanning.openai.service.OpenAiService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NoSuchObjectException;
import java.time.Duration;
import java.util.UUID;

@ApplicationScoped
@Named("answerGenerator")
public class GenerateAnswer implements Command<Question,QuestionAnsweredResponse> {

    private final Logger LOGGER = LoggerFactory.getLogger(GenerateAnswer.class);
    @Inject
    VaultRecoveryManager<OpenAISecret> openAiSecretRecovery;
    @Inject
    CompletionRequestAdapter completionRequestAdapter;
    @Inject
    QuestionAnsweredORMAdapter questionAnsweredORMAdapter;
    @Inject
    CulinaryTopicsRepository culinaryTopicsRepository;

    @Inject
    QuestionAnsweredResponseMapper questionAnsweredResponseMapper;

    @Inject
    UserDetailRepository userDetailRepository;

    @Override
    @SneakyThrows
    public Uni<QuestionAnsweredResponse> execute(Question question) {
        var username = question.getRequester().getUsername();
        LOGGER.info("Username: {} - Generating response in OpenAI Service", username);
        var openAiSecrets = openAiSecretRecovery.secrets();
        OpenAiService aiService = new OpenAiService(openAiSecrets.getApiToken(), Duration.ofSeconds(20));
        return Uni.createFrom().item(
                    aiService.createCompletion(completionRequestAdapter.from(
                            question, openAiSecrets.getModelType(), openAiSecrets.getMaxTokens()))
                )
                .invoke(openAiResponse -> LOGGER.info("Username: {} - Answer generated successfully", username))
                .map(openAiResponse -> openAiResponse.getChoices().get(0).getText())
                .onFailure()
                .transform(err -> {
                    LOGGER.error("Username: {} - Error generating answer. Fail with: {}", username, err);
                    return new OpenAiException(err);
                })
                .flatMap(answer -> answer.contains(QuestionType.UNSUPPORTED_QUESTION.name()) ?
                        Uni.createFrom().failure(new UnsupportedQuestionException())
                        : Uni.createFrom().item(answer)
                )
                .map(answer -> questionAnsweredORMAdapter.from(question,answer))
                .call(questionAnsweredORM -> culinaryTopicsRepository.add(questionAnsweredORM))
                .map(questionAnsweredResponseMapper::from)
                .call(questionAnsweredResponse -> {
                    UserDetailORM user = userDetailRepository.getById(question.getRequester().getUserDetailId()).subscribeAsCompletionStage().join();
                    user.setRemainingMonthlyQuestion(user.getRemainingMonthlyQuestion()-1);
                    return userDetailRepository.update(user);
                })
                .onFailure()
                .invoke(() -> LOGGER.error("Username: {} - Error generating answer. The question does not refer to culinary topics", username));
    }


}
