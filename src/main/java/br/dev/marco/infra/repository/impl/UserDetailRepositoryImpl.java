package br.dev.marco.infra.repository.impl;

import br.dev.marco.infra.repository.UserDetailRepository;
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

@ApplicationScoped
public class UserDetailRepositoryImpl implements UserDetailRepository{

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailRepositoryImpl.class);

    private final DynamoDbAsyncTable<UserDetailORM> dbTable;

    private final String TABLE_NAME = "userDetailTable";

    private final String USERNAME_GSI = "username-index";

    @Inject
    public UserDetailRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        this.dbTable = dynamoDbEnhancedAsyncClient.table(TABLE_NAME, TableSchema.fromClass(UserDetailORM.class));
    }

    @Override
    public Uni<Void> add(UserDetailORM userDetailORM) {
        LOGGER.info("UserId: {} - Starting to add record into UserDetailRepository", userDetailORM.getUserDetailId());
        return Uni.createFrom().completionStage(dbTable.putItem(userDetailORM))
                .invoke(() -> LOGGER.info("UserId: {} - Success adding record. {}",
                        userDetailORM.getUserDetailId(), userDetailORM))
                .onFailure()
                .invoke(err -> LOGGER.error("UserId: {} - Error adding record. Fail with: {}",
                        userDetailORM.getUserDetailId(), err));
    }
    @Override
    public Uni<Void> update(UserDetailORM userDetailORM) {
        LOGGER.info("Username: {} - Starting to update record into UserDetailRepository. Record: {}",
                userDetailORM.getUserDetailId(), userDetailORM);
        return Uni.createFrom().completionStage(dbTable.updateItem(userDetailORM))
                .replaceWithVoid()
                .invoke(() -> LOGGER.info("Username: {} - Success adding record. {}", userDetailORM.getUserDetailId()))
                .onFailure()
                .invoke(err -> LOGGER.error("Username: {} - Error adding record. Fail with: {}",
                        userDetailORM.getUserDetailId(), err));
    }

    @Override
    public Uni<UserDetailORM> getById(String userDetailId) {
        LOGGER.info("UserId: {} - Starting getByUsername method in UserDetailRepository", userDetailId);
        var key = Key.builder().partitionValue(userDetailId).build();
        return Uni.createFrom().completionStage(dbTable.getItem(key))
                .invoke(() -> LOGGER.info("Username: {} - Success in getById method", userDetailId))
                .onFailure()
                .transform(err -> {
                    LOGGER.error("UserId: {} - Error in getById method in UserDetailRepository. Fail with: {}",
                            userDetailId, err);
                    return new RepositoryException(err.getMessage());
                });
    }

    @Override
    public Uni<UserDetailORM> getByUsername(String username) {
        LOGGER.info("Username: {} - Starting getByUsername method in UserDetailRepository", username);
        Key key = Key.builder().partitionValue(username).build();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(key);
        SdkPublisher<Page<UserDetailORM>> queryResult = dbTable.index(USERNAME_GSI).query(queryConditional);
        var result = new ArrayList<UserDetailORM>();
        return Uni.createFrom().completionStage(queryResult.subscribe(
                    response -> result.addAll(response.items())
                ))
                .map(res -> result.stream().findFirst().orElseThrow())
                .invoke(() ->   LOGGER.info("Username: {} - Success in getByUsername method", username));
//                .onFailure()
//                .transform(err -> {
//                    LOGGER.error("Username: {} - Error in getByUsername method in UserDetailRepository. Fail with: {}",
//                            username, err);
//                    return new RepositoryException(err.getMessage());
//                });
    }
}
