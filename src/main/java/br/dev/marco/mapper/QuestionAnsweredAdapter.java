package br.dev.marco.mapper;

import br.dev.marco.domain.entity.QuestionAnswered;
import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class QuestionAnsweredAdapter {

    public QuestionAnswered from(QuestionAnsweredORM questionAnsweredORM) {
        var questionAnswered = new QuestionAnswered();
        questionAnswered.setQuestionId(questionAnsweredORM.getQuestionId().toString());
        questionAnswered.setTopic(questionAnsweredORM.getTopic());
        questionAnswered.setTittle(questionAnsweredORM.getTittle());
        questionAnswered.setQuestion(questionAnsweredORM.getQuestion());
        questionAnswered.setAnswer(questionAnsweredORM.getAnswer());
        questionAnswered.setAnswerCreatedAt(questionAnsweredORM.getAnswerCreatedAt());
        questionAnswered.setIsFavorite(questionAnsweredORM.getIsFavorite());
        return questionAnswered;
    }
}
