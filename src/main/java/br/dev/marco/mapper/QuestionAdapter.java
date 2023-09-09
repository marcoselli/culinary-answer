package br.dev.marco.mapper;

import br.dev.marco.domain.entity.Question;
import br.dev.marco.domain.exception.MessageException;
import br.dev.marco.domain.exception.RandomnessException;
import br.dev.marco.infra.web.request.QuestionRequest;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;

@ApplicationScoped
public class QuestionAdapter {
    public Question from(QuestionRequest questionRequest) throws MessageException, RandomnessException {
        return new Question(questionRequest.message, questionRequest.randomness);
    }
}
