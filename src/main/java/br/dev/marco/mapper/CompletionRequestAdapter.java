package br.dev.marco.mapper;

import br.dev.marco.domain.entity.Question;
import com.theokanning.openai.completion.CompletionRequest;
import jakarta.enterprise.context.ApplicationScoped;

import java.rmi.NoSuchObjectException;
import java.util.Objects;

@ApplicationScoped
public class CompletionRequestAdapter {

    public CompletionRequest from(Question question, String modelType, Integer maxTokens) throws NoSuchObjectException, NoSuchFieldException {
        if(Objects.isNull(question)) throw new NoSuchObjectException("Question must not be null");
        if(Objects.isNull(modelType) || modelType.isEmpty() || modelType.isEmpty())
            throw new NoSuchFieldException("Model type must be present");
        if(Objects.isNull(maxTokens))
            throw new NoSuchFieldException("Max tokens quantity must not be null");
        return CompletionRequest.builder()
                .prompt(question.promptMessage())
                .model(modelType)
                .temperature(question.getRandomness().getValue())
                .maxTokens(2048)
                .build();
    }
}
