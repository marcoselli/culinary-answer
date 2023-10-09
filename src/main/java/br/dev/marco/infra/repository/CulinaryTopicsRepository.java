package br.dev.marco.infra.repository;

import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CulinaryTopicsRepository {
    Uni<Void> add(QuestionAnsweredORM questionAnsweredORM);
    Uni<Void> update(QuestionAnsweredORM questionAnsweredORM);
    Uni<QuestionAnsweredORM> getById(String questionId);
    Uni<List<QuestionAnsweredORM>> getQuestionsByRequesterId(String requesterId);
    Uni<List<QuestionAnsweredORM>> getUserFavoritesQuestions(String requesterId);

}
