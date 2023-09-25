package br.dev.marco.domain.usecase.impl;

import br.dev.marco.domain.entity.UserDetail;
import br.dev.marco.domain.usecase.exceptions.QuestionCreditException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserQuestionRequestValidator {

    private final Integer MINIMUM_QUESTION_CREDIT = 1;

    public Uni<Void> execute(UserDetail userDetail) {
        var voidUni = Uni.createFrom().voidItem();
        if(userDetail.isMember()) return voidUni;
        var questionCredit = Integer.valueOf(userDetail.getRemainingMonthlyQuestion());
        return questionCredit >= MINIMUM_QUESTION_CREDIT ? voidUni :
                Uni.createFrom().failure(new QuestionCreditException("User without credits to request questions"));
    }
}
