package br.dev.marco.domain.usecase.impl;

import br.dev.marco.infra.repository.CulinaryTopicsRepository;
import br.dev.marco.infra.repository.UserDetailRepository;
import br.dev.marco.infra.web.response.QuestionAnsweredResponse;
import br.dev.marco.mapper.QuestionAnsweredResponseMapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class GetFavoriteQuestions {

    private final Logger LOGGER = LoggerFactory.getLogger(GetFavoriteQuestions.class);

    @Inject
    UserDetailRepository userDetailRepository;
    @Inject
    CulinaryTopicsRepository culinaryTopicsRepository;
    @Inject
    QuestionAnsweredResponseMapper questionAnsweredResponseMapper;

    public Uni<List<QuestionAnsweredResponse>> execute(String username) {
        return userDetailRepository.getByUsername(username)
                .flatMap(userDetail -> culinaryTopicsRepository.getQuestionsByRequesterId(userDetail.getUserDetailId().toString()))
                .map(questionAnsweredORMS -> questionAnsweredORMS.stream()
                        .map(questionAnsweredResponseMapper::from)
                        .collect(Collectors.toList())
                );
    }



}
