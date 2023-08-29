package br.dev.marco.usecase;

import br.dev.marco.config.OpenAIConfig;
import br.dev.marco.domain.CulinaryQuestion;
import br.dev.marco.usecase.impl.AnswerGenerator;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class AnswerGeneratorTest {
    @Mock
    Logger LOGGER;
    @Mock
    OpenAIConfig openAIConfig;
    @InjectMocks
    AnswerGenerator answerGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecute() {
        when(openAIConfig.apiToken()).thenReturn("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");
        CulinaryQuestion abc = CulinaryQuestion.builder()
                .message("abc")
                .build();
        answerGenerator.execute(abc)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailed();
    }
}

