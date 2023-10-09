package br.dev.marco.domain.usecase.impl;

import br.dev.marco.infra.repository.CulinaryTopicsRepository;
import br.dev.marco.infra.repository.UserDetailRepository;
import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import br.dev.marco.infra.web.request.FavoriteQuestionRequest;
import br.dev.marco.mapper.QuestionAnsweredAdapter;
import br.dev.marco.mapper.QuestionAnsweredORMAdapter;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class FavoriteQuestion {

    private final Logger LOGGGER = LoggerFactory.getLogger(FavoriteQuestion.class);
    @Inject
    UserDetailRepository userDetailRepository;
    @Inject
    CulinaryTopicsRepository culinaryTopicsRepository;

    @Inject
    QuestionAnsweredAdapter questionAnsweredAdapter;

    @Inject
    QuestionAnsweredORMAdapter questionAnsweredORMAdapter;


    public Uni<Void> execute(FavoriteQuestionRequest favoriteQuestionRequest) {
        LOGGGER.info("Username: {} - Favorite question {}",
                favoriteQuestionRequest.getUsername(), favoriteQuestionRequest.getQuestionId());
        return userDetailRepository.getByUsername(favoriteQuestionRequest.getUsername())
                .flatMap(userDetail -> culinaryTopicsRepository.getById(favoriteQuestionRequest.getQuestionId()))
                .map(questionAnsweredAdapter::from)
                .map(questionAnswered -> {
                    questionAnswered.favoriteQuestion(favoriteQuestionRequest.getTopic(), favoriteQuestionRequest.getTittle());
                    return questionAnswered;
                })
                .call(questionAnswered -> culinaryTopicsRepository.update(questionAnsweredORMAdapter.from(questionAnswered)))
                .replaceWithVoid();
    }

}
