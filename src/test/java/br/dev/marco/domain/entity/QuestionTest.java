package br.dev.marco.domain.entity;


import br.dev.marco.domain.exception.MessageException;
import br.dev.marco.domain.exception.RandomnessException;
import br.dev.marco.usecase.enuns.QuestionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    @DisplayName("Should instantiate Question correctly")
    void instantiateQuestion() throws MessageException, RandomnessException {
        String message = "Who is the best chef in Brazil?";
        Double randomness = 0.7;
        var question = new Question(message,randomness);

        Assertions.assertNotNull(question);
        Assertions.assertEquals(message, question.getMessage().getValue());
        Assertions.assertEquals(randomness, question.getRandomness().getValue());
    }

    @Test
    @DisplayName("Should format the message to prompt correctly")
    void promptMessageTest() throws MessageException, RandomnessException {
        String message = "Who is the best chef in Brazil?";
        Double randomness = 0.7;
        var question = new Question(message,randomness);

        var sb = new StringBuilder();
        sb.append("Diante da seguinte pergunta: \n\"");
        sb.append(message + "\"");
        sb.append("\nResponda caso essa pergunta seja do tema culinário, caso contrário escreva ");
        sb.append(QuestionType.UNSUPPORTED_QUESTION.name());

        Assertions.assertEquals(sb.toString(), question.promptMessage());

    }
}

