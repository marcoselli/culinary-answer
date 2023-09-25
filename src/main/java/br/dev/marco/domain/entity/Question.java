package br.dev.marco.domain.entity;

import br.dev.marco.domain.exceptions.MessageException;
import br.dev.marco.domain.exceptions.RandomnessException;
import br.dev.marco.domain.usecase.enuns.QuestionType;
import br.dev.marco.domain.usecase.enuns.Topic;
import lombok.SneakyThrows;

public class Question {
    private Message message;

    private Topic topic;
    private Randomness randomness;

    private UserDetail requester;
    @SneakyThrows
    public Question(String message, Double randomness, UserDetail requester) throws MessageException, RandomnessException {
        this.requester = requester;
        this.topic = Topic.GENERAL;
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

    public UserDetail getRequester() {
        return requester;
    }
}
