package br.dev.marco.infra.repository;

import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CulinaryTopicsRepository {
    Uni<Void> add(QuestionAnsweredORM questionAnsweredORM);

    Uni<List<QuestionAnsweredORM>> getQuestionByRequesterId(String username);
}
