package br.dev.marco.infra.repository.impl;

import br.dev.marco.infra.repository.orm.QuestionAnswered;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


@ApplicationScoped
public class CulinaryTopicsRepositoryImpl {

    private final Logger LOGGER = LoggerFactory.getLogger(CulinaryTopicsRepositoryImpl.class);

    private DynamoDbAsyncTable<QuestionAnswered> dbTable;

    private final String TABLE_NAME = "questionAnsweredTable";

    @Inject
    public CulinaryTopicsRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoEnhancedAsyncClient) {
        this.dbTable = dynamoEnhancedAsyncClient.table(TABLE_NAME,
                TableSchema.fromClass(QuestionAnswered.class));
    }
    public Uni<Void> add(QuestionAnswered questionAnswered) {
        return Uni.createFrom().completionStage(dbTable.putItem(questionAnswered))
                .invoke(() -> LOGGER.info("UserId: {} - Success adding record. {}",
                        questionAnswered.getRequesterId(), questionAnswered))
                .onFailure()
                .invoke(err -> LOGGER.error("UserId: {} - Error adding record. Fail with: {}",
                        questionAnswered.getRequesterId(), err));
    }
}
