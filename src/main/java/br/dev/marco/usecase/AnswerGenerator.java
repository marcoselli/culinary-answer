package br.dev.marco.usecase;

import br.dev.marco.config.OpenAIConfig;
import br.dev.marco.domain.CulinaryQuestion;
import br.dev.marco.enums.QuestionType;
import br.dev.marco.usecase.exceptions.OpenAiException;
import br.dev.marco.usecase.exceptions.UnsupportedQuestionException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Objects;

@ApplicationScoped
public class AnswerGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerGenerator.class);
    @Inject
    OpenAIConfig openAIConfig;

    private final String MODEL_TYPE = "text-davinci-003";

    public Uni<String> execute(CulinaryQuestion culinaryQuestion) {
        LOGGER.info("Generating response in OpenAI Service");
        OpenAiService aiService = new OpenAiService(openAIConfig.apiToken(), Duration.ofSeconds(20));
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(culinaryQuestion.promptMessage())
                .model(MODEL_TYPE)
                .temperature(Objects.nonNull(culinaryQuestion.getRandomness()) ? culinaryQuestion.getRandomness() : 0)
                .maxTokens(2048)
                .build();
        return Uni.createFrom().item(aiService.createCompletion(completionRequest))
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
