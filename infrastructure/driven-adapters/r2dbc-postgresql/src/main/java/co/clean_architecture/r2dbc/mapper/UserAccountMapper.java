package co.clean_architecture.r2dbc.mapper;

import co.clean_architecture.model.useraccount.UserAccount;
import co.clean_architecture.r2dbc.entity.UserAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    public UserAccountEntity toEntity(UserAccount domain) {
        return UserAccountEntity.builder()
            .id(domain.getId())
            .customerId(domain.getCustomerId())
            .balance(domain.getBalance())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .build();
    }

    public UserAccount toDomain(UserAccountEntity entity) {
        return UserAccount.restore(
                entity.getId(),
                entity.getCustomerId(),
                entity.getBalance(),
                co.clean_architecture.model.useraccount.StatusUserAccountEnum.valueOf(entity.getStatus()),
                entity.getCreatedAt()
        );
    }
}
