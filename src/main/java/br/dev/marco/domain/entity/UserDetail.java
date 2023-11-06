package br.dev.marco.domain.entity;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class UserDetail {
    private String userDetailId;
    private String username;
    private LocalDateTime userCreatedAt;
    private LocalDateTime memberSince;
    private LocalDateTime memberExpirationDate;
    private Integer remainingMonthlyQuestion;

    public Boolean isMember() {
        var now = LocalDateTime.now();
        return Objects.isNull(this.memberExpirationDate) ? Boolean.FALSE : this.memberExpirationDate.isAfter(now);
    }

    public void refreshMemberPlan() {
        this.memberExpirationDate = LocalDateTime.now().plusDays(30);
    }
}
