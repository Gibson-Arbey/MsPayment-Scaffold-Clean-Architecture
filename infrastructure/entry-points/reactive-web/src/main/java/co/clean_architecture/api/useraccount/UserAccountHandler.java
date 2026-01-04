package co.clean_architecture.api.useraccount;

import co.clean_architecture.api.useraccount.request.CreateUserAccountRequest;
import co.clean_architecture.api.useraccount.response.UserAccountResponse;
import co.clean_architecture.model.useraccount.UserAccount;
import co.clean_architecture.usecase.useraccount.UserAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserAccountHandler {

    private final UserAccountUseCase userAccountUseCase;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateUserAccountRequest.class)
                .map(this::toDomain)
                .flatMap(userAccountUseCase::createUserAccount)
                .flatMap(userAccount ->  this.toResponse(userAccount, HttpStatus.CREATED));
    }

    private UserAccount toDomain(CreateUserAccountRequest request) {
        return UserAccount.builder()
                .customerId(request.getCustomerId())
                .balance(request.getBalance())
                .build();
    }

    private Mono<ServerResponse> toResponse(UserAccount userAccount, HttpStatus status) {
        return ServerResponse
                .status(status)
                .bodyValue(UserAccountResponse.fromDomain(userAccount));
    }
}
