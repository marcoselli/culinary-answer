package br.dev.marco.domain.usecase.impl;

import br.dev.marco.domain.exceptions.QuestionAnsweredException;
import br.dev.marco.infra.repository.CulinaryTopicsRepository;
import br.dev.marco.infra.repository.UserDetailRepository;
import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import br.dev.marco.infra.web.response.QuestionAnsweredResponse;
import br.dev.marco.mapper.QuestionAnsweredResponseMapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GetLatestQuestions {

    private final Logger LOGGER = LoggerFactory.getLogger(GetLatestQuestions.class);

    @Inject
    UserDetailRepository userDetailRepository;

    @Inject
    CulinaryTopicsRepository culinaryTopicsRepository;

    @Inject
    QuestionAnsweredResponseMapper questionAnsweredResponseMapper;


    public Uni<List<QuestionAnsweredResponse>> execute(String username) {
        LOGGER.info("Username: {} - Starting getting latest questions", username);
        return userDetailRepository.getByUsername(username)
                .flatMap(userDetailORM -> culinaryTopicsRepository.getQuestionByRequesterId(userDetailORM.getUserDetailId().toString()))
                .map(questionsAnswered -> questionsAnswered.stream()
                        .sorted(Comparator.comparing(QuestionAnsweredORM::getAnswerCreatedAt).reversed())
                        .map(questionAnsweredResponseMapper::from)
                        .collect(Collectors.toList())
                )
                .flatMap(questionsAnswered -> questionsAnswered.isEmpty() ?
                        Uni.createFrom().failure(new QuestionAnsweredException("Latest questions not found"))
                        : Uni.createFrom().item(questionsAnswered)
                )
                .invoke(questionsAnswered -> LOGGER.info("Username: {} - Success getting latest questions", username))
                .onFailure()
                .invoke(err -> LOGGER.info("Username: {} - Error getting latest questions. Fail with: {}", username, err));
    }
}
