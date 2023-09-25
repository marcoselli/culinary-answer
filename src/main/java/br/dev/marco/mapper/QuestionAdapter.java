package br.dev.marco.mapper;

import br.dev.marco.domain.entity.Question;
import br.dev.marco.domain.entity.UserDetail;
import br.dev.marco.domain.exceptions.MessageException;
import br.dev.marco.domain.exceptions.RandomnessException;
import br.dev.marco.infra.web.request.QuestionRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.SneakyThrows;

@ApplicationScoped
public class QuestionAdapter {
    @SneakyThrows
    public Question from(QuestionRequest questionRequest, UserDetail userDetail) {
        return new Question(questionRequest.message, questionRequest.randomness, userDetail);
    }
}
