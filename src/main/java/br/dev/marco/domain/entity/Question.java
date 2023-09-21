package br.dev.marco.domain.entity;

import br.dev.marco.domain.exception.MessageException;
import br.dev.marco.domain.exception.RandomnessException;
import br.dev.marco.domain.usecase.enuns.QuestionType;
import br.dev.marco.domain.usecase.enuns.Topic;

public class Question {
    private Message message;

    private Topic topic;
    private Randomness randomness;

    public Question(String message, Double randomness) throws MessageException, RandomnessException {
        this.message = new Message(message);
        this.randomness = new Randomness(randomness);
    }

    public String promptMessage() {
        var sb = new StringBuilder();
        sb.append("Diante da seguinte pergunta: \n\"");
        sb.append(message + "\"");
        sb.append("\nResponda caso essa pergunta seja do tema culinário, caso contrário escreva ");
        sb.append(QuestionType.UNSUPPORTED_QUESTION.name());
        return sb.toString();
    }

    public Message getMessage() {
        return this.message;
    }

    public Randomness getRandomness() {
        return this.randomness;
    }
}
