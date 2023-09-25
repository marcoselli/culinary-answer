package br.dev.marco.mapper;

import br.dev.marco.domain.entity.User;
import br.dev.marco.domain.usecase.enuns.UserType;
import br.dev.marco.infra.repository.orm.UserDetailORM;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class UserDetailORMAdapter {

    public UserDetailORM from(User user, String ssoId) {
        UserType userType = user.getUserType();
        var now = LocalDateTime.now();
        return UserDetailORM.builder()
                .userDetailId(UUID.randomUUID())
                .username(user.getUsername().getValue())
                .userCreatedAt(now)
                .memberSince(userType.equals(UserType.MEMBER) ? now : null)
                .memberExpirationDate(userType.equals(UserType.MEMBER) ? now.plusDays(30) : null)
                .remainingMonthlyQuestion(30)
                .build();
    }
}
