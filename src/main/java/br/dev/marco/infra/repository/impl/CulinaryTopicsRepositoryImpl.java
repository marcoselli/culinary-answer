package br.dev.marco.infra.repository.impl;

import br.dev.marco.infra.repository.CulinaryTopicsRepository;
import br.dev.marco.infra.repository.exceptions.RepositoryException;
import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import br.dev.marco.infra.repository.orm.UserDetailORM;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class CulinaryTopicsRepositoryImpl implements CulinaryTopicsRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(CulinaryTopicsRepositoryImpl.class);

    private DynamoDbAsyncTable<QuestionAnsweredORM> dbTable;

    private final String TABLE_NAME = "questionAnsweredTable";

    private final String USERNAME_GSI = "requesterId-index";

    @Inject
    public CulinaryTopicsRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoEnhancedAsyncClient) {
        this.dbTable = dynamoEnhancedAsyncClient.table(TABLE_NAME,
                TableSchema.fromClass(QuestionAnsweredORM.class));
    }

    @Override
    public Uni<Void> add(QuestionAnsweredORM questionAnsweredORM) {
        LOGGER.info("UserId: {} - Starting to add user in CulinaryTopicsRepositoryImpl", questionAnsweredORM.getRequesterId());
        return Uni.createFrom().completionStage(dbTable.putItem(questionAnsweredORM))
                .invoke(() -> LOGGER.info("UserId: {} - Success adding record. {}",
                        questionAnsweredORM.getRequesterId(), questionAnsweredORM))
                .onFailure()
                .invoke(err -> LOGGER.error("UserId: {} - Error adding record. Fail with: {}",
                        questionAnsweredORM.getRequesterId(), err));
    }

    @Override
    public Uni<Void> update(QuestionAnsweredORM questionAnsweredORM) {
        LOGGER.info("UserId: {} - Starting to update user in CulinaryTopicsRepositoryImpl", questionAnsweredORM.getRequesterId());
        return Uni.createFrom().completionStage(dbTable.updateItem(questionAnsweredORM))
                .invoke(questionResponse -> LOGGER.info("UserId: {} - Success updating record. {}",
                        questionAnsweredORM.getRequesterId(), questionResponse))
                .replaceWithVoid()
                .onFailure()
                .invoke(err -> LOGGER.error("UserId: {} - Error updating record. Fail with: {}",
                        questionAnsweredORM.getRequesterId(), err));
    }

    @Override
    public Uni<QuestionAnsweredORM> getById(String questionId) {
        var key = Key.builder().partitionValue(questionId).build();
        LOGGER.info("QuestionId: {} - Starting getById method in CulinaryTopicsRepositoryImpl.", questionId);
        return Uni.createFrom().completionStage(dbTable.getItem(key))
                .invoke(questionAnswered -> LOGGER.info("QuestionId: {} - Success in getById method in CulinaryTopicsRepositoryImpl. " +
                        "Response: {}", questionId, questionAnswered))
                .onFailure()
                .invoke(err -> LOGGER.error("Question: {} - Error in getById method in CulinaryTopicsRepositoryImpl. " +
                        "Fail with: {}", questionId, err
                ));
    }

    @Override
    public Uni<List<QuestionAnsweredORM>> getQuestionsByRequesterId(String requesterId) {
        LOGGER.info("UserId: {} - Starting getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl", requesterId);
        Key key = Key.builder().partitionValue(requesterId).build();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(key);
        SdkPublisher<Page<QuestionAnsweredORM>> queryResult = dbTable.index(USERNAME_GSI).query(queryConditional);
        List<QuestionAnsweredORM> questionsAnswered = new ArrayList<>();
        return Uni.createFrom().completionStage(queryResult.subscribe(
                        response -> questionsAnswered.addAll(response.items())
                ))
                .map(ignored -> questionsAnswered)
                .invoke(() -> LOGGER.info("UserId: {} - Success in getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl", requesterId));
//                .onFailure()
//                .transform(err -> {
//                    LOGGER.error("Username: {} - Error in getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl. Fail with: {}",
//                            requesterId, err);
//                    return new RepositoryException(err.getMessage());
//                });
    }

    @Override
    public Uni<List<QuestionAnsweredORM>> getUserFavoritesQuestions(String requesterId) {
        LOGGER.info("UserId: {} - Starting getUserFavoritesQuestions method in CulinaryTopicsRepositoryImpl", requesterId);
        Key key = Key.builder().partitionValue(requesterId).build();
        Expression expression = Expression.builder()
                .expression("isFavorite = true")
                .build();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(key);
        QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .filterExpression(expression)
                .build();
        SdkPublisher<Page<QuestionAnsweredORM>> queryResult = dbTable.index(USERNAME_GSI)
                .query(queryEnhancedRequest);
        List<QuestionAnsweredORM> questionsAnswered = new ArrayList<>();
        return Uni.createFrom().completionStage(queryResult.subscribe(
                        response -> questionsAnswered.addAll(response.items())
                ))
                .map(ignored -> questionsAnswered)
                .invoke(() -> LOGGER.info("UserId: {} - Success in getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl", requesterId));
//                .onFailure()
//                .transform(err -> {
//                    LOGGER.error("Username: {} - Error in getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl. Fail with: {}",
//                            requesterId, err);
//                    return new RepositoryException(err.getMessage());
//                });    }
    }
}

