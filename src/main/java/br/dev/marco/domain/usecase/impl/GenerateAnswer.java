package br.dev.marco.domain.usecase.impl;

import br.dev.marco.domain.entity.Question;
import br.dev.marco.domain.usecase.Command;
import br.dev.marco.domain.usecase.enuns.QuestionType;
import br.dev.marco.domain.usecase.exceptions.OpenAiException;
import br.dev.marco.domain.usecase.exceptions.UnsupportedQuestionException;
import br.dev.marco.infra.security.vault.VaultRecoveryManager;
import br.dev.marco.infra.security.vault.exceptions.CredentialException;
import br.dev.marco.infra.security.vault.model.OpenAISecret;
import br.dev.marco.mapper.CompletionRequestAdapter;
import com.theokanning.openai.service.OpenAiService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NoSuchObjectException;
import java.time.Duration;

@ApplicationScoped
@Named("answerGenerator")
public class GenerateAnswer implements Command<Question,String> {

    private final Logger LOGGER = LoggerFactory.getLogger(GenerateAnswer.class);
    @Inject
    VaultRecoveryManager<OpenAISecret> openAiSecretRecovery;
    @Inject
    CompletionRequestAdapter completionRequestAdapter;

    @Override
    public Uni<String> execute(Question question) throws NoSuchObjectException, NoSuchFieldException, CredentialException {
        LOGGER.info("Generating response in OpenAI Service");
        var openAiSecrets = openAiSecretRecovery.secrets();
        OpenAiService aiService = new OpenAiService(openAiSecrets.getApiToken(), Duration.ofSeconds(20));
        return Uni.createFrom().item(
                    aiService.createCompletion(completionRequestAdapter.from(
                            question, openAiSecrets.getModelType(), openAiSecrets.getMaxTokens()))
                )
                .invoke(openAiResponse -> LOGGER.info("Answer generated successfully"))
                .map(openAiResponse -> openAiResponse.getChoices().get(0).getText())
                .onFailure()
                .transform(err -> {
                    LOGGER.error("Error generating answer. Fail with: {}", err);
                    return new OpenAiException(err);
                })
                .flatMap(answer -> answer.contains(QuestionType.UNSUPPORTED_QUESTION.name()) ?
                        Uni.createFrom().failure(new UnsupportedQuestionException())
                        : Uni.createFrom().item(answer)
                )
                .onFailure()
                .invoke(() -> LOGGER.error("Error generating answer. The question does not refer to culinary topics"));
    }


}
