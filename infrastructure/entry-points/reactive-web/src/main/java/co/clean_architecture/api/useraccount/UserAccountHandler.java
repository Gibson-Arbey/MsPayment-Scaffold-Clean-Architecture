package co.clean_architecture.api.useraccount;

import co.clean_architecture.api.useraccount.request.UpdateUserAccounStatusRequest;
import co.clean_architecture.usecase.useraccount.command.CreateUserAccountCommand;
import co.clean_architecture.usecase.useraccount.command.UpdateUserAccounStatusCommand;
import co.clean_architecture.usecase.useraccount.command.UpdateUserAccountBalanceCommand;
import co.clean_architecture.api.useraccount.request.CreateUserAccountRequest;
import co.clean_architecture.api.useraccount.request.UpdateUserAccounBalanceRequest;
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
                .map(req -> new CreateUserAccountCommand(
                    req.getCustomerId(),
                    req.getBalance()
                ))
                .flatMap(userAccountUseCase::createUserAccount)
                .flatMap(userAccount ->
                    ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(UserAccountResponse.fromDomain(userAccount))
                );
    }


    public Mono<ServerResponse> getByCustomerId(ServerRequest request) {
        Long customerId = Long.valueOf(request.pathVariable("customerId"));
        return userAccountUseCase.getUserAccountByCustomerId(customerId)
                .flatMap(userAccount -> this.toResponse(userAccount, HttpStatus.OK));
    }

    public Mono<ServerResponse> updateUserAccountBalance(ServerRequest request) {
        Long customerId = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(UpdateUserAccounBalanceRequest.class)
                .map(req -> new UpdateUserAccountBalanceCommand(
                        customerId,
                        req.getBalance()
                ))
                .flatMap(userAccountUseCase::updateUserAccountBalance)
                .then(ServerResponse.noContent().build());
    }


    public Mono<ServerResponse> updateUserAccountStatus(ServerRequest request) {
        Long customerId = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(UpdateUserAccounStatusRequest.class)
                .map(req -> new UpdateUserAccounStatusCommand(
                        customerId,
                        req.getStatus()
                ))
                .flatMap(userAccountUseCase::updateUserAccountStatus)
                .then(ServerResponse.noContent().build());
    }

    private Mono<ServerResponse> toResponse(UserAccount userAccount, HttpStatus status) {
        return ServerResponse
                .status(status)
                .bodyValue(UserAccountResponse.fromDomain(userAccount));
    }
}
