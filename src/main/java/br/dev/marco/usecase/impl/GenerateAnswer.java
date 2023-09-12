package br.dev.marco.usecase.impl;

import br.dev.marco.config.OpenAIConfig;
import br.dev.marco.domain.entity.Question;
import br.dev.marco.usecase.enuns.QuestionType;
import br.dev.marco.mapper.CompletionRequestAdapter;
import br.dev.marco.usecase.Command;
import br.dev.marco.usecase.exceptions.OpenAiException;
import br.dev.marco.usecase.exceptions.UnsupportedQuestionException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateAnswer.class);
    @Inject
    OpenAIConfig openAIConfig;

    @Inject
    CompletionRequestAdapter completionRequestAdapter;

    private final String MODEL_TYPE = "text-davinci-003";

    @Override
    public Uni<String> execute(Question question) throws NoSuchObjectException, NoSuchFieldException {
        LOGGER.info("Generating response in OpenAI Service");
        OpenAiService aiService = new OpenAiService(openAIConfig.apiToken(), Duration.ofSeconds(20));

        return Uni.createFrom().item(
                    aiService.createCompletion(completionRequestAdapter.from(question, MODEL_TYPE, 2048))
                )
                .invoke(openAiResponse ->  LOGGER.info("Answer generated successfully"))
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
