package br.dev.marco.domain.entity;


import br.dev.marco.domain.exceptions.MessageException;
import br.dev.marco.domain.exceptions.RandomnessException;
import br.dev.marco.domain.usecase.enuns.QuestionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

class QuestionTest {

    //@Test
    @DisplayName("Should instantiate Question correctly")
    void instantiateQuestion() throws MessageException, RandomnessException {
        String message = "Who is the best chef in Brazil?";
        Double randomness = 0.7;

        var question = new Question(message,randomness, new UserDetail());

        Assertions.assertNotNull(question);
        Assertions.assertEquals(message, question.getMessage().getValue());
        Assertions.assertEquals(randomness, question.getRandomness().getValue());
    }

    //@Test
    @DisplayName("Should format the message to prompt correctly")
    void promptMessageTest() throws MessageException, RandomnessException {
        String message = "Who is the best chef in Brazil?";
        Double randomness = 0.7;
        var question = new Question(message,randomness, new UserDetail());

        var sb = new StringBuilder();
        sb.append("Diante da seguinte pergunta: \n\"");
        sb.append(message + "\"");
        sb.append("\nResponda caso essa pergunta seja do tema culinário, caso contrário escreva ");
        sb.append(QuestionType.UNSUPPORTED_QUESTION.name());

        Assertions.assertEquals(sb.toString(), question.promptMessage());

    }
}

