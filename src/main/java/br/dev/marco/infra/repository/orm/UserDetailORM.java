package br.dev.marco.infra.repository.orm;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

import java.time.LocalDateTime;
import java.util.UUID;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailORM {
    @Getter(onMethod = @__({@DynamoDbAttribute("userDetailId"), @DynamoDbPartitionKey}))
    private UUID userDetailId;
    @Getter(onMethod = @__({@DynamoDbAttribute("username"), @DynamoDbSecondaryPartitionKey(indexNames = "username-index")}))
    private String username;
    private LocalDateTime userCreatedAt;
    private LocalDateTime memberSince;
    private LocalDateTime memberExpirationDate;
    private Integer remainingMonthlyQuestion;

    @Override
    public String toString() {
        return "UserDetailORM{" +
                "userDetailId='" + userDetailId + '\'' +
                ", username='" + username + '\'' +
                ", userCreatedAt=" + userCreatedAt +
                ", memberSince=" + memberSince +
                ", memberExpirationDate=" + memberExpirationDate +
                ", remainingMonthlyQuestion=" + remainingMonthlyQuestion +
                '}';
    }
}
