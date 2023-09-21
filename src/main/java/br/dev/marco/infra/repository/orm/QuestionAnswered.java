package br.dev.marco.infra.repository.orm;

import br.dev.marco.domain.usecase.enuns.Topic;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;
import java.util.UUID;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswered {
    @Getter(onMethod = @__({@DynamoDbAttribute("questionId"), @DynamoDbPartitionKey}))
    private UUID questionId;
    private Topic topic;
    private String tittle;
    private String question;
    private String answer;
    private LocalDateTime answerCreatedAt;
    private UUID requesterId;
    private Boolean isPersistent;

    @Override
    public String toString() {
        return "QuestionAnswered{" +
                "questionId=" + questionId +
                ", topic=" + topic +
                ", tittle='" + tittle + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", answerCreatedAt=" + answerCreatedAt +
                ", requesterId=" + requesterId +
                ", isPersistent=" + isPersistent +
                '}';
    }
}
