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
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

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
        return Uni.createFrom().completionStage(dbTable.putItem(questionAnsweredORM))
                .invoke(() -> LOGGER.info("UserId: {} - Success adding record. {}",
                        questionAnsweredORM.getRequesterId(), questionAnsweredORM))
                .onFailure()
                .invoke(err -> LOGGER.error("UserId: {} - Error adding record. Fail with: {}",
                        questionAnsweredORM.getRequesterId(), err));
    }

    @Override
    public Uni<List<QuestionAnsweredORM>> getQuestionByRequesterId(String requesterId) {
        LOGGER.info("Username: {} - Starting getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl", requesterId);
        Key key = Key.builder().partitionValue(requesterId).build();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(key);
        SdkPublisher<Page<QuestionAnsweredORM>> queryResult = dbTable.index(USERNAME_GSI).query(queryConditional);
        List<QuestionAnsweredORM> questionsAnswered = new ArrayList<>();
        return Uni.createFrom().completionStage(queryResult.subscribe(
                        response -> questionsAnswered.addAll(response.items())
                ))
                .map(ignored -> questionsAnswered)
                .invoke(() ->   LOGGER.info("Username: {} - Success in getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl", requesterId));
//                .onFailure()
//                .transform(err -> {
//                    LOGGER.error("Username: {} - Error in getQuestionByRequesterId method in CulinaryTopicsRepositoryImpl. Fail with: {}",
//                            requesterId, err);
//                    return new RepositoryException(err.getMessage());
//                });
    }
}
