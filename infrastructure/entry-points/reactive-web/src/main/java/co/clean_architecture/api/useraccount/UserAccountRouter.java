package co.clean_architecture.api.useraccount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserAccountRouter {

    @Bean
    public RouterFunction<ServerResponse> userAccountRoutes(UserAccountHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/user-accounts", handler::create)
                .GET("/api/v1/user-accounts/{customerId}", handler::getByCustomerId)
                .PATCH("/api/v1/user-accounts/{id}/balance", handler::updateUserAccountBalance)
                .PATCH("/api/v1/user-accounts/{id}/status", handler::updateUserAccountStatus)
                .build();
    }
}
